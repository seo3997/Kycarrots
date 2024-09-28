package com.whomade.kycarrots.repository.mybatis;

import com.whomade.kycarrots.entity.TbUserSite;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

/**
 * @author: ADMIN
 * @version: 1.0.0
 * @since: 2024-06-23
 */
@Mapper
public interface TbUserSiteMapper {

    void save(TbUserSite item);

    Optional<TbUserSite> findById(Integer id);

}
