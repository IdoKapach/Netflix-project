package com.example.targil4.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.targil4.MyApplication;
import com.example.targil4.api.UserAPI;
import com.example.targil4.entity.User;
import com.example.targil4.entity.UserResponse;
import com.example.targil4.room.AppDB;
import com.example.targil4.room.UserDao;

public class UserRepository {
    private UserDao dao;
    private LoggedOnLiveData loggedOnLiveData;
    private UserAPI userAPI;

    // A builder for the repository
    public UserRepository() {
        AppDB db = Room.databaseBuilder(MyApplication.context,
                        AppDB.class, "FooDB")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries().build();
        this.dao = db.UserDao();
        android.util.Log.d("createUser", "creating the API");
        loggedOnLiveData = new LoggedOnLiveData();
        userAPI = new UserAPI(loggedOnLiveData, dao);
    }

    // class that extends the Mutable live data to check if user is logged on
    class LoggedOnLiveData extends MutableLiveData<Boolean> {
        public LoggedOnLiveData() {
            super();
        }


        // when called gets from room the user data, and set livedata to true if there is a user
        @Override
        protected void onActive() {
            super.onActive();
            android.util.Log.d("createUser", "On active activated");
            new Thread(() -> {
                try {
                    UserResponse userResponse = dao.getLoggedInUser();
                    loggedOnLiveData.postValue(userResponse != null);
                } catch (Exception e) {}
            }).start();
        }
    }

    // calls the API to signup the user, which will update livedata loggedOn
    public void signup(User user) {
        userAPI.signup(user);
    }

    // calls the API to sign in the user, which will update livedata loggedOn
    public void signin(User user) {
        userAPI.signin(user);
    }

    // calls room to check is there is a user logged on, returns the boolean accordingly
    public LiveData<Boolean> hasToken() {
        return loggedOnLiveData;
    }

    // calls room to delete its user data
    public void signOut() {
        try {
            dao.clearUserData();
        }

        // no user data already
        catch (Exception e) {
            return;
        }
    }
}
