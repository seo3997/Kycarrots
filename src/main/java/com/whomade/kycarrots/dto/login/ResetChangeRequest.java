package com.whomade.kycarrots.dto.login;

import lombok.Data;

@Data
public class ResetChangeRequest {
    private String newPassword;
    private String confirmPassword;
}
