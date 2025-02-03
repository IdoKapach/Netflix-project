package com.example.targil4; // Replace with your package name

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.targil4.api.FileUploadAPI;
import com.example.targil4.api.UriRequestBody;
import com.example.targil4.viewModels.UserViewModel;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdminFragment extends Fragment {

    private EditText editTextMovieTitle;
    private EditText editTextMovieDescription;
    private Button buttonAddMovie;
    private Button buttonSelectFile;
    private Button buttonSelectImage;
    private Uri videoUri;
    private Uri imageUri;
    private ActivityResultLauncher<PickVisualMediaRequest> videoPicker;
    private ActivityResultLauncher<PickVisualMediaRequest> imagePicker;
    private ActivityResultLauncher<String[]> requestPermissionLauncher;

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // register media pickers
        videoPicker = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
            if (uri != null) {
                Log.d("VideoPicker", "Selected URI: " + uri);
                videoUri = uri;
            } else {
                Log.d("VideoPicker", "No media selected");
            }
            });
        imagePicker = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
            if (uri != null) {
                Log.d("ImagePicker", "Selected URI: " + uri);
                imageUri = uri;
            } else {
                Log.d("ImagePicker", "No media selected");
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin, container, false);

        editTextMovieTitle = view.findViewById(R.id.editTextMovieTitle);
        editTextMovieDescription = view.findViewById(R.id.editTextMovieDescription);
        buttonAddMovie = view.findViewById(R.id.buttonAddMovie);
        buttonSelectFile = view.findViewById(R.id.buttonSelectFile);
        buttonSelectImage = view.findViewById(R.id.buttonSelectImage);

        buttonAddMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { addMovie(); }
        });

        buttonSelectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseVideo();
            }
        });

        buttonSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

        return view;
    }

    private void addMovie() {
        // fetch data from view
        String movieTitle = editTextMovieTitle.getText().toString();
        String movieDescription = editTextMovieDescription.getText().toString();

        // check if all fields are filled
        if (videoUri == null) {
//            Toast.makeText(getContext(), "Please select a file to upload.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (imageUri == null) {
//            Toast.makeText(getContext(), "Please select a file to upload.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (movieTitle.isEmpty()) {
            editTextMovieTitle.setError("Title is required");
            return;
        }
        if (movieDescription.isEmpty()) {
            editTextMovieDescription.setError("Description is required");
            return;
        }

        // upload the movie
        // Create our custom RequestBody instances using the content URIs.
        UriRequestBody videoRequestBody = new UriRequestBody(getContext(), videoUri, "video/*");
        UriRequestBody imageRequestBody = new UriRequestBody(getContext(), imageUri, "image/*");

        // create file names
        String videoFileName = movieTitle + "." + extractFileExtension(videoUri);
        String imageFileName = movieTitle + "." + extractFileExtension(imageUri);

        // Create the RequestBodies.
        RequestBody movieTitleRequestBody = RequestBody.create(MediaType.parse("text/plain"), movieTitle);
        RequestBody movieDescriptionRequestBody = RequestBody.create(MediaType.parse("text/plain"), movieDescription);

        // Wrap the RequestBodies in MultipartBody.Part objects.
        MultipartBody.Part videoPart = MultipartBody.Part.createFormData("videoFile", videoFileName, videoRequestBody);
        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("imageFile", imageFileName, imageRequestBody);

        // Build Retrofit instance.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.BaseURL))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create an instance of API interface.
        FileUploadAPI service = retrofit.create(FileUploadAPI.class);

        // read user token
        UserViewModel user = new ViewModelProvider(this).get(UserViewModel.class);
        String token = "Bearer " + user.getToken();
        Log.d("FileUpload", "Token: " + token);

        List<String> categoriesList = new ArrayList<>();
        String categoriesJson = new Gson().toJson(categoriesList);
        RequestBody categories = RequestBody.create(
                MediaType.parse("application/json"),
                categoriesJson
        );

        // Create the call and enqueue it.
        Call<ResponseBody> call = service.upload(videoPart, imagePart, movieTitleRequestBody, movieDescriptionRequestBody, token, categories);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.e("FileUpload", "Upload successful!");
                } else {
                    Log.e("FileUpload", "Upload failed: " + response.code() + " " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("FileUpload", "Error uploading file", t);
            }
        });
    }

    private void chooseVideo() {
        videoPicker.launch(new PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.VideoOnly.INSTANCE)
                .build());
    }

    private void chooseImage() {
        imagePicker.launch(new PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                .build());
    }

    private String extractFileExtension(Uri uri) {
        Context context = getContext();
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