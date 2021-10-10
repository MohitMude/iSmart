package com.iitism.ismart.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserItem {

    @SerializedName("Item")
    @Expose
    private User item;

    public User getItem() {
        return item;
    }

    public void setItem(User item) {
        this.item = item;
    }




}
