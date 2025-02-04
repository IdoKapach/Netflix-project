package com.example.targil4.api;

import androidx.lifecycle.MutableLiveData;

import com.example.targil4.MyApplication;
import com.example.targil4.R;
import com.example.targil4.entity.User;
import com.example.targil4.entity.UserResponse;
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

    // A builder for the user API
    public UserAPI(MutableLiveData<Boolean> loggedOn, UserDao dao) {
        retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.BaseURL).concat("api/"))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
        this.loggedOn = loggedOn;
        this.dao = dao;
    }

    // A function that Creates a new user in the API, then logs in to that user if successful
    public void signup(User user) {

        // Call the server to create user
        Call<UserResponse> call = webServiceAPI.createUser(user);
        call.enqueue(new Callback<>() {

            // when server responds:
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {

                // if user was created sign in to that user
                if (response.isSuccessful()) {
                    String username = response.body().getUsername();
                    android.util.Log.d("createUser", "User created successfully! " + response.code() + ", " + username);

                    // call API function sign in to try to sign in
                    new Thread (() -> {
                        android.util.Log.d("createUser", "creating the UserResponse");
                        signin(user);
                    }).start();
                }

                // if server did not create a new user set loggedOn to false
                else {

                    new Thread (() -> {
                        android.util.Log.d("createUser", "User initialize error! " + response.code());
                        loggedOn.postValue(false);
                    }).start();
                }
            }

            // if failed to call the server print log explaining the problem
            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                android.util.Log.d("createUser", "User initialization failed!" + t.getMessage());
            }
        });
    }

    // A function that tries to sign in a user, and sets boolean loggedOn accordingly
    public void signin(User user) {

        // Call the server to login user
        Call<UserResponse> call = webServiceAPI.loginUser(user);
        call.enqueue(new Callback<>() {

            // when server responds:
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {

                // if user successfully signed in save in room the data and set loggedOn to true
                if (response.isSuccessful()) {
                    String token = response.body().getToken();
                    android.util.Log.d("createUser", "User logged in successfully! " + response.code() + ", " + token);

                    new Thread(() -> {
                        android.util.Log.d("createUser", "inserting user to dao");
                        UserResponse user = new UserResponse(token);
                        dao.insertUser(user);
                        android.util.Log.d("createUser", "User Logged On: " + dao.getLoggedInUser().getUsername());
                        loggedOn.postValue(true);
                    }).start();
                }

                // if user failed to log in set loggedOn to false
                else {

                    new Thread(() -> {
                        android.util.Log.d("createUser", "User initialize error! " + response.code());
                        loggedOn.postValue(false);
                    }).start();
                }
            }

            // if failed to call the server print log explaining the problem
            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                android.util.Log.d("createUser", "User initialization failed!" + t.getMessage());
            }
        });
    }
}