package com.whomade.kycarrots.service.member;

import com.whomade.kycarrots.entity.member.OpUserAuthorVO;
import com.whomade.kycarrots.entity.member.OpUserVO;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.repository.mybatis.member.OpUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author: ADMIN
 * @version: 1.0.0
 * @since: 2024-06-23
 */
@Service
@RequiredArgsConstructor
public class OpUserService {
    private final OpUserRepository opUserRepository;


    public OpUserVO findByUserIdAndPassword(DataMap param) {
        return opUserRepository.findByUserIdAndPassword(param);
    }

    public int insertUser(OpUserVO user) {
        return opUserRepository.insertUser(user);
    }

    public int insertAuthUser(OpUserAuthorVO opUserAuthorVO) {
        return opUserRepository.insertAuthUser(opUserAuthorVO);
    }

    public boolean existsByEmail(String email) {
        return opUserRepository.existsByEmail(email);
    }


}
