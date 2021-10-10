package com.iitism.ismart.API;


import org.checkerframework.checker.nullness.compatqual.PolyNullDecl;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    String BASE_URL = "https://uoj6uggv08.execute-api.eu-west-1.amazonaws.com";
    String USER_BASE_URL = "https://h1h8310ash.execute-api.eu-west-1.amazonaws.com";

    @GET("/items")
    Call<Item> getMine();

    @GET("items/{id}")
    Call<GatewayItem> getGateway(@Path("id") String id);

    @PUT("/item")
    Call<User> putUser(@Body User body);

    @GET("/items/{email}")
    Call<UserItem> getUser(@Path("email") String email);
}