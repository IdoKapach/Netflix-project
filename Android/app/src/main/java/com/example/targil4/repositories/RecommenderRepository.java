package com.example.targil4.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.targil4.api.RecommenderAPI;
import com.example.targil4.entity.Movie;
import com.example.targil4.viewModels.UserViewModel;

import java.util.List;

public class RecommenderRepository {
    private MoviesLiveData moviesLiveData;
    private RecommenderAPI recommenderAPI;

    public RecommenderRepository(UserViewModel userViewModel) {
        moviesLiveData = new MoviesLiveData();
        recommenderAPI = new RecommenderAPI(moviesLiveData, userViewModel);
    }

    class MoviesLiveData extends MutableLiveData<List<Movie>> {
        public MoviesLiveData() {
            super();
        }

        @Override
        protected void onActive() {
            super.onActive();
        }
    }

    public LiveData<List<Movie>> getRecommendations() {
        return moviesLiveData;
    }

    public void watchedMovie(String id) {
        recommenderAPI.watchedMovie(id);
    }

    public void findRecommendations(String id) {
        recommenderAPI.getRecommendations(id);
    }

}
