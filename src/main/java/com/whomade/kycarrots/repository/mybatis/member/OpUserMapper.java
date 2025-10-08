package com.whomade.kycarrots.repository.mybatis.member;

import com.whomade.kycarrots.entity.member.OpUserAuthorVO;
import com.whomade.kycarrots.entity.member.OpUserVO;
import com.whomade.kycarrots.framework.common.object.DataMap;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

}
