package com.whomade.kycarrots.push;

import com.whomade.kycarrots.entity.member.OpUserVO;
import com.whomade.kycarrots.service.member.OpUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class PushService {

    private final FcmService fcmService;
    private final OpUserService opUserService;

    /**
     * 공통 푸시 발송 서비스
     *
     * @param targetRoles    대상 권한 목록 (예: ROLE_PUB, ROLE_PROJ, ROLE_SELL)
     * @param targetBranchId 대상 지점 ID (특정 지점 관리자에게 보낼 때)
     * @param targetUserId   단일 사용자 STRING ID (구매자 등)
     * @param targetUserNo   단일 사용자 LONG NUMBER (구매자 등)
     * @param messageTitle   푸시 제목
     * @param messageBody    푸시 내용
     * @param eventType      이벤트 종류 (push_log 기록용)
     * @param dataPayload    FCM 데이터 페이로드
     */
    public void sendTargetPush(List<String> targetRoles, String targetBranchId, String targetUserId, Long targetUserNo,
            String messageTitle, String messageBody, String eventType, Map<String, String> dataPayload) {

        // 1. targetUserId 또는 targetUserNo가 있는 경우 단일 사용자에게 매핑된 토큰으로 발송 (예: 구매자)
        if (targetUserId != null && !targetUserId.isEmpty()) {
            OpUserVO user = opUserService.fetchFcmToken(targetUserId);
            sendToSingleUser(user, messageTitle, messageBody, eventType, dataPayload);
            return; // 단일 발송이 우선 처리됨
        } else if (targetUserNo != null && targetUserNo > 0) {
            OpUserVO user = opUserService.fetchFcmTokenByUserNo(targetUserNo);
            sendToSingleUser(user, messageTitle, messageBody, eventType, dataPayload);
            return;
        }

        // 2. target_roles와 target_branch_id가 있는경우 복수 발송 (지점 관리자, 본사 관리자 등)
        if (targetRoles != null && !targetRoles.isEmpty()) {
            for (String role : targetRoles) {
                // 특정 지점의 역할에게 발송
                if (targetBranchId != null && !targetBranchId.isEmpty()) {
                    List<OpUserVO> users = opUserService.selectUsersByBranchAndRole(targetBranchId, role);
                    for (OpUserVO user : users) {
                        sendToSingleUser(user, messageTitle, messageBody, eventType, dataPayload);
                    }
                } else {
                    // 특정 역할 전체에게 (본사 관리자, 혹은 전체 사용자 브로드캐스트)
                    fcmService.sendPushToTopicAndLog(
                            0L,
                            role,
                            messageTitle,
                            messageBody,
                            dataPayload,
                            eventType);
                }
            }
        }
    }

    private void sendToSingleUser(OpUserVO user, String messageTitle, String messageBody, String eventType,
            Map<String, String> dataPayload) {
        if (user != null && user.getPushToken() != null && !user.getPushToken().isEmpty()) {
            fcmService.sendPushToUserAndLog(
                    0L, // System actor
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
