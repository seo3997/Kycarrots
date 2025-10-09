package com.whomade.kycarrots.email;

public enum PasswordResetResult {
    OK,                // 정상: 메일 발송 성공
    USER_NOT_FOUND,    // 해당 이메일 사용자 없음
    EMAIL_SEND_FAILED  // 메일 발송 중 오류
}
