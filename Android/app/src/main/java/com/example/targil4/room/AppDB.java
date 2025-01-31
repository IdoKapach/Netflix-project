package com.example.targil4.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.targil4.entity.UserResponse;

@Database(entities = {UserResponse.class}, version = 2)
public abstract class AppDB extends RoomDatabase {
    public abstract UserDao UserDao();
}
