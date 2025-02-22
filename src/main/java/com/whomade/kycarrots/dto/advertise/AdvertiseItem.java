package com.whomade.kycarrots.dto.advertise;


public class AdvertiseItem {
    private int ad_idx;
    private String ad_nm;
    private String ad_mainurl;
    private int ad_point;
    private String ad_content;
    private String ad_etc;
    private String ad_brief;
    private double ad_rating;
    private int ad_event;

    public AdvertiseItem() {}

    public AdvertiseItem(int ad_idx, String ad_nm, String ad_mainurl, int ad_point,
                         String ad_content, String ad_etc, String ad_brief,
                         double ad_rating, int ad_event) {
        this.ad_idx = ad_idx;
        this.ad_nm = ad_nm;
        this.ad_mainurl = ad_mainurl;
        this.ad_point = ad_point;
        this.ad_content = ad_content;
        this.ad_etc = ad_etc;
        this.ad_brief = ad_brief;
        this.ad_rating = ad_rating;
        this.ad_event = ad_event;
    }

    public int getAd_idx() {
        return ad_idx;
    }

    public void setAd_idx(int ad_idx) {
        this.ad_idx = ad_idx;
    }

    public String getAd_nm() {
        return ad_nm;
    }

    public void setAd_nm(String ad_nm) {
        this.ad_nm = ad_nm;
    }

    public String getAd_mainurl() {
        return ad_mainurl;
    }

    public void setAd_mainurl(String ad_mainurl) {
        this.ad_mainurl = ad_mainurl;
    }

    public int getAd_point() {
        return ad_point;
    }

    public void setAd_point(int ad_point) {
        this.ad_point = ad_point;
    }

    public String getAd_content() {
        return ad_content;
    }

    public void setAd_content(String ad_content) {
        this.ad_content = ad_content;
    }

    public String getAd_etc() {
        return ad_etc;
    }

    public void setAd_etc(String ad_etc) {
        this.ad_etc = ad_etc;
    }

    public String getAd_brief() {
        return ad_brief;
    }

    public void setAd_brief(String ad_brief) {
        this.ad_brief = ad_brief;
    }

    public double getAd_rating() {
        return ad_rating;
    }

    public void setAd_rating(double ad_rating) {
        this.ad_rating = ad_rating;
    }

    public int getAd_event() {
        return ad_event;
    }

    public void setAd_event(int ad_event) {
        this.ad_event = ad_event;
    }
}
