package com.whomade.kycarrots.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WebSocketUserTracker {

    private final SimpUserRegistry userRegistry;

    public boolean isUserOnline(String userId) {
        return userRegistry.getUser(userId) != null;
    }
}
