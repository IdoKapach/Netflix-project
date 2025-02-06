package com.example.targil4.viewModels;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.targil4.MyApplication;
import com.example.targil4.api.UriRequestBody;
import com.example.targil4.entity.Category;
import com.example.targil4.entity.Movie;
import com.example.targil4.repositories.MovieRepository;
import com.google.gson.Gson;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class MovieViewModel extends ViewModel {

    private MovieRepository movieRepo;
    private LiveData<List<Movie>> movies;
    private LiveData<List<Category>> categories;

    MovieViewModel(UserViewModel userViewModel) {
        movieRepo = new MovieRepository(userViewModel);
        movies = movieRepo.getMovies();
        categories = movieRepo.getCategories();
    }

    public LiveData<List<Category>> getPromotedCategories() {
        return categories;
    }

    public LiveData<List<Movie>> getHomePageMovies() {
        movieRepo.getCategories();
        return movies;
    }

    public void addMovie(String movieTitle, String movieDescription, Uri videoUri,Uri imageUri, List<String> categoriesList) {
        UriRequestBody videoRequestBody = new UriRequestBody(MyApplication.context, videoUri, "video/*");
        UriRequestBody imageRequestBody = new UriRequestBody(MyApplication.context, imageUri, "image/*");

        // create file names
        String videoFileName = movieTitle + "." + extractFileExtension(videoUri);
        String imageFileName = movieTitle + "." + extractFileExtension(imageUri);

        // Create the RequestBodies.
        RequestBody movieTitleRequestBody = RequestBody.create(MediaType.parse("text/plain"), movieTitle);
        RequestBody movieDescriptionRequestBody = RequestBody.create(MediaType.parse("text/plain"), movieDescription);

        // Wrap the RequestBodies in MultipartBody.Part objects.
        MultipartBody.Part videoPart = MultipartBody.Part.createFormData("videoFile", videoFileName, videoRequestBody);
        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("imageFile", imageFileName, imageRequestBody);

        String categoriesJson = new Gson().toJson(categoriesList);
        RequestBody categories = RequestBody.create(
                MediaType.parse("application/json"),
                categoriesJson
        );
        movieRepo.addMovie(videoPart, imagePart, movieTitleRequestBody, movieDescriptionRequestBody, categories);
    }

    public void deleteMovie(Movie movie) {
        movieRepo.deleteMovie(movie);
    }

    public void editMovie(Movie movie) {
        movieRepo.editMovie(movie);
    }

    public Movie getMovie(String id) {
        android.util.Log.d("Movies", "viewModelling");
        if (movies == null || movies.getValue() == null) {
            android.util.Log.d("Movies", "its empty");
            return null;
        }
        List<Movie> movieList = movies.getValue();
        if (movieList != null) {
            for (Movie movie : movieList) {

                if (movie.get_id().equals(id)) {
                    android.util.Log.d("Movies", "found: " + movie.get_id());
                    return movie;
                }
            }
        }
        android.util.Log.d("Movies", "didnt find");
        return null;
    }

    private String extractFileExtension(Uri uri) {
        Context context = MyApplication.context;
        if (context == null) {
            throw new IllegalStateException("Context is null");
        }
        String mimeType = context.getContentResolver().getType(uri);
        if (mimeType == null) {
            return "mp4"; // default value
        }
        String[] split = mimeType.split("/");
        if (split.length != 2) {
            return "mp4"; // default value
        }
        return split[1].toLowerCase();
    }
}
