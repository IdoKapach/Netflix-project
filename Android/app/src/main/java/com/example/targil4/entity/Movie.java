package com.example.targil4.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.targil4.room.Converters;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity
public class Movie {
    @PrimaryKey
    @NonNull
    private String _id;
    @SerializedName("name")
    private String movieName;
    @SerializedName("image")
    private String imageUrl;
    @SerializedName("video")
    private String movieUrl;
    private String description;
    @TypeConverters(Converters.class)
    private List<String> categories;


    public Movie(String _id, String movieName, String imageUrl, String movieUrl, String description, List<String> categories) {
        this._id = _id;
        this.description = description;
        this.imageUrl = imageUrl;
        this.movieName = movieName;
        this.movieUrl = movieUrl;
        this.categories = categories;
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

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() { return description; }

    public String getMovieUrl() { return movieUrl; }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
}
