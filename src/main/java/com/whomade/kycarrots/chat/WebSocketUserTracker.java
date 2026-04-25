package com.whomade.kycarrots.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Component;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class WebSocketUserTracker {

    private final SimpUserRegistry userRegistry;
    // 명시적으로 채팅방에 머물고 있는 유저를 트래킹하는 맵 (UserId -> RoomId)
    private final ConcurrentHashMap<String, String> activeChatters = new ConcurrentHashMap<>();

    public void addChatter(String userId, String roomId) {
        activeChatters.put(userId, roomId);
    }

    public void removeChatter(String userId) {
        if (userId != null) {
            activeChatters.remove(userId);
        }
    }

    public void removeChatters(List<String> userIds) {
        if (userIds != null) {
            for (String id : userIds) {
                activeChatters.remove(id);
            }
        }
    }

    public boolean isUserOnline(String userId) {
        // 명시적으로 채팅 화면에 위치한 유저(Active Chatter)만 온라인으로 간주합니다.
        // 이렇게 하면 앱을 끄거나 화면을 나가는 즉시 '오프라인'으로 판정되어 푸시가 정상 발송됩니다.
        return activeChatters.containsKey(userId);
    }
}
