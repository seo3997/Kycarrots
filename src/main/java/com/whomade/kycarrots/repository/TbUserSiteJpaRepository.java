package com.whomade.kycarrots.repository;

import com.whomade.kycarrots.entity.TbUserSite;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 * @author: ADMIN
 * @version: 1.0.0
 * @since: 2024-06-23
 */
@Repository
public class TbUserSiteJpaRepository {

    @PersistenceContext
    private EntityManager em;

    public TbUserSite save(TbUserSite tbUserSite) {
        em.persist(tbUserSite);
        return tbUserSite;
    }

    public  TbUserSite find(int userSn) {
        return em.find(TbUserSite.class, userSn);
    }
}
