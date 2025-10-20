package com.whomade.kycarrots.repository.mybatis.member;

import com.whomade.kycarrots.email.PasswordResetToken;
import com.whomade.kycarrots.entity.member.OpUserAuthorVO;
import com.whomade.kycarrots.entity.member.OpUserVO;
import com.whomade.kycarrots.framework.common.object.DataMap;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: ADMIN
 * @version: 1.0.0
 * @since: 2024-06-23
 */
@Mapper
public interface OpUserMapper {
    OpUserVO seelectUser(DataMap param);
    int insertUser(OpUserVO user);
    boolean existsByEmail(@Param("email") String email);
    int updatePushToken(OpUserVO user);
    OpUserVO fetchFcmToken(@Param("userId") String userId);
    // OpUserMapper.java
    List<OpUserVO> selectActiveWholesalers(@Param("memberCode") String memberCode);
    Long findWholesalerNoByUserId(@Param("userId") String userId);
    Long findWholesalerNoByUserNo(@Param("userNo") Long userNo);
    int updateDefaultWholesalerByUserId(OpUserVO user);
    OpUserVO findEmailByNameAndPhone(OpUserVO opUserVO);
    OpUserVO selectByEmail(@Param("email") String email);

    int upsertToken(PasswordResetToken token);
    // 검증: 사용자당 1행 유지 → USER_ID 로 단건 조회
    PasswordResetToken selectValidForUser(@Param("userId") String userId,
                                          @Param("now") LocalDateTime now);
    // 1회성 사용 처리
    int markUsed(@Param("userId") String userId);

    int updatePw(DataMap param);

    int insertTbSocialAccount(OpUserVO use);

    int updatetouchLastLogin(DataMap param);

    OpUserVO selectUserBySocial(DataMap param);

    boolean existsSocialAccount(DataMap param);

}
