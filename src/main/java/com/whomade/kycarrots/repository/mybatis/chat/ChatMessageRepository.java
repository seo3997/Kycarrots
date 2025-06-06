package com.whomade.kycarrots.repository.mybatis.chat;


import com.whomade.kycarrots.entity.chat.ChatMessageVo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * 채팅 메시지 저장용 MyBatis Repository
 */
@Repository
@RequiredArgsConstructor
@Transactional
public class ChatMessageRepository {

    private final ChatMessageMapper chatMessageMapper;

    public void save(ChatMessageVo messageVo) {
        chatMessageMapper.insertChatMessage(messageVo);
    }
}
