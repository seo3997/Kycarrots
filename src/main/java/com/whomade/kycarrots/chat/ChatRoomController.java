package com.whomade.kycarrots.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    /**
     * 채팅방 생성 또는 조회
     */
    @PostMapping("/room")
    public ResponseEntity<ChatRoomEntity> createOrGetRoom(
            @RequestParam Long productId,
            @RequestParam String buyerId,
            @RequestParam String sellerId) {
        ChatRoomEntity room = chatRoomService.createOrGetRoom(productId, buyerId, sellerId);
        return ResponseEntity.ok(room);
    }

    /**
     * 사용자가 참여한 모든 채팅방 목록 조회
     */
    @GetMapping("/rooms/{userId}")
    public ResponseEntity<List<ChatRoomEntity>> getUserChatRooms(@PathVariable String userId) {
        List<ChatRoomEntity> rooms = chatRoomService.getUserChatRooms(userId);
        return ResponseEntity.ok(rooms);
    }
}
