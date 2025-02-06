package com.example.targil4.api;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface FileUploadAPI {
    @Multipart
    @POST("movies")
    Call<ResponseBody> upload(
            @Part MultipartBody.Part videoFile,
            @Part MultipartBody.Part imageFile,
            @Part("title") RequestBody title,
            @Part("description") RequestBody description,
            @Header("authorization") String token,
            @Part("categories") RequestBody categories
    );
}