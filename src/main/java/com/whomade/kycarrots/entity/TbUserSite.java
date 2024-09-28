package com.whomade.kycarrots.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: ADMIN
 * @version: 1.0.0
 * @since: 2024-06-23
 */
@Entity
@Getter @Setter
public class TbUserSite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userSn;
    private String userId;
    private String siteNm;
    private String siteFile;
    private String siteHtml;
    private String insertDate;
    private String updateDate;

    public TbUserSite() {
    }

    public TbUserSite(String userId, String siteNm, String siteFile, String siteHtml, String insertDate, String updateDate) {
        this.userId = userId;
        this.siteNm = siteNm;
        this.siteFile = siteFile;
        this.siteHtml = siteHtml;
        this.insertDate = insertDate;
        this.updateDate = updateDate;
    }

}
