package com.iitism.ismart.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Item {
    @SerializedName("Items")
    @Expose
    private List<MineItem> items = null;
    @SerializedName("Count")
    @Expose
    private Integer count;
    @SerializedName("ScannedCount")
    @Expose
    private Integer scannedCount;

    public List<MineItem> getItems() {
        return items;
    }

    public void setItems(List<MineItem> items) {
        this.items = items;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getScannedCount() {
        return scannedCount;
    }

    public void setScannedCount(Integer scannedCount) {
        this.scannedCount = scannedCount;
    }
}
