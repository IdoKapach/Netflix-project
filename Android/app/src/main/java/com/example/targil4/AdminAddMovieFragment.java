package com.example.targil4;

import static com.example.targil4.MyApplication.context;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.targil4.entity.Category;
import com.example.targil4.viewModels.CategoryViewModel;
import com.example.targil4.viewModels.CategoryViewModelFactory;
import com.example.targil4.viewModels.MovieViewModel;
import com.example.targil4.viewModels.RecommenderViewModel;
import com.example.targil4.viewModels.UserViewModel;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdminAddMovieFragment extends Fragment {

    private EditText editTextMovieTitle;
    private EditText editTextMovieDescription;
    private EditText editTextMovieCategory;
    private Button buttonAddMovie;
    private Button buttonSelectFile;
    private Button buttonSelectImage;
    private Uri videoUri;
    private Uri imageUri;
    private AutoCompleteTextView autoCompleteCategory;
    private CategoryViewModelFactory factory;
    private ActivityResultLauncher<PickVisualMediaRequest> videoPicker;
    private ActivityResultLauncher<PickVisualMediaRequest> imagePicker;
    private ActivityResultLauncher<String[]> requestPermissionLauncher;
    private List<String> categories;

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
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_add_movie, container, false);

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

        autoCompleteCategory = view.findViewById(R.id.autoCompleteCategory);
        TextInputLayout categorySelect = view.findViewById(R.id.category);

        UserViewModel userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        factory = new CategoryViewModelFactory(userViewModel);
        CategoryViewModel categoryViewModel = new ViewModelProvider(this, factory).get(CategoryViewModel.class);

        categories = new ArrayList<>();
        categoryViewModel.getCategories().observe(getViewLifecycleOwner(), categoryList -> {

            if (categoryList == null) return;
            categories.clear();
            for (Category category : categoryList) {
                categories.add(category.getName());
            }
            // Create an ArrayAdapter with the categories
            ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, categories);

            // Set the adapter to the AutoCompleteTextView
            autoCompleteCategory.setAdapter(adapter);

            autoCompleteCategory.setOnClickListener(v -> autoCompleteCategory.showDropDown());

        });




        return view;
    }

    private void addMovie() {
        // fetch data from view
        String movieTitle = editTextMovieTitle.getText().toString();
        String movieDescription = editTextMovieDescription.getText().toString();
        String movieCategory = autoCompleteCategory.getText().toString();
        android.util.Log.d("createUser", "try to add! " + movieTitle + ", " + movieDescription + ", " + movieCategory);

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
        if (movieCategory.isEmpty()) {
            autoCompleteCategory.setError("Category is required");
            return;
        }

        List<String> categoriesList = new ArrayList<>();
        categoriesList.add(movieCategory);
        MovieViewModel movieViewModel = new ViewModelProvider(this, factory).get(MovieViewModel.class);
        movieViewModel.addMovie(movieTitle, movieDescription, videoUri, imageUri, categoriesList);
        editTextMovieTitle.setText("");
        autoCompleteCategory.setText("");
        editTextMovieDescription.setText("");
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


}

