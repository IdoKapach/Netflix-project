package com.example.targil4; // Replace with your package name

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
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
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AdminFragment extends Fragment {

    private EditText editTextMovieTitle;
    private EditText editTextMovieDescription;
    private Button buttonAddMovie;
    private Button buttonSelectFile;
    private Button buttonSelectImage;
    private Uri selectedFileUri;
    private ActivityResultLauncher<Intent> filePicker;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        filePicker = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            if (data != null && data.getData() != null) {
                                selectedFileUri = data.getData();
                                // do something with the file (local) uri (memory location in phone)
                                Log.d("FILE_URI", selectedFileUri.toString());
                            }
                        }
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
            public void onClick(View v) {
                addMovie();
            }
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
        // implement movie adding logic here
    }

    private void chooseVideo() {
        Intent fileIntent = new Intent(Intent.ACTION_GET_CONTENT);
        fileIntent.setType("video/*"); // only allow video files
        fileIntent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            filePicker.launch(Intent.createChooser(fileIntent, "Select Video File to Upload"));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getContext(), "Please install a File Manager.", Toast.LENGTH_SHORT).show();
        }
    }

    private void chooseImage() {
        Intent fileIntent = new Intent(Intent.ACTION_GET_CONTENT);
        fileIntent.setType("image/*"); // only allow image files
        fileIntent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            filePicker.launch(Intent.createChooser(fileIntent, "Select Image File to Upload"));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getContext(), "Please install a File Manager.", Toast.LENGTH_SHORT).show();
        }
    }
}