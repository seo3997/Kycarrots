package com.whomade.kycarrots.repository.mybatis.common;

import com.whomade.kycarrots.entity.common.OpCodeVo;
import com.whomade.kycarrots.entity.member.OpUserVO;
import com.whomade.kycarrots.framework.common.object.DataMap;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author: ADMIN
 * @version: 1.0.0
 * @since: 2024-06-23
 */
@Mapper
public interface OpCodeMapper {
    List<OpCodeVo> selectListCode(DataMap param);
}
