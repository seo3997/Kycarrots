package com.whomade.kycarrots.entity.chat;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatMessageVo {
    private Long id;
    private String roomId;
    private String senderId;
    private String message;
    private String createdAt;
    private Boolean isRead;
    private String time;
}