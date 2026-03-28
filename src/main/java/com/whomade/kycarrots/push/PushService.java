package com.whomade.kycarrots.push;

import com.whomade.kycarrots.entity.member.OpUserVO;
import com.whomade.kycarrots.framework.common.constant.Const;
import com.whomade.kycarrots.service.member.OpUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class PushService {

    private final FcmService fcmService;
    private final OpUserService opUserService;

    public void sendTargetPush(List<String> targetRoles, String targetBranchId, String targetUserId, Long targetUserNo,
            String messageTitle, String messageBody, String eventType, Map<String, String> dataPayload) {
        sendTargetPush(null, targetRoles, targetBranchId, targetUserId, targetUserNo, messageTitle, messageBody,
                eventType, dataPayload);
    }

    /**
     * 공통 푸시 발송 서비스
     *
     * @param actorUserNo    푸시 발생 주체 (발송자 본인 제외용)
     * @param targetRoles    대상 권한 목록 (예: ROLE_PUB, ROLE_PROJ, ROLE_SELL)
     * @param targetBranchId 대상 지점 ID (특정 지점 관리자에게 보낼 때)
     * @param targetUserId   단일 사용자 STRING ID (구매자 등)
     * @param targetUserNo   단일 사용자 LONG NUMBER (구매자 등)
     * @param messageTitle   푸시 제목
     * @param messageBody    푸시 내용
     * @param eventType      이벤트 종류 (push_log 기록용)
     * @param dataPayload    FCM 데이터 페이로드
     */
    public void sendTargetPush(Long actorUserNo, List<String> targetRoles, String targetBranchId, String targetUserId,
            Long targetUserNo,
            String messageTitle, String messageBody, String eventType, Map<String, String> dataPayload) {

        // 1. targetUserId 또는 targetUserNo가 있는 경우 단일 사용자에게 매핑된 토큰으로 발송 (예: 구매자)
        if (targetUserId != null && !targetUserId.isEmpty()) {
            OpUserVO user = opUserService.fetchFcmToken(targetUserId);
            if (user != null && isSameUser(actorUserNo, user.getUserNo()))
                return;
            sendToSingleUser(actorUserNo, user, messageTitle, messageBody, eventType, dataPayload);
            return; // 단일 발송이 우선 처리됨
        } else if (targetUserNo != null && targetUserNo > 0) {
            OpUserVO user = opUserService.fetchFcmTokenByUserNo(targetUserNo);
            if (user != null && isSameUser(actorUserNo, user.getUserNo()))
                return;
            sendToSingleUser(actorUserNo, user, messageTitle, messageBody, eventType, dataPayload);
            return;
        }

        // 2. target_roles가 있는 경우 복수 발송 (지점 관리자, 본사 관리자 등)
        if (targetRoles != null && !targetRoles.isEmpty()) {
            Set<String> processedUserNos = new HashSet<>();

            // 2-1. 본사 (BRANCH_ID=Const.CENTER_BRANCH_ID) 전송
            // ROLE_ADMIN은 필터링에서 제외 여부를 호출부 의도에 맡기도록 수정 (보통 관리자도 받아야함)
            List<String> targetRolesFiltered = targetRoles.stream()
                    .collect(java.util.stream.Collectors.toList());

            if (!targetRolesFiltered.isEmpty()) {
                // 본사(Center) 대상 전송
                for (String role : targetRolesFiltered) {
                    List<OpUserVO> hqUsers = opUserService.selectUsersByBranchAndRole(Const.CENTER_BRANCH_ID, role);
                    for (OpUserVO user : hqUsers) {
                        if (user != null && user.getUserNo() != null && !processedUserNos.contains(user.getUserNo())) {
                            if (isSameUser(actorUserNo, user.getUserNo()))
                                continue;
                            sendToSingleUser(actorUserNo, user, messageTitle, messageBody, eventType, dataPayload);
                            processedUserNos.add(user.getUserNo());
                        }
                    }
                }

                // 2-2. 판매지점 (targetBranchId) 전송 - 본사(Const.CENTER_BRANCH_ID)가 아닐 경우에만 추가 전송
                if (targetBranchId != null && !targetBranchId.isEmpty()
                        && !Const.CENTER_BRANCH_ID.equals(targetBranchId)) {
                    for (String role : targetRolesFiltered) {
                        List<OpUserVO> branchUsers = opUserService.selectUsersByBranchAndRole(targetBranchId, role);
                        for (OpUserVO user : branchUsers) {
                            if (user != null && user.getUserNo() != null
                                    && !processedUserNos.contains(user.getUserNo())) {
                                if (isSameUser(actorUserNo, user.getUserNo()))
                                    continue;
                                sendToSingleUser(actorUserNo, user, messageTitle, messageBody, eventType, dataPayload);
                                processedUserNos.add(user.getUserNo());
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean isSameUser(Long actorUserNo, String targetUserNo) {
        if (actorUserNo == null || actorUserNo <= 0L) return false;
        if (targetUserNo == null || targetUserNo.isEmpty()) return false;
        
        try {
            return actorUserNo.equals(Long.parseLong(targetUserNo));
        } catch (NumberFormatException e) {
            return actorUserNo.toString().equals(targetUserNo);
        }
    }

    private void sendToSingleUser(Long actorUserNo, OpUserVO user, String messageTitle, String messageBody,
            String eventType,
            Map<String, String> dataPayload) {
        if (user != null && user.getPushToken() != null && !user.getPushToken().isEmpty()) {
            fcmService.sendPushToUserAndLog(
                    actorUserNo,
                    user.getDeviceType(),
                    user.getUserNo(),
                    user.getPushToken(),
                    messageTitle,
                    messageBody,
                    dataPayload != null ? (dataPayload.containsKey("targetId") ? dataPayload.get("targetId")
                            : dataPayload.get("productId")) : null,
                    eventType,
                    dataPayload);
        }
    }
}
