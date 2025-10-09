package com.whomade.kycarrots.service.member;

import com.whomade.kycarrots.email.PasswordResetResult;
import com.whomade.kycarrots.email.PasswordResetToken;
import com.whomade.kycarrots.entity.member.OpUserAuthorVO;
import com.whomade.kycarrots.entity.member.OpUserVO;
import com.whomade.kycarrots.framework.common.constant.Const;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.framework.common.util.EgovFileScrty;
import com.whomade.kycarrots.repository.mybatis.member.OpUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.List;

/**
 * @author: ADMIN
 * @version: 1.0.0
 * @since: 2024-06-23
 */
@Service
@RequiredArgsConstructor
public class OpUserService {
    private final OpUserRepository opUserRepository;


    public OpUserVO seelectUser(DataMap param) {
        return opUserRepository.seelectUser(param);
    }

    public int insertUser(OpUserVO user) {
        return opUserRepository.insertUser(user);
    }

    public boolean existsByEmail(String email) {
        return opUserRepository.existsByEmail(email);
    }

    public int updatePushToken(OpUserVO user) {
        return opUserRepository.updatePushToken(user);
    }

    public OpUserVO fetchFcmToken(String userId) {
        return opUserRepository.fetchFcmToken(userId);
    }

    public List<OpUserVO> selectActiveWholesalers(String memberCode) {
        return opUserRepository.selectActiveWholesalers(memberCode);
    }

    // 추가: 기본 중간센터 조회/설정 (USER_ID 기준)
    public Long findWholesalerNoByUserId(String userId) {
        return opUserRepository.findWholesalerNoByUserId(userId);
    }
    // 추가: 기본 중간센터 조회/설정 (USER_ID 기준)

    public Long findWholesalerNoByUserNo(long userNo) {
        return opUserRepository.findWholesalerNoByUserNo(userNo);
    }

    public int updateDefaultWholesalerByUserId(OpUserVO user) {
        return opUserRepository.updateDefaultWholesalerByUserId(user);
    }

    public OpUserVO findEmailByNameAndPhone(OpUserVO opUserVO) {
        return opUserRepository.findEmailByNameAndPhone(opUserVO);
    }

    public OpUserVO selectByEmail(String email) {
        return opUserRepository.selectByEmail(email);
    }

    /**
     * @return Const.RESULT_CODE_200 (성공)
     *         Const.RESULT_NO_DATA (604: 토큰 만료/무효/구링크/검증실패)
     *         Const.RESULT_NO_USER (601: 사용자 없음)
     *         Const.RESULT_CODE_ERR (0: 기타 오류)
     */
    public String verifyAndChange(String userId, String selector, String verifierPlain, String newPasswordRaw) {
        try {
            var nowUtc = java.time.LocalDateTime.now(java.time.ZoneOffset.UTC);
            var token = opUserRepository.selectValidForUser(userId, nowUtc);
            if (token == null) return Const.RESULT_NO_DATA;             // 만료/무효

            if (!selector.equals(token.getSelector())) return Const.RESULT_NO_DATA; // 구링크

            String calc = sha256Base64(verifierPlain);
            if (!calc.equals(token.getVerifierHash())) return Const.RESULT_NO_DATA; // 검증 실패

            // 비번 저장 (SHA-512)
            String enc = EgovFileScrty.encryptSHA512(newPasswordRaw);
            DataMap inParam = new DataMap();
            inParam.put("member_id", userId);
            inParam.put("member_new_pw", enc);
            int updated = opUserRepository.updatePw(inParam);
            if (updated < 1) return Const.RESULT_NO_USER;

            // 1회성 사용 처리
            opUserRepository.markUsed(userId);
            return Const.RESULT_CODE_200;

        } catch (Exception e) {
            return Const.RESULT_CODE_ERR;
        }
    }


    private static String sha256Base64(String s) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(s.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(digest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
