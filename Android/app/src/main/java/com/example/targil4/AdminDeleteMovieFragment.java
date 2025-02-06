package com.example.targil4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.targil4.entity.Movie;
import com.example.targil4.viewModels.CategoryViewModelFactory;
import com.example.targil4.viewModels.MovieViewModel;
import com.example.targil4.viewModels.QueryViewModel;
import com.example.targil4.viewModels.UserViewModel;

public class AdminDeleteMovieFragment extends Fragment {
    private CategoryViewModelFactory factory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_delete_movie, container, false);

        UserViewModel userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        factory = new CategoryViewModelFactory(userViewModel);
        MovieViewModel movieViewModel = new ViewModelProvider(this, factory).get(MovieViewModel.class);
        QueryViewModel queryViewModel = new ViewModelProvider(this, factory).get(QueryViewModel.class);

        EditText editTextMovieTitle = view.findViewById(R.id.movieToDeleteName);

        Button buttonAddCategory = view.findViewById(R.id.buttonAddMovie);
        buttonAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextMovieTitle.getText().toString();

                if (name.isEmpty()) {
                    editTextMovieTitle.setError("Title is required");
                    return;
                }

                queryViewModel.fetchMovies(name);
                queryViewModel.getMovies().observe(getViewLifecycleOwner(), queryList -> {
                    if (queryList == null) {
                        editTextMovieTitle.setError("Movie name does not exists");
                        queryViewModel.getMovies().removeObservers(getViewLifecycleOwner());
                        return;
                    }
                    for (Movie movie : queryList) {
                        if (movie.getMovieName().equals(name)) {
                            movieViewModel.deleteMovie(movie);
                            editTextMovieTitle.setText("");
                            queryViewModel.getMovies().removeObservers(getViewLifecycleOwner());
                            return;
                        }
                    }
                    editTextMovieTitle.setError("Movie name does not exists");
                    queryViewModel.getMovies().removeObservers(getViewLifecycleOwner());
                });
            }
        });

        return view;
    }
}
