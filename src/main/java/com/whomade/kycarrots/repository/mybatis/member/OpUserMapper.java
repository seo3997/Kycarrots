package com.whomade.kycarrots.repository.mybatis.member;

import com.whomade.kycarrots.entity.member.OpUserAuthorVO;
import com.whomade.kycarrots.entity.member.OpUserVO;
import com.whomade.kycarrots.framework.common.object.DataMap;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author: ADMIN
 * @version: 1.0.0
 * @since: 2024-06-23
 */
@Mapper
public interface OpUserMapper {
    OpUserVO seelectUser(DataMap param);
    int insertUser(OpUserVO user);
    int insertAuthUser(OpUserAuthorVO opUserAuthorVO);
    boolean existsByEmail(@Param("email") String email);
}
