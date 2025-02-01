package com.example.targil4.entity;

public class Movie {
    private String movieName;
    private String imageUrl;

    public Movie(String movieName, String imageUrl) {
        this.movieName = movieName;
        this.imageUrl = imageUrl;
    }

    // Getters and Setters
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
}
