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

            List<OpUserVO> receivers = new java.util.ArrayList<>();
            String messageTitle = "";

            if (senderId.equals(id1)) {
                // Sender is id1 (Buyer or Branch). Target is id2.
                // If id2 is HQ ("BR_0002"), target role is ROLE_SELL.
                // Otherwise, target role is ROLE_PROJ.
                if ("BR_0002".equals(id2)) {
                    receivers = opUserService.selectUsersByBranchAndRole(id2, "ROLE_SELL");
                } else {
                    receivers = opUserService.selectUsersByBranchAndRole(id2, "ROLE_PROJ");
                }
            } else {
                // Sender is id2 (Branch or HQ). Target is id1.
                // If id1 is a branch (starts with 'BR_'), target role is ROLE_PROJ.
                // Otherwise, it's a single buyer.
                if (id1.startsWith("BR_")) {
                    receivers = opUserService.selectUsersByBranchAndRole(id1, "ROLE_PROJ");
                } else {
                    OpUserVO buyer = opUserService.fetchFcmToken(id1);
                    if (buyer != null)
                        receivers.add(buyer);
                }
            }

            log.info("메시지 저장 성공");

            Long productId = chatRoom.getProductId();
            DataMap param = new DataMap();
            param.put("productId", productId);
            param.put("userNo", "0");
            TnProductVo product = tnProductService.getProduct(param);
            messageTitle = (product != null ? product.getTitle() : "알림") + " 채팅메시지";

            for (OpUserVO receiver : receivers) {
                if (receiver != null && !receiver.getUserId().equals(senderId)
                        && !userTracker.isUserOnline(receiver.getUserId())) {
                    String pushId = UUID.randomUUID().toString();
                    Map<String, String> data = new HashMap<>();
                    data.put("id", pushId);
                    data.put("roomId", roomId);
                    data.put("buyerId", id1);
                    data.put("branchId", id2);
                    data.put("productId", productId != null ? productId.toString() : "");
                    data.put("type", "chat");
                    data.put("msg", message.getMessage());
                    data.put("title", messageTitle);
                    data.put("body", message.getMessage());

                    String fcmToken = receiver.getPushToken();
                    String deviceType = receiver.getDeviceType();
                    if (fcmToken != null) {
                        fcmService.sendPushToUser(deviceType, fcmToken, messageTitle, message.getMessage(), data);
                    }
                }
            }

        } catch (Exception e) {
            log.error("메시지 저장 실패", e);
        }
        return message;
    }
}
