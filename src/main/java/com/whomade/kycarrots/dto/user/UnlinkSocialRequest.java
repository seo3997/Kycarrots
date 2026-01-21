package com.whomade.kycarrots.dto.user;

import lombok.Getter;
import lombok.Setter;

// dto/UnlinkSocialRequest.java
@Getter
@Setter
public class UnlinkSocialRequest {
    private String provider;
    private String providerUserId;
}
