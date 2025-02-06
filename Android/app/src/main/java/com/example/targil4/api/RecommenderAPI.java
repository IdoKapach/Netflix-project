package com.example.targil4.api;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.targil4.MyApplication;
import com.example.targil4.R;
import com.example.targil4.entity.Movie;
import com.example.targil4.viewModels.UserViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecommenderAPI {
    private Retrofit retrofit;
    private WebServiceAPI webServiceAPI;
    private UserViewModel userViewModel;
    private String token;
    private MutableLiveData<List<Movie>> recommendedLiveData;

    public RecommenderAPI(MutableLiveData<List<Movie>> recommendedLiveData, UserViewModel userViewModel) {
        retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.BaseURL).concat("api/"))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
        this.recommendedLiveData = recommendedLiveData;
        this.userViewModel = userViewModel;
        this.token = "Bearer " + userViewModel.getToken();
    }

    public void getRecommendations(String id) {
        Call<List<Movie>> call = webServiceAPI.getRecommendations(token, id);
        call.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                Log.e("Recommender", "got Response: " + response.code() + " " + response.message());
                recommendedLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                Log.e("Recommender", "Error recommending", t);
            }
        });
    }

    public void watchedMovie(String id) {
        Call<Void> call = webServiceAPI.watchedMovie(token, id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.e("Recommender", "got Response: " + response.code() + " " + response.message());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Recommender", "Error recommending", t);
            }
        });
    }

}
