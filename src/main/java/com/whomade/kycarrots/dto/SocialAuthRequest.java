// src/main/java/com/whomade/kycarrots/api/auth/dto/SocialAuthRequest.java
package com.whomade.kycarrots.dto;

import lombok.Data;

@Data
public class SocialAuthRequest {
    private String provider;     // "KAKAO" | "NAVER" | "GOOGLE"
    private String providerUserId;
    private String accessToken;  // Kakao/Naver용
    private String idToken;      // Google OIDC(또는 Kakao OIDC 사용 시)
    private String deviceId;     // 선택
    private String appVersion;   // 선택
}
