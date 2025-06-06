package com.whomade.kycarrots.chat;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Long> {

    Optional<ChatRoomEntity> findByRoomId(String roomId);

    Optional<ChatRoomEntity> findByProductIdAndBuyerIdAndSellerId(Long productId, String buyerId, String sellerId);

    List<ChatRoomEntity> findAllByBuyerIdOrSellerId(String buyerId, String sellerId);
}