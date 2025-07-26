package com.whomade.kycarrots.entity.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpUserVO {
    private String userNo;              // USER_NO: 사용자 고유 번호
    private String userId;              // USER_ID: 사용자 아이디
    private String password;            // PASSWORD: 비밀번호
    private String userNm;              // USER_NM: 사용자 이름
    private String cttpcSeCode;         // CTTPC_SE_CODE: 통신사 구분 코드
    private String cttpc;               // CTTPC: 통신사 정보
    private String email;               // EMAIL: 이메일
    private String areaCode;            // AREA_CODE: 지역 코드
    private String areaCodeNm;            // AREA_CODE: 지역 코드
    private String areaSeCodeS;         // AREA_SE_CODE_S: 1차 지역 구분 코드
    private String areaSeCodeSNm;         // AREA_SE_CODE_S: 1차 지역 구분 코드
    private String areaSeCodeD;         // AREA_SE_CODE_D: 2차 지역 구분 코드
    private String userSttusCode;       // USER_STTUS_CODE: 사용자 상태 코드
    private String loginDt;             // LOGIN_DT: 마지막 로그인 일시
    private String userAge;             // USER_AGE: 사용자 연령 또는 연령대
    private String birthDate;           // BIRTH_DATE: 생년월일
    private String uniqueIdentifier;    // UNIQUE_IDENTIFIER: 고유 식별자 (CI)
    private String deviceId;            // DEVICE_ID: 디바이스 식별자
    private String duplicateIdentifier; // DUPLICATE_IDENTIFIER: 동일인 식별정보 (DI)
    private String gender;              // GENDER: 성별
    private String memberCode;          // MEMBER_CODE: 회원 구분 코드
    private String citizenshipType;     // CITIZENSHIP_TYPE: 내/외국인 구분
    private String passwordHash;        // PASSWORD_HASH: 해시 처리된 비밀번호
    private String referrerId;          // REFERRER_ID: 추천인 ID (옵션)
    private String registerNo;          // REGISTER_NO: 등록자 번호
    private String registDt;            // REGIST_DT: 등록 일시
    private String updusrNo;            // UPDUSR_NO: 수정자 번호
    private String updtDt;              // UPDT_DT: 수정 일시
    private String pushToken;
    private String deviceType;
}
