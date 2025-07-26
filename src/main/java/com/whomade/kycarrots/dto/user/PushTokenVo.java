package com.whomade.kycarrots.dto.user;

import lombok.Data;

@Data
public class PushTokenVo {
    private String userId;
    private String pushToken;
    private String deviceType;
}
