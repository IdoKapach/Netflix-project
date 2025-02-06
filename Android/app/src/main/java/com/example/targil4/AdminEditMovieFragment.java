package com.example.targil4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.targil4.entity.Category;
import com.example.targil4.entity.Movie;
import com.example.targil4.viewModels.CategoryViewModel;
import com.example.targil4.viewModels.CategoryViewModelFactory;
import com.example.targil4.viewModels.MovieViewModel;
import com.example.targil4.viewModels.QueryViewModel;
import com.example.targil4.viewModels.UserViewModel;

import java.util.ArrayList;
import java.util.List;

public class AdminEditMovieFragment extends Fragment {
    private List<String> categories;
    private AutoCompleteTextView autoCompleteCategory;
    private CategoryViewModelFactory factory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_edit_movie, container, false);

        UserViewModel userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        factory = new CategoryViewModelFactory(userViewModel);
        CategoryViewModel categoryViewModel = new ViewModelProvider(this, factory).get(CategoryViewModel.class);
        MovieViewModel movieViewModel = new ViewModelProvider(this, factory).get(MovieViewModel.class);
        QueryViewModel queryViewModel = new ViewModelProvider(this, factory).get(QueryViewModel.class);

        EditText editTextMovieTitle = view.findViewById(R.id.editTextMovieTitle);
        EditText editTextMovieToEdit = view.findViewById(R.id.movieToEditName);
        EditText editTextDescription = view.findViewById(R.id.editTextMovieDescription);
        autoCompleteCategory = view.findViewById(R.id.autoCompleteCategory);

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




        Button buttonAddCategory = view.findViewById(R.id.buttonAddMovie);
        buttonAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextMovieTitle.getText().toString();
                String oldName = editTextMovieToEdit.getText().toString();
                String newDescription = editTextDescription.getText().toString();
                String newCategory = autoCompleteCategory.getText().toString();
                if (oldName.isEmpty()) {
                    editTextMovieToEdit.setError("No movie selected");
                    return;
                }
                if (name.isEmpty()) {
                    editTextMovieTitle.setError("Title is required");
                    return;
                }
                if (newDescription.isEmpty()) {
                    editTextDescription.setError("Title is required");
                    return;
                }
                if (newCategory.isEmpty()) {
                    autoCompleteCategory.setError("Title is required");
                    return;
                }

                List<String> categoryList = new ArrayList<>();
                categoryList.add(newCategory);
                queryViewModel.fetchMovies(oldName);
                queryViewModel.getMovies().observe(getViewLifecycleOwner(), queryList -> {
                    if (queryList == null) {
                        editTextMovieToEdit.setError("Movie name does not exists");
                        queryViewModel.getMovies().removeObservers(getViewLifecycleOwner());
                        return;
                    }
                    for (Movie movie : queryList) {
                        if (movie.getMovieName().equals(oldName)) {
                            movie.setMovieName(name);
                            movie.setCategories(categoryList);
                            movie.setDescription(newDescription);
                            movieViewModel.editMovie(movie);
                            editTextMovieTitle.setText("");
                            autoCompleteCategory.setText("");
                            editTextMovieToEdit.setText("");
                            editTextDescription.setText("");
                            queryViewModel.getMovies().removeObservers(getViewLifecycleOwner());
                            return;
                        }
                    }
                    editTextMovieToEdit.setError("Movie name does not exists");
                    queryViewModel.getMovies().removeObservers(getViewLifecycleOwner());
                });
            }
        });

        return view;
    }
}
