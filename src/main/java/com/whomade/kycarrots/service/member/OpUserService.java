package com.whomade.kycarrots.service.member;

import com.whomade.kycarrots.entity.member.OpUserAuthorVO;
import com.whomade.kycarrots.entity.member.OpUserVO;
import com.whomade.kycarrots.framework.common.object.DataMap;
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
public class OpUserService {
    private final OpUserRepository opUserRepository;


    public OpUserVO seelectUser(DataMap param) {
        return opUserRepository.seelectUser(param);
    }

    public int insertUser(OpUserVO user) {
        return opUserRepository.insertUser(user);
    }

    public boolean existsByEmail(String email) {
        return opUserRepository.existsByEmail(email);
    }

    public int updatePushToken(OpUserVO user) {
        return opUserRepository.updatePushToken(user);
    }

    public OpUserVO fetchFcmToken(String userId) {
        return opUserRepository.fetchFcmToken(userId);
    }

    public List<OpUserVO> selectActiveWholesalers(String memberCode) {
        return opUserRepository.selectActiveWholesalers(memberCode);
    }

    // 추가: 기본 중간센터 조회/설정 (USER_ID 기준)
    public Long findWholesalerNoByUserId(String userId) {
        return opUserRepository.findWholesalerNoByUserId(userId);
    }
    // 추가: 기본 중간센터 조회/설정 (USER_ID 기준)

    public Long findWholesalerNoByUserNo(long userNo) {
        return opUserRepository.findWholesalerNoByUserNo(userNo);
    }

    public int updateDefaultWholesalerByUserId(OpUserVO user) {
        return opUserRepository.updateDefaultWholesalerByUserId(user);
    }

    public OpUserVO findEmailByNameAndPhone(OpUserVO opUserVO) {
        return opUserRepository.findEmailByNameAndPhone(opUserVO);
    }

}
