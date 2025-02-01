package com.example.targil4.entity;

public class Movie {
    private String movieName;
    private String imageUrl;
    private String movieUrl;
    private String description;

    public Movie(String movieName, String imageUrl, String description, String movieUrl) {
        this.movieName = movieName;
        this.imageUrl = imageUrl;
        this.description = description;
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

    public String getDescription() { return description; }

    public String getMovieUrl() { return movieUrl; }
}
