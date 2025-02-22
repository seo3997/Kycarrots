package com.whomade.kycarrots.repository.mybatis.member;

import com.whomade.kycarrots.entity.TbUserSite;
import com.whomade.kycarrots.entity.member.OpUserVO;
import com.whomade.kycarrots.framework.common.object.DataMap;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

/**
 * @author: ADMIN
 * @version: 1.0.0
 * @since: 2024-06-23
 */
@Mapper
public interface OpUserMapper {
    OpUserVO findByUserIdAndPassword(DataMap param);
}
