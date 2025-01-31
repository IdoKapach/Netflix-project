package com.example.targil4.api;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.targil4.MyApplication;
import com.example.targil4.R;
import com.example.targil4.entity.User;
import com.example.targil4.entity.UserResponse;
import com.example.targil4.room.AppDB;
import com.example.targil4.room.UserDao;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserAPI {
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;
    private MutableLiveData<Boolean> loggedOn;
    private UserDao dao;

    public UserAPI(MutableLiveData<Boolean> loggedOn, UserDao dao) {
        retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.BaseURL))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
        this.loggedOn = loggedOn;
        this.dao = dao;
    }

    public void signup(User user) {
        Call<UserResponse> call = webServiceAPI.createUser(user);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    String userId = response.body().getId();
                    android.util.Log.d("createUser", "User created successfully! " + response.code() + ", " + userId);

                    new Thread (() -> {
                        //dao.clearUserData();
                        android.util.Log.d("createUser", "creating the UserResponse");
                        UserResponse userResponse = new UserResponse(response.body().getId(), response.body().getUsername());
                        userResponse.setLoginSuccessful(true);
                        android.util.Log.d("createUser", "inserting use to dao");
                        dao.insertUser(userResponse);
                        android.util.Log.d("createUser", "User Logged On: " + dao.getLoggedInUser().getId());
                        loggedOn.postValue(true);
                    }).start();
                }
                else {

                    new Thread (() -> {
                        android.util.Log.d("createUser", "User initialize error! " + response.code());
                        loggedOn.postValue(false);
                    }).start();
                }
            }
            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                android.util.Log.d("createUser", "User initialization failed!" + t.getMessage());
            }
        });
    }

    public void signin(User user) {
    Call<UserResponse> call = webServiceAPI.loginUser(user);
        call.enqueue(new Callback<UserResponse>() {
        @Override
        public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
            if (response.isSuccessful()) {
                String userId = response.body().getId();
                android.util.Log.d("createUser", "User logged in successfully! " + response.code() + ", " + userId);

                new Thread (() -> {
                    //dao.clearUserData();
                    /*android.util.Log.d("createUser", "creating the UserResponse");
                    UserResponse userResponse = new UserResponse(response.body().getToken(), response.body().getUsername());
                    userResponse.setLoginSuccessful(true);
                    android.util.Log.d("createUser", "inserting use to dao");
                    dao.insertUser(userResponse);
                    android.util.Log.d("createUser", "User Logged On: " + dao.getLoggedInUser().getId());
                    loggedOn.postValue(true);*/
                }).start();
            }
            else {

                new Thread (() -> {
                    android.util.Log.d("createUser", "User initialize error! " + response.code());
                    loggedOn.postValue(false);
                }).start();
            }
        }
        @Override
        public void onFailure(Call<UserResponse> call, Throwable t) {
            android.util.Log.d("createUser", "User initialization failed!" + t.getMessage());
        }
    });
}
}