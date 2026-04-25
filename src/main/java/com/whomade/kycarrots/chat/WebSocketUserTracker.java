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
        activeChatters.remove(userId);
    }

    // 특정 지점의 특정 권한 유저들을 모두 제거 (ROLE_SELL 용)
    public void removeChattersByBranchAndRole(String branchId, String role) {
        // 실제 구현 시에는 유저의 지점/권한 정보를 알아야 하므로 
        // activeChatters에 저장된 정보를 순회하며 제거하거나, 
        // 컨트롤러에서 대상 유저 리스트를 받아와서 지울 수 있습니다.
        // 여기서는 안전하게 userId 기반으로 순회하며 처리하는 방식을 제안합니다.
    }

    public void removeChatters(List<String> userIds) {
        userIds.forEach(activeChatters::remove);
    }

    public boolean isUserOnline(String userId) {
        // 명시적으로 채팅 화면에 위치한 유저(Active Chatter)만 온라인으로 간주합니다.
        // 이렇게 하면 앱을 끄거나 화면을 나가는 즉시 '오프라인'으로 판정되어 푸시가 정상 발송됩니다.
        return activeChatters.containsKey(userId);
    }
}
