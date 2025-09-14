package com.whomade.kycarrots.chat;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Long> {

    Optional<ChatRoomEntity> findByRoomId(String roomId);

    Optional<ChatRoomEntity> findByProductIdAndBuyerIdAndSellerId(Long productId, String buyerId, String sellerId);

    @Query("SELECT r FROM ChatRoomEntity r WHERE r.productId = :productId AND  r.sellerId = :userId")
    List<ChatRoomEntity> findByProductIdAndUserInvolved(@Param("productId") String productId, @Param("userId") String userId);

}