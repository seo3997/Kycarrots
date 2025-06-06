package com.whomade.kycarrots.chat;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_chat_message")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_id", nullable = false)
    private String roomId;

    @Column(name = "sender_id", nullable = false)
    private String senderId;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Column(name = "created_at", insertable = false, updatable = false,
            columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "is_read", columnDefinition = "TINYINT(1) DEFAULT 0")
    private Boolean isRead;
}
