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

    class LoggedOnLiveData extends MutableLiveData<Boolean> {
        public LoggedOnLiveData() {
            super();
        }

        @Override
        protected void onActive() {
            super.onActive();
            android.util.Log.d("createUser", "On active atcivated");
            new Thread(() -> {
                try {
                    UserResponse userResponse = dao.getLoggedInUser();
                    loggedOnLiveData.postValue(userResponse.isLoginSuccessful());
                } catch (Exception e) {}
            }).start();
        }
    }

    public LiveData<Boolean> signup(User user) {
        android.util.Log.d("createUser", "Calling post");
        userAPI.signup(user);
        return loggedOnLiveData;
    }

    public LiveData<Boolean> signin(User user) {
        userAPI.signin(user);
        return loggedOnLiveData;
    }
    public boolean hasToken() {
        try {
            UserResponse user = dao.getLoggedInUser();
            return user.isLoginSuccessful();
        } catch (Exception e) {
            return false;
        }
    }

    public void signOut() {
        try {
            dao.clearUserData();
        } catch (Exception e) {
            return;
        }
    }
}
