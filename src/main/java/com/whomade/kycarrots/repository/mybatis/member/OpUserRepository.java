package com.whomade.kycarrots.repository.mybatis.member;

import com.whomade.kycarrots.entity.member.OpUserAuthorVO;
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

    public OpUserVO seelectUser(DataMap param) {
        OpUserVO opUserVO = opUserMapper.seelectUser(param);
        return opUserVO;
    }
    public int insertUser(OpUserVO user) {
        return opUserMapper.insertUser(user);
    }
    public int insertAuthUser(OpUserAuthorVO opUserAuthorVO) {
        return opUserMapper.insertAuthUser(opUserAuthorVO);
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
}
