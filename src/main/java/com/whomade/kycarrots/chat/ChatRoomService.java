package com.whomade.kycarrots.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    /**
     * 상품 + 구매자 + 판매자 기준으로 채팅방을 조회하거나 없으면 생성
     */
    public ChatRoomEntity createOrGetRoom(Long productId, String buyerId, String sellerId) {
        Optional<ChatRoomEntity> existing = chatRoomRepository
                .findByProductIdAndBuyerIdAndSellerId(productId, buyerId, sellerId);

        return existing.orElseGet(() -> {
            String roomId = generateRoomId(productId, buyerId, sellerId);
            ChatRoomEntity newRoom = ChatRoomEntity.builder()
                    .productId(productId)
                    .buyerId(buyerId)
                    .sellerId(sellerId)
                    .roomId(roomId)
                    .build();
            return chatRoomRepository.save(newRoom);
        });
    }

    /**
     * 현재 로그인 유저가 참여한 모든 채팅방 조회
     */
    public List<ChatRoomEntity> getUserChatRooms(String productId,String userId) {
        return chatRoomRepository.findByProductIdAndUserInvolved(productId,userId);
    }

    /**
     * 상품 ID + 사용자 ID 기준 고유 roomId 생성
     */
    private String generateRoomId(Long productId, String buyerId, String sellerId) {
        return productId + "_" + buyerId + "_" + sellerId;
    }

    // Optional로 리턴!
    public Optional<ChatRoomEntity> findByRoomId(String roomId) {
        return chatRoomRepository.findByRoomId(roomId);
    }
}
