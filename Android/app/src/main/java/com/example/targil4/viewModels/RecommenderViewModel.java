package com.example.targil4.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.targil4.entity.Movie;
import com.example.targil4.entity.User;
import com.example.targil4.repositories.RecommenderRepository;

import java.util.List;

public class RecommenderViewModel extends ViewModel {
    private RecommenderRepository recommenderRepo;
    private LiveData<List<Movie>> recommendations;

    RecommenderViewModel(UserViewModel userViewModel) {
        recommenderRepo = new RecommenderRepository(userViewModel);
        recommendations = recommenderRepo.getRecommendations();
    }

    public LiveData<List<Movie>> getRecommendations() {
        return recommendations;
    }

    public void findRecommendations(String id) {
        recommenderRepo.findRecommendations(id);
    }

    public void watchedMovie(String id) {
        recommenderRepo.watchedMovie(id);
    }


}
