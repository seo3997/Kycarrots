package com.whomade.kycarrots.repository.mybatis.member;

import com.whomade.kycarrots.entity.member.OpUserVO;
import com.whomade.kycarrots.framework.common.object.DataMap;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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

    public OpUserVO findByUserIdAndPassword(DataMap param) {
        OpUserVO opUserVO = opUserMapper.findByUserIdAndPassword(param);
        return opUserVO;
    }

}
