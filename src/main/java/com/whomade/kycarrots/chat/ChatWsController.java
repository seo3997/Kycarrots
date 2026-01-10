package com.whomade.kycarrots.chat;


import com.whomade.kycarrots.entity.chat.ChatMessageVo;
import com.whomade.kycarrots.entity.member.OpUserVO;
import com.whomade.kycarrots.entity.product.TnProductVo;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.push.FcmService;
import com.whomade.kycarrots.repository.mybatis.chat.ChatMessageRepository;
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

            String buyerId = chatRoom.getBuyerId();
            String sellerId = chatRoom.getSellerId();

            String receiverId = "";
            String messgeTitle = "";

            if (senderId.equals(buyerId)) {
                receiverId = sellerId;
            } else if (senderId.equals(sellerId)) {
                receiverId = buyerId;
            } else {
                // 예외 상황: senderId가 둘 다 아닌 경우
                log.warn("senderId가 이 채팅방에 속하지 않음: {}", senderId);
                receiverId = ""; // 또는 throw 예외
            }

            log.info("receiverId:["+receiverId+"]");

            log.info("메시지 저장 성공");
            if (!userTracker.isUserOnline(receiverId) && !receiverId.equals("")) {
                //log.info("receiver {} 는 접속 중이 아님. 푸시 전송 시도", receiverId);

                Long productId = chatRoom.getProductId();
                DataMap param = new DataMap();
                param.put("productId", productId);
                param.put("userNo", "0");
                TnProductVo product = tnProductService.getProduct(param);
                messgeTitle = product.getTitle() + "  채팅메시지";
                String pushId = UUID.randomUUID().toString(); // ✅ 푸시 고유키

                // 2. FCM 데이터 payload 구성
                Map<String, String> data = new HashMap<>();
                data.put("id", pushId);                        // ✅ 핵심(중복방지용)
                data.put("roomId", roomId);
                data.put("buyerId", buyerId);
                data.put("sellerId", sellerId);
                data.put("productId", productId != null ? productId.toString() : "");
                data.put("type", "chat");
                data.put("msg", message.getMessage());
                data.put("title", messgeTitle); // 알림 제목
                data.put("body", message.getMessage());   // 알림 내

                OpUserVO opUserVO = opUserService.fetchFcmToken(receiverId); // 직접 구현 필요
                String fcmToken = opUserVO.getPushToken();
                String deviceType = opUserVO.getDeviceType(); // "ANDROID", "IOS"
                log.info("fcmToken:["+fcmToken+"]");
                if (fcmToken != null) {
                    fcmService.sendPushToUser(
                            deviceType,                   // ✅ IOS / ANDROID
                            fcmToken,
                            messgeTitle,
                            message.getMessage(),
                            data
                    );

                } else {
                    log.warn("푸시 전송 실패: FCM 토큰 없음");
                }

                return null; // 👈 메시지 브로드캐스트하지 않음
            } else {
                log.info("receiver {} 는 현재 접속 중", receiverId);
            }



        } catch (Exception e) {
            log.error("메시지 저장 실패", e);
        }
        return  message;
    }
}
