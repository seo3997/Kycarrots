package com.whomade.kycarrots.service.common;

import com.whomade.kycarrots.entity.common.OpCodeVo;
import com.whomade.kycarrots.entity.common.OpSclasCodeVO;
import com.whomade.kycarrots.entity.member.OpUserVO;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.repository.mybatis.common.OpCodeRepository;
import com.whomade.kycarrots.repository.mybatis.member.OpUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: ADMIN
 * @version: 1.0.0
 * @since: 2024-06-23
 */
@Service
@RequiredArgsConstructor
public class OpCodeService {
    private final OpCodeRepository opCodeRepository;


    public List<OpCodeVo> selectListCode(DataMap param) {
        return opCodeRepository.selectListCode(param);
    }
    public List<OpSclasCodeVO> selectSCodeList(DataMap param) {
        return opCodeRepository.selectSCodeList(param);
    }


}
