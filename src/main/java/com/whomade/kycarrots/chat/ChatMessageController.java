package com.whomade.kycarrots.chat;

import com.whomade.kycarrots.entity.chat.ChatMessageVo;
import com.whomade.kycarrots.repository.mybatis.chat.ChatMessageRepository;
import com.whomade.kycarrots.service.chat.ChatMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chatmessage")
@Slf4j
@RequiredArgsConstructor
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    /**
     * 채팅방 생성 또는 조회
     */
    @PostMapping("/add")
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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return message; // 원본 메시지 그대로 브로드캐스트
        }
    }

    @GetMapping("/list/{roomId}")
    public List<ChatMessageVo> getMessagesByRoomId(@PathVariable String roomId) {
        return chatMessageService.getMessagesByRoomId(roomId);
    }
}
