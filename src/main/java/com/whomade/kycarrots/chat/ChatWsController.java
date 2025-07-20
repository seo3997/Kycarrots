package com.whomade.kycarrots.chat;


import com.whomade.kycarrots.entity.chat.ChatMessageVo;
import com.whomade.kycarrots.push.FcmService;
import com.whomade.kycarrots.repository.mybatis.chat.ChatMessageRepository;
import com.whomade.kycarrots.service.chat.ChatMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatWsController {

    private final ChatMessageService chatMessageService;
    @Autowired
    private WebSocketUserTracker userTracker;
    @Autowired
    private FcmService fcmService;

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

            String receiverId = "sel1@gmail.com";


            log.info("메시지 저장 성공");
            if (!userTracker.isUserOnline(receiverId)) {
                //log.info("receiver {} 는 접속 중이 아님. 푸시 전송 시도", receiverId);

                //String fcmToken = fetchFcmToken(receiverId); // 직접 구현 필요
                String fcmToken = "cZFtxLX6QWSjMT8GpMyXEh:APA91bH8VmxPN1UTOxiU3aNAWh8i6-2V15_862EfxkrV5KpcZ-j29cxr28MEclGU17s-HnS0-mrToHbRZxVpQDWdzjOKafm0GSvrHhefzR5VugFFW6-e8nE"; // 직접 구현 필요
                if (fcmToken != null) {
                    fcmService.sendPush(fcmToken, "새 메시지", message.getMessage());
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
