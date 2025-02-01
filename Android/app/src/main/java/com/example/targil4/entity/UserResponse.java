package com.example.targil4.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserResponse {
    @PrimaryKey
    @NonNull
    private String username;
    private String token;
    private boolean admin;

    public UserResponse(@NonNull String username, String token, boolean admin) {
        this.token = token;
        this.admin = admin;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}