package com.iitism.ismart.Authentication;

public class StateAndCity {

    String stateName;
    String CityName;

    public StateAndCity(String stateName, String cityName) {
        this.stateName = stateName;
        CityName = cityName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }
}
