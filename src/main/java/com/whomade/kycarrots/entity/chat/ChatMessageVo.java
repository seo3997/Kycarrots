package com.whomade.kycarrots.entity.chat;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatMessageVo {
    private String roomId;
    private String senderId;
    private String message;
}