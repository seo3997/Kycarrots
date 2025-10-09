package com.whomade.kycarrots.email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor
public class PasswordResetToken {
    private String userId;            // USER_ID
    private String selector;          // SELECTOR
    private String verifierHash;      // VERIFIER_HASH (Base64 SHA-256)
    private LocalDateTime expiresAt;  // EXPIRES_AT (UTC 권장)
    private String usedYn;
}
