package com.whomade.kycarrots.repository.mybatis.common;

import com.whomade.kycarrots.entity.common.OpCodeVo;
import com.whomade.kycarrots.entity.member.OpUserVO;
import com.whomade.kycarrots.entity.product.TnProductVo;
import com.whomade.kycarrots.framework.common.object.DataMap;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: ADMIN
 * @version: 1.0.0
 * @since: 2024-06-23
 */
@Repository
@RequiredArgsConstructor
@Transactional
public class OpCodeRepository {

    private final OpCodeMapper opCodeMapper;

    public List<OpCodeVo> selectListCode(DataMap param) {
        List<OpCodeVo> opCodeVo = opCodeMapper.selectListCode(param);
        return opCodeVo;
    }



}
