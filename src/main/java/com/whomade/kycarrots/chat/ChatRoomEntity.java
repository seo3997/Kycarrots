package com.whomade.kycarrots.chat;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_chat_room", uniqueConstraints = @UniqueConstraint(columnNames = "room_id"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "PRODUCT_ID", nullable = false)
    private Long productId;

    @Column(name = "BUYER_ID", nullable = false)
    private String buyerId;

    @Column(name = "BRANCH_ID", nullable = false)
    private String branchId;

    @Column(name = "ROOM_ID", nullable = false, unique = true)
    private String roomId;
}
