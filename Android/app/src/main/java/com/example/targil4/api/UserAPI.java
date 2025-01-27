package com.example.targil4.api;

import com.example.targil4.MyApplication;
import com.example.targil4.R;
import com.example.targil4.entity.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserAPI {
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;

    public UserAPI() {
        retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.BaseURL))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public void post(User user) {
        Call<Void> call = webServiceAPI.createUser(user);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                android.util.Log.d("createUser", "User initialized successfully!");
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                android.util.Log.d("createUser", "User initialization failed!" + t.getMessage());
            }
        });
    }
}
