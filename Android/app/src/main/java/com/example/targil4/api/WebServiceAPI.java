package com.example.targil4.api;

import com.example.targil4.entity.Category;
//import com.example.targil4.entity.Movie;
import com.example.targil4.entity.Movie;
import com.example.targil4.entity.User;
import com.example.targil4.entity.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface WebServiceAPI {
    @POST("users")
    Call<UserResponse> createUser(@Body User user);

    @POST("tokens")
    Call<UserResponse> loginUser(@Body User user);
/*
    @GET("movies")
    Call<List<Category>> getMovies(@Header("authorization") String token);

    @POST("movies")
    Call<Movie> addMovie(@Header("authorization") String token, @Body Movie movie);

    @GET("movies/:id")
    Call<Movie> getMovie(@Header("authorization") String token);

    @PUT("movies/:id")
    Call<Movie> editMovie(@Header("authorization") String token, @Body Movie movie);

    @DELETE("movies/:id")
    Call<Movie> removeMovie(@Header("authorization") String token);
*/
    @GET("categories")
    Call<List<Category>> getCategories(@Header("authorization") String token);

    @POST("categories")
    Call<Category> addCategory(@Header("authorization") String token, @Body Category category);

    @GET("categories/{id}")
    Call<Category> getCategory(@Header("authorization") String token, @Path("id") String id);

    @PATCH("categories/{id}")
    Call<Category> editCategory(@Header("authorization") String token, @Path("id") String id, @Body Category category);

    @DELETE("categories/{id}")
    Call<Category> deleteCategory(@Header("authorization") String token, @Path("id") String id);

    @GET("movies/search/{query}")
    Call<List<Movie>> getQuery(@Header("authorization") String token, @Path("query") String query);
}
