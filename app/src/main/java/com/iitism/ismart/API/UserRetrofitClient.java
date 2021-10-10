package com.iitism.ismart.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserRetrofitClient {
    private static UserRetrofitClient instance = null;
    private Api Api;

    private UserRetrofitClient() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.USER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api = retrofit.create(Api.class);
    }

    public static synchronized UserRetrofitClient getInstance() {
        if (instance == null) {
            instance = new UserRetrofitClient();
        }
        return instance;
    }

    public Api getApi() {
        return Api;
    }
}
