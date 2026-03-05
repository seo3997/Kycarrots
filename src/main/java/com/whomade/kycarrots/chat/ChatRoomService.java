package com.whomade.kycarrots.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    /**
     * 상품 + 구매자 + 판매자 기준으로 채팅방을 조회하거나 없으면 생성
     */
    public ChatRoomEntity createOrGetRoom(Long productId, String buyerId, String branchId) {
        Optional<ChatRoomEntity> existing = chatRoomRepository
                .findByProductIdAndBuyerIdAndBranchId(productId, buyerId, branchId);

        return existing.orElseGet(() -> {
            String roomId = generateRoomId(productId, buyerId, branchId);
            ChatRoomEntity newRoom = ChatRoomEntity.builder()
                    .productId(productId)
                    .buyerId(buyerId)
                    .branchId(branchId)
                    .roomId(roomId)
                    .build();
            return chatRoomRepository.save(newRoom);
        });
    }

    /**
     * 현재 로그인 유저가 참여한 모든 채팅방 조회
     */
    public List<ChatRoomEntity> getUserChatRooms(String productId, String userId) {
        return chatRoomRepository.findByProductIdAndUserInvolved(productId, userId);
    }

    /**
     * 상품 ID + 사용자 ID 기준 고유 roomId 생성
     */
    private String generateRoomId(Long productId, String id1, String id2) {
        return productId + "_" + id1 + "_" + id2;
    }

    // Optional로 리턴!
    public Optional<ChatRoomEntity> findByRoomId(String roomId) {
        return chatRoomRepository.findByRoomId(roomId);
    }
}
