package com.example.targil4.entity;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.auth0.android.jwt.JWT;

@Entity
public class UserResponse {
    @PrimaryKey
    @NonNull
    private String token;
    private String username;
    private boolean admin;
    private boolean darkMode;

    public UserResponse(@NonNull String token) {
        this.token = token;
        try {
            android.util.Log.d("createUser", "new JWT creation");
            JWT jwt = new JWT(token);
            this.username = jwt.getClaim("username").asString();
            android.util.Log.d("createUser", "User ID: " + username);
            String role = jwt.getClaim("role").asString();
            this.admin = role.equals("admin");
            android.util.Log.d("createUser", "Admin?: " + admin + ", " + role);
            this.darkMode = true;
        } catch (Exception e) {
            android.util.Log.d("createUser", "Invalid Token: " + e.getMessage());
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(@NonNull String token) {
        this.token = token;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isDarkMode() {
        return darkMode;
    }

    public void setDarkMode(boolean darkMode) {
        this.darkMode = darkMode;
    }
}