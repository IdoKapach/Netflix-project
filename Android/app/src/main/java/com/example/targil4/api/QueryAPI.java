package com.example.targil4.api;

import androidx.lifecycle.MutableLiveData;

import com.example.targil4.MyApplication;
import com.example.targil4.R;
import com.example.targil4.entity.Category;
import com.example.targil4.entity.Movie;
import com.example.targil4.room.CategoryDao;
import com.example.targil4.viewModels.UserViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QueryAPI {
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;
    private MutableLiveData<List<Movie>> moviesLiveData;
    private UserViewModel userViewModel;
    private String token;

    public QueryAPI(MutableLiveData<List<Movie>> moviesLiveData, UserViewModel userViewModel) {
        retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.BaseURL).concat("api/"))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
        this.moviesLiveData = moviesLiveData;
        this.userViewModel = userViewModel;
        this.token = "Bearer " + userViewModel.getToken();
    }

    public void getQuery(String query) {
        Call<List<Movie>> call = webServiceAPI.getQuery(token, query);
        call.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                android.util.Log.d("Query", "getting Query success! " + response.code());
                moviesLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                android.util.Log.d("Query", "getting Query falied! " + t.getMessage());
            }
        });
    }
}
