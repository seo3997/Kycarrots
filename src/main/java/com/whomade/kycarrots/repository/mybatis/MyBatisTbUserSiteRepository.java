package com.whomade.kycarrots.repository.mybatis;

import com.whomade.kycarrots.entity.TbUserSite;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author: ADMIN
 * @version: 1.0.0
 * @since: 2024-06-23
 */
@Repository
@RequiredArgsConstructor
@Transactional
public class MyBatisTbUserSiteRepository {

    private final TbUserSiteMapper tbUserSiteMapper;

    public TbUserSite save(TbUserSite tbUserSite){
        tbUserSiteMapper.save(tbUserSite);
        return tbUserSite;
    }

    public Optional<TbUserSite> findById(Integer id) {
        Optional<TbUserSite> tbUserSite = tbUserSiteMapper.findById(id);
        return tbUserSite;
    }

}
