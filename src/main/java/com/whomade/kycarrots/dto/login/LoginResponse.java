package com.whomade.kycarrots.dto.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private int resultCode;
    private String token;
    private String login_idx;
    private String login_si;
    private String login_gu;
    private String login_sex;
    private String login_age;
    private String login_nm;
    private String member_code;
    private String login_id;
    private String login_cd;
    private String login_social_id;
}
