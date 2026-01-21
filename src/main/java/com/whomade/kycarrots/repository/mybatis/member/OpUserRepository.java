package com.whomade.kycarrots.repository.mybatis.member;

import com.whomade.kycarrots.email.PasswordResetToken;
import com.whomade.kycarrots.entity.member.OpUserAuthorVO;
import com.whomade.kycarrots.entity.member.OpUserVO;
import com.whomade.kycarrots.framework.common.object.DataMap;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: ADMIN
 * @version: 1.0.0
 * @since: 2024-06-23
 */
@Repository
@RequiredArgsConstructor
@Transactional
public class OpUserRepository {

    private final OpUserMapper opUserMapper;

    public OpUserVO seelectUser(DataMap param) {
        OpUserVO opUserVO = opUserMapper.seelectUser(param);
        return opUserVO;
    }
    public int insertUser(OpUserVO user) {
        return opUserMapper.insertUser(user);
    }

    public boolean existsByEmail(String email) {
        return opUserMapper.existsByEmail(email);
    }

    public int updatePushToken(OpUserVO user) {
        return opUserMapper.updatePushToken(user);
    }
    public OpUserVO fetchFcmToken(String userId) {
        return opUserMapper.fetchFcmToken(userId);
    }

    public List<OpUserVO> selectActiveWholesalers(String memberCode) {
        return opUserMapper.selectActiveWholesalers(memberCode);
    }

    public Long findWholesalerNoByUserId(String userId) {
        return opUserMapper.findWholesalerNoByUserId(userId);
    }
    public Long findWholesalerNoByUserNo(Long userNo) {
        return opUserMapper.findWholesalerNoByUserNo(userNo);
    }
    public int updateDefaultWholesalerByUserId(OpUserVO user) {
        return opUserMapper.updateDefaultWholesalerByUserId(user);
    }

    public OpUserVO findEmailByNameAndPhone(OpUserVO opUserVO) {
        return opUserMapper.findEmailByNameAndPhone(opUserVO);
    }

    public OpUserVO selectByEmail(@Param("email") String email) {
        return opUserMapper.selectByEmail(email);
    }

    public PasswordResetToken selectValidForUser(@Param("userId") String userId,@Param("now") LocalDateTime now) {
        return opUserMapper.selectValidForUser(userId, now);
    }
    // 1회성 사용 처리
    public int markUsed(@Param("userId") String userId) {
        return opUserMapper.markUsed(userId);
    }

    public int updatePw(DataMap param) {
        return opUserMapper.updatePw(param);
    }

    public int insertTbSocialAccount(OpUserVO opUserVO) {
        return opUserMapper.insertTbSocialAccount(opUserVO);
    }

    public int updatetouchLastLogin(DataMap param) {
        return opUserMapper.updatetouchLastLogin(param);
    }

    public OpUserVO selectUserBySocial(DataMap param) {
        OpUserVO opUserVO = opUserMapper.selectUserBySocial(param);
        return opUserVO;
    }

    public boolean existsSocialAccount(DataMap param) {
        return opUserMapper.existsSocialAccount(param);
    }

    public int deleteTbSocialAccount(DataMap param) {
        return opUserMapper.deleteTbSocialAccount(param);
    }
}
