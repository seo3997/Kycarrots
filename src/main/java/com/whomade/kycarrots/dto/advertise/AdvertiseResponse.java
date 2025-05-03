package com.whomade.kycarrots.dto.advertise;

import com.whomade.kycarrots.entity.product.TnProductVo;

import java.util.ArrayList;
import java.util.List;

public class AdvertiseResponse {
    private List<TnProductVo> items = new ArrayList<>();

    public List<TnProductVo> getItems() {
        return items;
    }

    public void setItems(List<TnProductVo> items) {
        this.items = items;
    }
}