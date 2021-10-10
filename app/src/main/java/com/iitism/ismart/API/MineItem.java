package com.iitism.ismart.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;



public class MineItem {


    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("__typename")
    @Expose
    private String typename;
    @SerializedName("_lastChangedAt")
    @Expose
    private Long lastChangedAt;
    @SerializedName("gateways")
    @Expose
    private Object gateways;
    @SerializedName("_version")
    @Expose
    private Integer version;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("gateways")
    @Expose
    private List<String> gateway = null;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public Long getLastChangedAt() {
        return lastChangedAt;
    }

    public void setLastChangedAt(Long lastChangedAt) {
        this.lastChangedAt = lastChangedAt;
    }

    public Object getGateways() {
        return gateways;
    }

    public void setGateways(Object gateways) {
        this.gateways = gateways;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<String> getGateway() {
        return gateway;
    }

    public void setGateway(List<String> gateway) {
        this.gateway = gateway;
    }

}
