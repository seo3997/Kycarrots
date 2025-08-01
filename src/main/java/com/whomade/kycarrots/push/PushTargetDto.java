package com.whomade.kycarrots.push;

import lombok.Data;

/**
 * FCM 푸시 대상자 정보를 담는 DTO
 * - tb_product.WHOLESALER_NO → op_user.USER_NO join 결과용
 */
@Data
public class PushTargetDto {
    private String userNo;        // 사용자 번호
    private String deviceType;  // 기기 종류 (ANDROID, IOS 등)
    private String pushToken;   // FCM 토큰
}
