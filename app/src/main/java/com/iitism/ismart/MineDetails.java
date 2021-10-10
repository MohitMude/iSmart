package com.iitism.ismart;

import java.util.ArrayList;

public class MineDetails {

    String name,state,city;
    ArrayList<String> gateway;

    public MineDetails(String name, String state, String city, ArrayList<String> gateway) {
        this.name = name;
        this.state = state;
        this.city = city;
        this.gateway = gateway;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public ArrayList<String> getGateway() {
        return gateway;
    }

    public void setGateway(ArrayList<String> gateway) {
        this.gateway = gateway;
    }
}
