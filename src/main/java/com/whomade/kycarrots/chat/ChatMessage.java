package com.whomade.kycarrots.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Lombok
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    private String id;
    private String senderId;
    private String message;
    private String roomId;
    private String type; // "text", "image" 등
    private String time;

    // 생성자
    public ChatMessage(String senderId, String message, String roomId, String type, String time) {
        this.senderId = senderId;
        this.message = message;
        this.roomId = roomId;
        this.type = type;
        this.time = time;
    }


}
