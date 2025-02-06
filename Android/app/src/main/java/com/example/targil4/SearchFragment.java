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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.targil4.adapters.MovieAdapter;
import com.example.targil4.entity.Movie;
import com.example.targil4.entity.User;
import com.example.targil4.viewModels.CategoryViewModelFactory;
import com.example.targil4.viewModels.QueryViewModel;
import com.example.targil4.viewModels.UserViewModel;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private RecyclerView searchResultsRecycler;
    private EditText searchEditText;
    private MovieAdapter movieAdapter;
    private QueryViewModel queryViewModel;
    private Button searchButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        searchResultsRecycler = view.findViewById(R.id.search_results_recycler);
        searchEditText = view.findViewById(R.id.search_edit_text);
        searchButton = view.findViewById(R.id.search_button);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // set button on click to fetch query
        searchButton.setOnClickListener(v -> {
            String query = searchEditText.getText().toString();
            queryViewModel.fetchMovies(query);
        });

        // init the query view model
        UserViewModel userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        CategoryViewModelFactory factory = new CategoryViewModelFactory(userViewModel);
        queryViewModel = new ViewModelProvider(this, factory).get(QueryViewModel.class);

        // set up the recycler view (grid layout)
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 4);
        searchResultsRecycler.setLayoutManager(gridLayoutManager);
        String userToken = userViewModel.getToken();

        // observe incoming movies
        queryViewModel.getMovies().observe(getViewLifecycleOwner(), movies -> {
            if (movies == null) {
                movies = new ArrayList<>();
            }
            if (movieAdapter == null) {
                movieAdapter = new MovieAdapter(getContext(), movies, userToken);
                searchResultsRecycler.setAdapter(movieAdapter);
            } else {
                movieAdapter.updateMovies(movies);
            }
        });
    }
}