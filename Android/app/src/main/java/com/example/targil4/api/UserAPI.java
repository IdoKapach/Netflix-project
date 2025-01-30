package com.example.targil4.api;

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

    public UserAPI() {
        retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.BaseURL))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public void post(User user) {
        Call<UserResponse> call = webServiceAPI.createUser(user);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    String userId = response.body().getId();
                    android.util.Log.d("createUser", "User initialized successfully! " + response.code() + ", " + userId);
                    AppDB db = Room.databaseBuilder(MyApplication.context,
                            AppDB.class,"FooDB")
                    .allowMainThreadQueries().build();
                    UserDao userDao = db.UserDao();
                    userDao.insertUser(response.body());
                    android.util.Log.d("createUser", "User Logged On: " + userDao.getLoggedInUser().getId());
                    user.setLoginSuccessful(true);
                }
                else {
                    android.util.Log.d("createUser", "User initialize error! " + response.code());
                }
            }
            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                android.util.Log.d("createUser", "User initialization failed!" + t.getMessage());
            }
        });
    }
}
