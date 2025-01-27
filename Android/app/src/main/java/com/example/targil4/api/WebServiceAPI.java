package com.example.targil4.api;

import com.example.targil4.entity.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface WebServiceAPI {
    @POST("users")
    Call<Void> createUser(@Body User user);
}
