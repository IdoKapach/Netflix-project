package com.example.targil4.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.targil4.entity.UserResponse;

@Dao
public interface UserDao {
    @Insert
    void insertUser(UserResponse user);

    @Query("SELECT * FROM UserResponse LIMIT 1")
    UserResponse getLoggedInUser();

    @Query("DELETE FROM UserResponse")
    void clearUserData();
    @Query("UPDATE UserResponse SET DarkMode = :darkMode WHERE username = :username")
    void updateDarkMode(boolean darkMode, String username);

}