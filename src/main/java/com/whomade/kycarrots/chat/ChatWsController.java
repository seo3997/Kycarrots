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
            ChatMessageVo chatMessageVo = ChatMessageVo.builder()
                    .roomId(roomId)
                    .senderId(message.getSenderId())
                    .message(message.getMessage())
                    .build();
            chatMessageService.insertChatMessage(chatMessageVo); // MyBatis 방식 저장

            // 1. ChatRoom 정보 조회 (roomId로 또는 productId, buyerId, sellerId로)
            Optional<ChatRoomEntity> chatRoomOpt = chatRoomService.findByRoomId(roomId);
            if (!chatRoomOpt.isPresent()) {
                log.warn("채팅방 정보가 없습니다. roomId: {}", roomId);
                return null;
            }

            String senderId = message.getSenderId();
            ChatRoomEntity chatRoom = chatRoomOpt.get();

            String id1 = chatRoom.getBuyerId(); // Buyer or Branch
            String id2 = chatRoom.getBranchId(); // Branch or HQ

            log.info("채팅방 정보 확인 - roomId: {}, id1: {}, id2: {}, senderId: {}", roomId, id1, id2, senderId);

            String targetTopic = null;
            OpUserVO singleReceiver = null;

            if (senderId.equals(id1)) {
                log.info("발신자가 id1({})입니다. 타겟은 id2({})", id1, id2);
                if ("2".equals(id2)) {
                    log.info("id2가 본사(2)입니다. 본사(ROLE_SELL) 토픽으로 발송합니다.");
                    targetTopic = "ROLE_SELL";
                } else {
                    log.info("id2가 지점({})입니다. 지점(ROLE_PROJ) 토픽으로 발송합니다.", id2);
                    targetTopic = "BRANCH_" + id2 + "_ROLE_PROJ";
                }
            } else {
                log.info("발신자가 id2({})입니다. 타겟은 id1({})", id2, id1);
                if ("2".equals(id2)) {
                    log.info("발신자 id2가 본사(2)입니다. 타겟 지점({})의 (ROLE_PROJ) 토픽으로 발송합니다.", id1);
                    targetTopic = "BRANCH_" + id1 + "_ROLE_PROJ";
                } else {
                    log.info("발신자 id2가 지점입니다. 타겟 단일 구매자({})의 FCM 토큰을 검색합니다.", id1);
                    singleReceiver = opUserService.fetchFcmToken(id1);
                }
            }

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

            // 1. 토픽 발송 (본사 또는 지점 담당자들들)
            if (targetTopic != null) {
                log.info("토픽 푸시 발송 - topic: {}", targetTopic);
                OpUserVO sender = opUserService.fetchFcmToken(senderId);
                Long actorNo = (sender != null && sender.getUserNo() != null) ? Long.parseLong(sender.getUserNo()) : 0L;
                fcmService.sendPushToTopicAndLog(actorNo, targetTopic, messageTitle, message.getMessage(), data, "chat");
            } 
            // 2. 단일 발송 (구매자)
            else if (singleReceiver != null) {
                String rcvUserId = singleReceiver.getUserId();
                if (!userTracker.isUserOnline(rcvUserId)) {
                    log.info("단일 푸시 발송 - userId: {}", rcvUserId);
                    fcmService.sendPushToUserAndLog(0L, singleReceiver.getDeviceType(), singleReceiver.getUserNo(), 
                        singleReceiver.getPushToken(), messageTitle, message.getMessage(), roomId, "chat", data);
                }
            }

        } catch (Exception e) {
            log.error("메시지 저장 실패", e);
        }
        return message;
    }
}
