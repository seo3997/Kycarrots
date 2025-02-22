package com.whomade.kycarrots.dto.advertise;

import java.util.ArrayList;
import java.util.List;

public class AdvertiseResponse {
    private List<AdvertiseItem> items = new ArrayList<>();

    public List<AdvertiseItem> getItems() {
        return items;
    }

    public void setItems(List<AdvertiseItem> items) {
        this.items = items;
    }
}