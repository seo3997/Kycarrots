package com.whomade.kycarrots.chat;

import com.whomade.kycarrots.entity.chat.ChatMessageVo;
import com.whomade.kycarrots.entity.member.OpUserVO;
import com.whomade.kycarrots.entity.product.TnProductVo;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.push.FcmService;
import com.whomade.kycarrots.service.chat.ChatMessageService;
import com.whomade.kycarrots.service.member.OpUserService;
import com.whomade.kycarrots.service.product.TnProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import com.whomade.kycarrots.framework.common.constant.Const;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatWsController {
    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;
    private final OpUserService opUserService;

    @Autowired
    private WebSocketUserTracker userTracker;
    @Autowired
    private FcmService fcmService;
    @Autowired
    private TnProductService tnProductService;

    @MessageMapping("/chat.enter.{roomId}")
    public void enterRoom(@DestinationVariable String roomId, ChatMessage message) {
        log.info("채팅방 진입 - roomId: {}, userId: {}", roomId, message.getSenderId());
        userTracker.addChatter(message.getSenderId(), roomId);
    }

    @MessageMapping("/chat.exit.{roomId}")
    public void exitRoom(@DestinationVariable String roomId, ChatMessage message) {
        log.info("채팅방 퇴장 신호 수신 - roomId: {}, userId: {}", roomId, message.getSenderId());

        // 1. 퇴장하는 유저 정보 조회
        OpUserVO senderInfo = opUserService.fetchFcmToken(message.getSenderId());

        if (senderInfo != null) {
            String role = senderInfo.getMemberCode();
            String branchId = senderInfo.getBranchId();

            // ROLE_PROJ (본사/센터관리)인 경우: 본사 지점 전체 관련자 오프라인 처리
            if (Const.ROLE_PROJ.equals(role)) {
                List<OpUserVO> hqStaff = opUserService.selectUsersByBranchAndRole(branchId, Const.ROLE_PROJ);
                for (OpUserVO staff : hqStaff) {
                    userTracker.removeChatter(staff.getUserId());
                }
                log.info("퇴장으로 인해 본사(ROLE_PROJ) 그룹의 모든 관련자 채팅 상태를 해제했습니다. (Branch: {})", branchId);
            }
            // ROLE_SELL (판매지점 직원)인 경우: 해당 지점 전체 관련자 오프라인 처리
            else if (Const.ROLE_SELL.equals(role)) {
                List<OpUserVO> branchStaff = opUserService.selectUsersByBranchAndRole(branchId, Const.ROLE_SELL);
                for (OpUserVO staff : branchStaff) {
                    userTracker.removeChatter(staff.getUserId());
                }
                log.info("퇴장으로 인해 판매점(ROLE_SELL) 그룹의 모든 관련자 채팅 상태를 해제했습니다. (Branch: {})", branchId);
            } else {
                // 일반 구매자 권한 등은 본인만 오프라인 처리
                userTracker.removeChatter(message.getSenderId());
            }
        } else {
            userTracker.removeChatter(message.getSenderId());
        }
    }

    /**
     * 나만 오프라인 처리 (인트로/메인 진입 시 본인 상태 초기화용)
     * 그룹 전체에 영향을 주지 않고 본인 세션만 제거합니다.
     */
    @MessageMapping("/chat.me.exit")
    public void exitMe(ChatMessage message) {
        log.info("본인 상태 초기화 신호 수신 - userId: {}", message.getSenderId());
        userTracker.removeChatter(message.getSenderId());
    }

    @MessageMapping("/chat.sendsample")
    @SendTo("/topic/room1sample")
    public ChatMessage send(ChatMessage message) {
        System.out.println("수신된 메시지: " + message.getMessage());
        return message; // 구독 중인 모든 클라이언트에게 전송
    }

    @MessageMapping("/chat.send.{roomId}")
    @SendTo("/topic/{roomId}")
    public ChatMessage sendRoomId(@DestinationVariable String roomId, ChatMessage message) {
        log.info("message.getSenderId(): {}", message.getSenderId());
        log.info("message.getMessage(): {}", message.getMessage());
        log.info("roomId: {}", roomId);
        if (message.getSenderId() == null || message.getMessage() == null) {
            log.warn("필수 필드 누락: senderId 또는 message가 null");
            return null; // 또는 기본 에러 메시지 반환
        }

        try {
            // 발신자의 권한(Group) 정보 가져오기
            OpUserVO senderInfo = opUserService.fetchFcmToken(message.getSenderId());
            String senderGroup = (senderInfo != null) ? senderInfo.getMemberCode() : "ROLE_PUB";
            log.info("발신자 정보 조회 - ID: {}, Group: {}", message.getSenderId(), senderGroup);

            // 클라이언트로 보낼 메시지에도 세팅
            message.setSenderGroup(senderGroup);

            // DB 저장용 VO 생성 (수신 그룹 추가)
            ChatMessageVo chatMessageVo = ChatMessageVo.builder()
                    .roomId(roomId)
                    .senderId(message.getSenderId())
                    .senderGroup(senderGroup)
                    .receiveGroup(message.getReceiveGroup()) // 명시적 수신 그룹 저장
                    .message(message.getMessage())
                    .build();
            chatMessageService.insertChatMessage(chatMessageVo); 

            // 1. ChatRoom 정보 조회
            Optional<ChatRoomEntity> chatRoomOpt = chatRoomService.findByRoomId(roomId);
            if (!chatRoomOpt.isPresent()) {
                log.warn("채팅방 정보가 없습니다. roomId: {}", roomId);
                return message;
            }

            ChatRoomEntity chatRoom = chatRoomOpt.get();
            String id1 = chatRoom.getBuyerId();
            String id2 = chatRoom.getBranchId();
            String targetTopic = null;
            OpUserVO singleReceiver = null;
            String receiveGroup = message.getReceiveGroup();

            log.info("[채팅푸시트레이스] roomId: {}, senderId: {}, receiveGroup: {}", roomId, message.getSenderId(), receiveGroup);

            // 2. 푸시 타겟 및 토픽 결정
            if (Const.ROLE_SELL.equals(receiveGroup)) {
                targetTopic = Const.ROLE_SELL;
            } else if (Const.ROLE_PROJ.equals(receiveGroup)) {
                String targetBranchId = message.getSenderId().equals(id1) ? id2 : id1;
                targetTopic = "BRANCH_" + targetBranchId + "_" + Const.ROLE_PROJ;
            } else if (Const.ROLE_PUB.equals(receiveGroup)) {
                String targetBuyerId = message.getSenderId().equals(id1) ? id2 : id1;
                singleReceiver = opUserService.fetchFcmToken(targetBuyerId);
            }

            // 3. 온라인 상태 체크 (타켓 그룹 중 한 명이라도 온라인이면 푸시 억제)
            boolean isTargetGroupOnline = false;
            if (Const.ROLE_SELL.equals(receiveGroup)) {
                isTargetGroupOnline = userTracker.isAnyHqOnline();
            } else if (Const.ROLE_PROJ.equals(receiveGroup)) {
                String targetBranchId = message.getSenderId().equals(id1) ? id2 : id1;
                isTargetGroupOnline = userTracker.isAnyBranchStaffOnline(targetBranchId);
            } else if (singleReceiver != null) {
                isTargetGroupOnline = userTracker.isUserOnline(singleReceiver.getUserId());
            }

            if (isTargetGroupOnline) {
                log.info("[채팅푸시트레이스] 타겟이 온라인 상태이므로 푸시 발송을 하지 않습니다.");
            } else {
                // 4. 푸시 발송 (토픽 또는 개인)
                Long productId = chatRoom.getProductId();
                DataMap param = new DataMap();
                param.put("productId", productId);
                param.put("userNo", "0");
                TnProductVo product = tnProductService.getProduct(param);
                String messageTitle = (product != null ? product.getTitle() : "알림") + " 채팅메시지";

                Map<String, String> data = new HashMap<>();
                data.put("targetId", roomId);
                data.put("type", "chat");
                data.put("msg", message.getMessage());
                data.put("title", messageTitle);
                data.put("body", message.getMessage());

                OpUserVO sender = opUserService.fetchFcmToken(message.getSenderId());
                Long actorNo = (sender != null && sender.getUserNo() != null) ? Long.parseLong(sender.getUserNo()) : 0L;

                // 본인에게 푸시가 가지 않도록 방어 로직 추가
                if (singleReceiver != null && singleReceiver.getUserId().equals(message.getSenderId())) {
                    log.info("[채팅푸시트레이스] 타겟이 발신자 본인이므로 푸시를 취소합니다.");
                    return message;
                }

                if (targetTopic != null) {
                    log.info("[채팅푸시트레이스] 토픽({}) 푸시 발송", targetTopic);
                    fcmService.sendPushToTopicAndLog(actorNo, targetTopic, messageTitle, message.getMessage(), data, "chat");
                } else if (singleReceiver != null) {
                    log.info("[채팅푸시트레이스] 개인({}) 푸시 발송", singleReceiver.getUserId());
                    fcmService.sendPushToTokenAndLog(actorNo, singleReceiver.getUserId(), singleReceiver.getPushToken(), 
                            messageTitle, message.getMessage(), singleReceiver.getDeviceType(), data, "chat");
                }
            }

        } catch (Exception e) {
            log.error("메시지 저장 실패", e);
        }
        return message;
    }
}
