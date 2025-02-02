package com.example.targil4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.targil4.adapters.MovieAdapter;
import com.example.targil4.entity.Movie;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView trendingRecycler;
    private RecyclerView popularRecycler;
    private RecyclerView recommendedRecycler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        trendingRecycler = view.findViewById(R.id.trending_recycler);
        popularRecycler = view.findViewById(R.id.popular_recycler);
        recommendedRecycler = view.findViewById(R.id.recommended_recycler);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // get data to inflate recycler views (stump)
        List<Movie> trendingMovies = getRandomMovies();
        List<Movie> popularMovies = getRandomMovies();
        List<Movie> recommendedMovies = getRecommendedMovies();

        // create adapters
        MovieAdapter trendingAdapter = new MovieAdapter(getContext(), trendingMovies);
        MovieAdapter popularAdapter = new MovieAdapter(getContext(), popularMovies);
        MovieAdapter recommendedAdapter = new MovieAdapter(getContext(), recommendedMovies);

        // set layout managers (horizontal)
        trendingRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        popularRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recommendedRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        // set adapters to views
        trendingRecycler.setAdapter(trendingAdapter);
        popularRecycler.setAdapter(popularAdapter);
        recommendedRecycler.setAdapter(recommendedAdapter);
    }

    private List<Movie> getRecommendedMovies() {
        // stump - need to implement data fetching
        // data based on recommend api route
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Movie 1", "https://via.placeholder.com/150", "Description 1", "https://example.com/movie"));
        movies.add(new Movie("Movie 2", "https://via.placeholder.com/150", "Description 2", "https://example.com/movie"));
        movies.add(new Movie("Movie 3", "https://via.placeholder.com/150", "Description 3", "https://example.com/movie"));
        movies.add(new Movie("Movie 4", "https://via.placeholder.com/150", "Description 4", "https://example.com/movie"));
        movies.add(new Movie("Movie 5", "https://via.placeholder.com/150", "Description 5", "https://example.com/movie"));
        movies.add(new Movie("Movie 6", "https://via.placeholder.com/150", "Description 6", "https://example.com/movie"));
        movies.add(new Movie("Movie 7", "https://via.placeholder.com/150", "Description 7", "https://example.com/movie"));

        return movies;
    }

    private List<Movie> getRandomMovies() {
        // stump - need to implement data fetching
        // we don't need to actually use trending\popular - just random is good
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Movie 1", "https://via.placeholder.com/150", "Description 1", "https://example.com/movie"));
        movies.add(new Movie("Movie 2", "https://via.placeholder.com/150", "Description 2", "https://example.com/movie"));
        movies.add(new Movie("Movie 3", "https://via.placeholder.com/150", "Description 3", "https://example.com/movie"));
        return movies;
    }
}