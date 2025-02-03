package com.example.targil4.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Movie {
    @PrimaryKey
    @NonNull
    private String _id;
    private String movieName;
    private String imageUrl;
    private String movieUrl;
    private String description;


    public Movie(String _id, String movieName, String imageUrl, String movieUrl, String description) {
        this._id = _id;
        this.description = description;
        this.imageUrl = imageUrl;
        this.movieName = movieName;
        this.movieUrl = movieUrl;
    }

    @Ignore
    public Movie(String movieName, String imageUrl, String description, String movieUrl) {
        this.movieName = movieName;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    // Getters and Setters

    @NonNull
    public String get_id() {
        return _id;
    }

    public void set_id(@NonNull String _id) {
        this._id = _id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String username) {
        this.movieName = movieName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String password) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() { return description; }

    public String getMovieUrl() { return movieUrl; }
}
