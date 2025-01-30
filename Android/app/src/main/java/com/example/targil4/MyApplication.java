package com.example.targil4;

import android.app.Application;
import android.content.Context;

import com.example.targil4.room.UserDao;

public class MyApplication extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}
