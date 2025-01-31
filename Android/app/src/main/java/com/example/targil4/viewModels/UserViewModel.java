package com.example.targil4.viewModels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.targil4.api.UserAPI;
import com.example.targil4.entity.User;
import com.example.targil4.entity.UserResponse;
import com.example.targil4.repositories.UserRepository;

public class UserViewModel extends ViewModel {
    private LiveData<Boolean> loggedOn;
    private UserRepository userRepo;

    UserViewModel() {
        userRepo = new UserRepository();
    }

    public LiveData<Boolean> signup(User user) {
        return userRepo.signup(user);
    }

    public LiveData<Boolean> signin(User user) {
        return userRepo.signin(user);
    }

    public boolean hasToken() {
        return userRepo.hasToken();
    }

    public void signOut() {
        userRepo.signOut();
    }
}