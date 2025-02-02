package com.example.targil4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.targil4.adapters.MovieAdapter;
import com.example.targil4.entity.Movie;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private RecyclerView searchResultsRecycler;
    private EditText searchEditText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        searchResultsRecycler = view.findViewById(R.id.search_results_recycler);
        searchEditText = view.findViewById(R.id.search_edit_text);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        searchResultsRecycler.setLayoutManager(gridLayoutManager);
        searchResultsRecycler.setAdapter(new MovieAdapter(requireContext(), getDummyMovies(), true));
        // TODO: Implement search logic here
    }

    private List<Movie> getDummyMovies() {
        // stump - need to implement data fetching
        // we don't need to actually use trending\popular - just random is good
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Movie 1", "https://via.placeholder.com/150", "Description 1", "https://example.com/movie"));
        movies.add(new Movie("Movie 2", "https://via.placeholder.com/150", "Description 2", "https://example.com/movie"));
        movies.add(new Movie("Movie 3", "https://via.placeholder.com/150", "Description 3", "https://example.com/movie"));
        return movies;
    }
}