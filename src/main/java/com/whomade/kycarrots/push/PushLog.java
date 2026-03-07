package com.whomade.kycarrots.push;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_push_log")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PushLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "ACTOR_USER_NO")
    private Long actorUserNo;

    @Column(name = "TARGET_TYPE", nullable = false, length = 10)
    private String targetType;

    @Column(name = "TARGET_VALUE", nullable = false, length = 50)
    private String targetValue;

    @Column(name = "TARGET_ID", length = 50)
    private String targetId;

    @Column(name = "EVENT_TYPE", nullable = false, length = 30)
    private String eventType;

    @Column(name = "MESSAGE_TITLE", length = 100)
    private String messageTitle;

    @Lob
    @Column(name = "MESSAGE_BODY")
    private String messageBody;

    @Column(name = "PUSH_TYPE", length = 20)
    private String pushType;

    @Column(name = "SEND_STATUS", length = 10)
    private String sendStatus;

    @Column(name = "READ_YN", length = 1)
    private String readYn;

    // DB default (CURRENT_TIMESTAMP) 쓰려면 insertable=false, updatable=false
    @Column(name = "SENT_AT", insertable = false, updatable = false)
    private LocalDateTime sentAt;
}
