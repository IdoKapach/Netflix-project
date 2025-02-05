package com.example.targil4.api;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.targil4.MyApplication;
import com.example.targil4.R;
import com.example.targil4.entity.Category;
import com.example.targil4.entity.Movie;
import com.example.targil4.entity.UserResponse;
import com.example.targil4.room.CategoryDao;
import com.example.targil4.room.MovieDao;
import com.example.targil4.room.UserDao;
import com.example.targil4.viewModels.UserViewModel;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieAPI {
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;
    //private MutableLiveData<Movie> movieLiveData;
    private UserViewModel userViewModel;
    private String token;
    private MovieDao moviedao;
    private CategoryDao categoryDao;
    MutableLiveData<List<Category>> categoryLiveData;
    MutableLiveData<List<Movie>> moviesLiveData;
    public MovieAPI(MutableLiveData<List<Category>> categoriesLiveData, MutableLiveData<List<Movie>> moviesLiveData, UserViewModel userViewModel, MovieDao movieDao, CategoryDao categoryDao) {
        retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.BaseURL).concat("api/"))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
        this.moviesLiveData = moviesLiveData;
        this.categoryLiveData = categoriesLiveData;
        this.userViewModel = userViewModel;
        this.token = "Bearer " + userViewModel.getToken();
        this.categoryDao = categoryDao;
        this.moviedao = movieDao;
    }

    public void addMovie(MultipartBody.Part videoPart, MultipartBody.Part imagePart, RequestBody movieTitleRequestBody, RequestBody movieDescriptionRequestBody, RequestBody categories) {

        Call<Movie> call = webServiceAPI.upload(videoPart, imagePart, movieTitleRequestBody, movieDescriptionRequestBody, token, categories);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful()) {
                    Log.e("FileUpload", "Upload successful!");
                    moviedao.insertMovie(response.body());
                    moviesLiveData.postValue(moviedao.getAllMovies());
                } else {
                    Log.e("FileUpload", "Upload failed: " + response.code() + " " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.e("FileUpload", "Error uploading file", t);
            }
        });
    }

    public void deleteMovie(String movieId) {
        Call<Void> call = webServiceAPI.deleteMovie(token, movieId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.e("FileUpload", "Delete successful!");
                moviedao.deleteMovie(movieId);
                moviesLiveData.postValue(moviedao.getAllMovies());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("FileUpload", "Error deleting file ", t);
            }
        });
    }

    public void editMovie(Movie movie) {
        Call<Movie> call = webServiceAPI.editMovie(token, movie.get_id(), movie);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                Log.e("Movies", "got the Respond! " + response.code());
                android.util.Log.d("Movies", "new name is: ");
                Movie editedMovie = response.body();
                android.util.Log.d("Movies", "new name is: " + editedMovie.getMovieName());

                moviedao.insertMovie(editedMovie);
                moviesLiveData.postValue(moviedao.getAllMovies());
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.e("Movies", "got the Respond! " + t.getMessage());


            }
        });
    }
    public void getMovie(String id) {
        Call<Movie> call = webServiceAPI.getMovie(token, id);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                Log.e("Movies", "getMovie - got the Respond! " + response.code());
                if (response.isSuccessful()) {
                    android.util.Log.d("Movies", "the name is: " + response.body().getMovieName());
                    android.util.Log.d("Movies", "he has C: " + response.body().getCategories().size());
                    moviedao.insertMovie(response.body());
                    moviesLiveData.postValue(moviedao.getAllMovies());
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.e("Movies", "got the Respond! " + t.getMessage());
            }
        });

    }

    public void getMovies() {
        android.util.Log.d("Movies", "getting Movies! " + token);
        Call<Map<String, List<String>>> call = webServiceAPI.getMovies(token);
        call.enqueue(new Callback<Map<String, List<String>>>() {
            @Override
            public void onResponse(Call<Map<String, List<String>>> call, Response<Map<String, List<String>>> response) {
                Log.e("Movies", "getMovies (plural) - got the Respond! " + response.code());
                if (response.isSuccessful()) {
                    Log.e("Movies", "response successful!");
                    Map<String, List<String>> moviesMap = response.body();

                    for (String categoryName : moviesMap.keySet()) {
                        android.util.Log.d("Movies", "Category: " + categoryName);
                        List<String> movies = moviesMap.get(categoryName);
                        android.util.Log.d("Movies", "Movies: " + movies.size());
                        Category category = new Category(categoryName, movies, true);
                        android.util.Log.d("Movies", "category -> room");
                        categoryDao.insertCategory(category);
                        android.util.Log.d("Movies", "category -> room good");
                        if (movies != null) {
                            for (String movieId : movies) {
                                android.util.Log.d("Movies", "  Movie ID: " + movieId);
                                getMovie(movieId);
                            }
                        }
                        android.util.Log.d("Movies", "Post the categories");
                        categoryLiveData.postValue(categoryDao.getCategories());
                        android.util.Log.d("Movies", "categories posted");
                    }
                }
                Log.e("Movies", "got the Respond! " + response.message());
            }


            @Override
            public void onFailure(Call<Map<String, List<String>>> call, Throwable t) {

            }
        });
    }
}
