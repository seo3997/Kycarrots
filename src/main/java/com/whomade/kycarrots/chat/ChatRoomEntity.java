package com.whomade.kycarrots.chat;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_chat_room",
        uniqueConstraints = @UniqueConstraint(columnNames = "room_id"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "buyer_id", nullable = false)
    private String buyerId;

    @Column(name = "seller_id", nullable = false)
    private String sellerId;

    @Column(name = "room_id", nullable = false, unique = true)
    private String roomId;
}
