package com.whomade.kycarrots.service;

import com.whomade.kycarrots.entity.TbUserSite;
import com.whomade.kycarrots.repository.mybatis.MyBatisTbUserSiteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author: ADMIN
 * @version: 1.0.0
 * @since: 2024-06-23
 */
@Service
@RequiredArgsConstructor
public class TbUserSiteService {
    private final MyBatisTbUserSiteRepository myBatisTbUserSiteRepository;

    public TbUserSite save(TbUserSite tbUserSite){
        myBatisTbUserSiteRepository.save(tbUserSite);
        return tbUserSite;
    }

    public Optional<TbUserSite> findById(Integer id) {
        return myBatisTbUserSiteRepository.findById(id);
    }


}
