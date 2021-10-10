package com.iitism.ismart.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GatewayItem {
    @SerializedName("Item")
    @Expose
    private GatewayMineItem item;

    public GatewayMineItem getItem() {
        return item;
    }

    public void setItem(GatewayMineItem item) {
        this.item = item;
    }
}
