package com.example.targil4.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.targil4.entity.Movie;
import com.example.targil4.entity.User;
import com.example.targil4.repositories.QueryRepository;

import java.util.List;

public class QueryViewModel extends ViewModel {
    private LiveData<List<Movie>> movies;
    private QueryRepository queryRepo;

    QueryViewModel(UserViewModel userViewModel) {
        queryRepo = new QueryRepository(userViewModel);
        movies = queryRepo.getMovies();
    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }

    public void fetchMovies(String query) {
        queryRepo.fetchMovies(query);
    }
}
