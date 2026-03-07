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

            log.info("채팅방 정보 확인 - roomId: {}, id1(Buyer/Branch): {}, id2(Branch/HQ): {}, senderId: {}", roomId, id1,
                    id2, senderId);

            List<OpUserVO> receivers = new java.util.ArrayList<>();
            String messageTitle = "";

            if (senderId.equals(id1)) {
                log.info("발신자가 id1({})입니다. 타겟은 id2({})", id1, id2);
                // Sender is id1 (Buyer or Branch). Target is id2.
                // If id2 is HQ ("2"), target role is ROLE_SELL.
                // Otherwise, target role is ROLE_PROJ.
                if ("2".equals(id2)) {
                    log.info("id2가 본사(2)입니다. 본사(ROLE_SELL) 유저를 검색합니다.");
                    receivers = opUserService.selectUsersByBranchAndRole(id2, "ROLE_SELL");
                } else {
                    log.info("id2가 지점({})입니다. 지점(ROLE_PROJ) 유저를 검색합니다.", id2);
                    receivers = opUserService.selectUsersByBranchAndRole(id2, "ROLE_PROJ");
                }
            } else {
                log.info("발신자가 id2({})입니다. 타겟은 id1({})", id2, id1);
                // Sender is id2 (Branch or HQ). Target is id1.
                // If id2 is HQ ("2"), id1 is a branch, target role is ROLE_PROJ.
                // Otherwise, it's a single buyer.
                if ("2".equals(id2)) {
                    log.info("발신자 id2가 본사(2)입니다. 타겟 지점({})의 (ROLE_PROJ) 유저를 검색합니다.", id1);
                    receivers = opUserService.selectUsersByBranchAndRole(id1, "ROLE_PROJ");
                } else {
                    log.info("발신자 id2가 지점입니다. 타겟 단일 구매자({})의 FCM 토큰을 검색합니다.", id1);
                    OpUserVO buyer = opUserService.fetchFcmToken(id1);
                    if (buyer != null) {
                        log.info("구매자({}) 정보가 존재하여 수신자에 추가합니다.", id1);
                        receivers.add(buyer);
                    } else {
                        log.warn("구매자({}) 정보를 찾을 수 없습니다.", id1);
                    }
                }
            }

            log.info("메시지 저장 성공, 검색된 총 수신자 수: {}", receivers.size());

            Long productId = chatRoom.getProductId();
            DataMap param = new DataMap();
            param.put("productId", productId);
            param.put("userNo", "0");
            TnProductVo product = tnProductService.getProduct(param);
            messageTitle = (product != null ? product.getTitle() : "알림") + " 채팅메시지";
            log.info("푸시 알림 타이틀: {}", messageTitle);

            for (OpUserVO receiver : receivers) {
                if (receiver == null) {
                    log.warn("수신자(receiver) 객체가 null입니다. 건너뜁니다.");
                    continue;
                }

                String rcvUserId = receiver.getUserId();
                boolean isSender = rcvUserId.equals(senderId);
                boolean isOnline = userTracker.isUserOnline(rcvUserId);
                log.info("수신자 검사 - userId: {}, isSender: {}, isOnline: {}", rcvUserId, isSender, isOnline);

                if (!isSender && !isOnline) {
                    log.info("조건 만족: 수신자({})에게 푸시 발송 준비", rcvUserId);
                    String pushId = UUID.randomUUID().toString();
                    Map<String, String> data = new HashMap<>();
                    data.put("id", pushId);
                    data.put("targetId", roomId);
                    data.put("type", "chat");
                    data.put("msg", message.getMessage());
                    data.put("title", messageTitle);
                    data.put("body", message.getMessage());

                    String fcmToken = receiver.getPushToken();
                    String deviceType = receiver.getDeviceType();

                    if (fcmToken != null && !fcmToken.isEmpty()) {
                        log.info("푸시 발송 - userId: {}, token: {}, deviceType: {}", rcvUserId, fcmToken, deviceType);
                        fcmService.sendPushToUser(deviceType, fcmToken, messageTitle, message.getMessage(), data);
                    } else {
                        log.warn("푸시 발송 실패 - 수신자({})의 FCM 토큰이 없거나 비어있습니다.", rcvUserId);
                    }
                } else {
                    log.info("푸시 발송 제외 - userId: {} (isSender: {}, isOnline: {})", rcvUserId, isSender, isOnline);
                }
            }

        } catch (Exception e) {
            log.error("메시지 저장 실패", e);
        }
        return message;
    }
}
