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
        int iReturn = 0;
        iReturn = opUserRepository.insertUser(user);
        if (!"PWD".equals(user.getProvider())) {
            opUserRepository.insertTbSocialAccount(user);
            iReturn++;
        }
        return iReturn;
    }

    public boolean existsByEmail(String email) {
        return opUserRepository.existsByEmail(email);
    }

    public int updatePushToken(OpUserVO user) {
        return opUserRepository.updatePushToken(user);
    }

    public int updateUser(OpUserVO user) {
        return opUserRepository.updateUser(user);
    }

    public OpUserVO fetchFcmToken(String userId) {
        return opUserRepository.fetchFcmToken(userId);
    }

    public OpUserVO fetchFcmTokenByUserNo(Long userNo) {
        return opUserRepository.fetchFcmTokenByUserNo(userNo);
    }

    public OpUserVO findEmailByNameAndPhone(OpUserVO opUserVO) {
        return opUserRepository.findEmailByNameAndPhone(opUserVO);
    }

    public int insertTbSocialAccount(OpUserVO opUserVO) {
        return opUserRepository.insertTbSocialAccount(opUserVO);
    }

    public int updatetouchLastLogin(DataMap param) {
        return opUserRepository.updatetouchLastLogin(param);
    }

    public OpUserVO selectUserBySocial(DataMap param) {
        return opUserRepository.selectUserBySocial(param);
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
            if (token == null)
                return Const.RESULT_NO_DATA; // 만료/무효

            if (!selector.equals(token.getSelector()))
                return Const.RESULT_NO_DATA; // 구링크

            String calc = sha256Base64(verifierPlain);
            if (!calc.equals(token.getVerifierHash()))
                return Const.RESULT_NO_DATA; // 검증 실패

            // 비번 저장 (SHA-512)
            String enc = EgovFileScrty.encryptSHA512(newPasswordRaw);
            DataMap inParam = new DataMap();
            inParam.put("member_id", userId);
            inParam.put("member_new_pw", enc);
            int updated = opUserRepository.updatePw(inParam);
            if (updated < 1)
                return Const.RESULT_NO_USER;

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

    public boolean existsSocialAccount(DataMap param) {
        return opUserRepository.existsSocialAccount(param);
    }

    public int deleteTbSocialAccount(DataMap param) {
        return opUserRepository.deleteTbSocialAccount(param);
    }

    public com.whomade.kycarrots.dto.BranchInfoVo selectBranchInfo(Long branchId) {
        return opUserRepository.selectBranchInfo(branchId);
    }

    public List<OpUserVO> selectUsersByBranchAndRole(String branchId, String memberCode) {
        return opUserRepository.selectUsersByBranchAndRole(branchId, memberCode);
    }
}
