package com.example.targil4.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.lang.annotation.Native;
import java.util.List;

@Entity
public class Category {

    private String _id;
    @PrimaryKey
    @NonNull
    private String name;
    private List<String> movies;
    private boolean promoted;

    public Category(String _id, String name, List<String> movies, boolean promoted) {
        this._id = _id;
        this.name = name;
        this.movies = movies;
        this.promoted = promoted;
    }

    @Ignore
    public Category(String name,List<String> movies, boolean promoted) {
        this.name = name;
        this.promoted = promoted;
        this.movies = movies;
    }

    public String get_id() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public List<String> getMovies() {
        return movies;
    }

    public void setMovies(List<String> movies) {
        this.movies = movies;
    }

    public boolean isPromoted() {
        return promoted;
    }

    public void setPromoted(boolean promoted) {
        this.promoted = promoted;
    }
}
