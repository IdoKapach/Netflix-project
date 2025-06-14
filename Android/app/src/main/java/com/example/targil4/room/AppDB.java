package com.example.targil4.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.targil4.entity.Category;
import com.example.targil4.entity.Movie;
import com.example.targil4.entity.UserResponse;

@Database(entities = {UserResponse.class, Category.class, Movie.class}, version = 9)
@TypeConverters(Converters.class)
public abstract class AppDB extends RoomDatabase {
    public abstract UserDao UserDao();
    public abstract CategoryDao CategoryDao();
    public abstract MovieDao MovieDao();
}