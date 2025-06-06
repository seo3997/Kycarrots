package com.whomade.kycarrots.repository.mybatis.chat;


import com.whomade.kycarrots.entity.chat.ChatMessageVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatMessageMapper {
    void insertChatMessage(ChatMessageVo message);
    List<ChatMessageVo> findMessagesByRoomId(String roomId);
}
