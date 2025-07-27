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

    @GetMapping("/rooms/{productId}/{userId}")
    public ResponseEntity<List<ChatRoomEntity>> getUserChatRooms(
            @PathVariable String productId,
            @PathVariable String userId) {

        List<ChatRoomEntity> rooms = chatRoomService.getUserChatRooms(productId, userId);
        return ResponseEntity.ok(rooms);
    }

}
