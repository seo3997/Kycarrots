package com.whomade.kycarrots.service.chat;

import com.whomade.kycarrots.entity.chat.ChatMessageVo;
import com.whomade.kycarrots.repository.mybatis.chat.ChatMessageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageMapper chatMessageMapper;

    public void insertChatMessage(ChatMessageVo message) {
        chatMessageMapper.insertChatMessage(message);
    }

    public List<ChatMessageVo> getMessagesByRoomId(String roomId) {
        return chatMessageMapper.findMessagesByRoomId(roomId);
    }
}
