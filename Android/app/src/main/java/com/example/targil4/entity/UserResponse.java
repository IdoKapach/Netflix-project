package com.example.targil4.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserResponse {
    @PrimaryKey
    @NonNull
    private String _id;
    private String username;

    public UserResponse(@NonNull String id, String username) {
        this._id = id;
        this.username = username;
    }

    public String getId() {
        return _id;
    }

    public void setId(@NonNull String _id) {
        this._id = this._id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}