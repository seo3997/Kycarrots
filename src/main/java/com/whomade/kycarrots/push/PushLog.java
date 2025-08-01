package com.whomade.kycarrots.push;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_push_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PushLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Column(name = "TARGET_USER_ID", length = 20)
    private String TARGET_USER_ID;

    @Column(name = "PRODUCT_ID", length = 20)
    private String PRODUCT_ID;

    @Column(name = "MESSAGE_TITLE", length = 100)
    private String MESSAGE_TITLE;

    @Column(name = "MESSAGE_BODY", columnDefinition = "TEXT")
    private String MESSAGE_BODY;

    @Column(name = "PUSH_TYPE", length = 20)
    private String PUSH_TYPE;

    @Column(name = "SEND_STATUS", length = 10)
    private String SEND_STATUS;

    @Column(name = "SENT_AT")
    private LocalDateTime SENT_AT;

    @Column(name = "READ_YN", length = 1)
    private String READ_YN = "N";
}
