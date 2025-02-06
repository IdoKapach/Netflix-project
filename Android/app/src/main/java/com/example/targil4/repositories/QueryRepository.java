package com.example.targil4.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.targil4.api.CategoryAPI;
import com.example.targil4.api.QueryAPI;
import com.example.targil4.entity.Movie;
import com.example.targil4.viewModels.UserViewModel;

import java.util.List;

public class QueryRepository {
    private MoviesLiveData moviesLiveData;
    private QueryAPI queryAPI;

    public QueryRepository(UserViewModel userViewModel) {
        moviesLiveData = new MoviesLiveData();
        this.queryAPI = new QueryAPI(moviesLiveData, userViewModel);
    }
    class MoviesLiveData extends MutableLiveData<List<Movie>> {
        public MoviesLiveData() {
            super();
        }

        @Override
        protected void onActive() {
            android.util.Log.d("Query", "on active activated");
            super.onActive();
        }
    }

    public LiveData<List<Movie>> getMovies() {
        return moviesLiveData;
    }

    public void fetchMovies(String query) {
        queryAPI.getQuery(query);
    }
}
