package com.example.targil4.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.targil4.entity.User;
import com.example.targil4.repositories.UserRepository;

public class UserViewModel extends ViewModel {
    private LiveData<Boolean> loggedOn;
    private UserRepository userRepo;

    UserViewModel() {
        userRepo = new UserRepository();
        loggedOn = userRepo.hasToken();
    }

    public void signup(User user) {
        userRepo.signup(user);
    }

    public void signin(User user) {
        userRepo.signin(user);
    }

    public LiveData<Boolean> hasToken() {
        return loggedOn;
    }

    public void signOut() {
        userRepo.signOut();
    }

    public String getToken() {
        android.util.Log.d("Movies", "wanting Token too!");
        return userRepo.getToken();
    }

    public boolean isAdmin() {
        return userRepo.isAdmin();
    }
    public boolean isDarkMode() {
        return userRepo.isDarkMode();
    }

    public void updateDarkMode() {
        userRepo.UpdateDarkMode();
    }
}