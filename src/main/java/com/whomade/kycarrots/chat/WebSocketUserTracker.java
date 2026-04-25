package com.whomade.kycarrots.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Component;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;

@Component
@RequiredArgsConstructor
public class WebSocketUserTracker {

    private final SimpUserRegistry userRegistry;
    private final com.whomade.kycarrots.service.member.OpUserService opUserService;

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
        return activeChatters.containsKey(userId);
    }

    /**
     * 본사 담당자 중 한 명이라도 온라인인지 확인 (branchId='2')
     */
    public boolean isAnyHqOnline() {
        List<com.whomade.kycarrots.entity.member.OpUserVO> hqStaff = opUserService.selectUsersByRole(com.whomade.kycarrots.framework.common.constant.Const.ROLE_SELL);
        if (hqStaff == null) return false;
        
        for (com.whomade.kycarrots.entity.member.OpUserVO staff : hqStaff) {
            if (isUserOnline(staff.getUserId())) return true;
        }
        return false;
    }

    /**
     * 특정 지점 담당자 중 한 명이라도 온라인인지 확인
     */
    public boolean isAnyBranchStaffOnline(String branchId) {
        List<com.whomade.kycarrots.entity.member.OpUserVO> branchStaff = opUserService.selectUsersByBranchAndRole(branchId, com.whomade.kycarrots.framework.common.constant.Const.ROLE_PROJ);
        if (branchStaff == null) return false;
        
        for (com.whomade.kycarrots.entity.member.OpUserVO staff : branchStaff) {
            if (isUserOnline(staff.getUserId())) return true;
        }
        return false;
    }
}
