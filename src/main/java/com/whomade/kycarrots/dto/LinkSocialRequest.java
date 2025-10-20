// src/main/java/com/whomade/kycarrots/api/auth/dto/LinkSocialRequest.java
package com.whomade.kycarrots.dto;

import lombok.Data;

@Data
public class LinkSocialRequest {
    private String userId;            // 기존 회원 번호(필수)
    private String userNo;            // 기존 회원 번호(필수)
    private String provider;        // "KAKAO" | "NAVER" | "GOOGLE" ...
    private String providerUserId;  // 소셜의 고유 사용자 ID (예: 카카오 user.id)
}
