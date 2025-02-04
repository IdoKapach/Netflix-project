package com.example.targil4.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.targil4.entity.Movie;

import java.util.List;

@Dao
public interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(Movie movie);
    @Query("SELECT * FROM Movie WHERE _id = :id LIMIT 1")
    Movie getMovie(String id);
    @Query("DELETE FROM Movie WHERE _id = :id")
    void deleteMovie(String id);
    @Update
    void updateMovie(Movie movie);
    @Query("SELECT * FROM Movie")
    List<Movie> getAllMovies();
}
