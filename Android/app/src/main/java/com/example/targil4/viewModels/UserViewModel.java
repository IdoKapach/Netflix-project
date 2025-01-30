package com.example.targil4.viewModels;

import androidx.lifecycle.LiveData;

import com.example.targil4.api.UserAPI;
import com.example.targil4.entity.User;

public class UserViewModel {
    private LiveData<User> user;
    private UserAPI userAPI;

    UserViewModel() {
        userAPI = new UserAPI();
    }

    public boolean signup(User user) {
        userAPI.post(user);
        return user.isLoginSuccessful();
    }
}
