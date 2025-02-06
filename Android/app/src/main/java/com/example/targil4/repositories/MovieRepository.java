package com.example.targil4.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.targil4.MyApplication;
import com.example.targil4.api.MovieAPI;
import com.example.targil4.entity.Category;
import com.example.targil4.entity.Movie;
import com.example.targil4.room.AppDB;
import com.example.targil4.room.CategoryDao;
import com.example.targil4.room.MovieDao;
import com.example.targil4.viewModels.UserViewModel;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class MovieRepository {
    private CategoriesLiveData categoriesLiveData;
    private MoviesLiveData moviesLiveData;
    private CategoryDao categoryDao;
    private MovieDao movieDao;
    private MovieAPI movieAPI;

    public MovieRepository(UserViewModel userViewModel) {
        AppDB db = Room.databaseBuilder(MyApplication.context,
                        AppDB.class, "FooDB")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries().build();
        this.movieDao = db.MovieDao();
        this.categoryDao = db.CategoryDao();
        categoriesLiveData = new CategoriesLiveData();
        moviesLiveData = new MoviesLiveData();
        movieAPI = new MovieAPI(categoriesLiveData, moviesLiveData, userViewModel, movieDao, categoryDao);
        movieAPI.getMovies();
    }

    class CategoriesLiveData extends MutableLiveData<List<Category>> {
        public CategoriesLiveData() {
            super();
        }

        @Override
        protected void onActive() {
            android.util.Log.d("Categories", "on active activated");
            super.onActive();
            new Thread(() -> {
                try {
                    List<Category> category = categoryDao.getCategories();
                    android.util.Log.d("Movies", "categories here are: " + category.size());
                    categoriesLiveData.postValue(category);
                } catch (Exception e) {}
            }).start();
        }
    }

    class MoviesLiveData extends  MutableLiveData<List<Movie>> {
        public MoviesLiveData() {
            super();
        }

        @Override
        protected void onActive() {
            super.onActive();
        }
    }

    public LiveData<List<Movie>> getMovies() {
        return moviesLiveData;
    }

    public LiveData<List<Category>> getCategories() {
        return categoriesLiveData;
    }

    public void editMovie(Movie movie) {
        movieAPI.editMovie(movie);
    }


    public void addMovie(MultipartBody.Part videoPart, MultipartBody.Part imagePart, RequestBody movieTitleRequestBody, RequestBody movieDescriptionRequestBody, RequestBody categories) {
        movieAPI.addMovie(videoPart, imagePart, movieTitleRequestBody, movieDescriptionRequestBody, categories);
    }

    public void deleteMovie(Movie movie) {
        movieAPI.deleteMovie(movie.get_id());
    }


}
