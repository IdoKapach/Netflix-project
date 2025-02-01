package com.example.targil4;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.targil4.adapters.MovieAdapter;
import com.example.targil4.entity.Movie;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView trendingRecycler;
    private RecyclerView popularRecycler;
    private RecyclerView recommendedRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // init recycler views
        trendingRecycler = findViewById(R.id.trending_recycler);
        popularRecycler = findViewById(R.id.popular_recycler);
        recommendedRecycler = findViewById(R.id.recommended_recycler);

        // get data to inflate recycler views (stump)
        List<Movie> trendingMovies = getRandomMovies();
        List<Movie> popularMovies = getRandomMovies();
        List<Movie> recommendedMovies = getRecommendedMovies();

        // create adapters
        MovieAdapter trendingAdapter = new MovieAdapter(this, trendingMovies);
        MovieAdapter popularAdapter = new MovieAdapter(this, popularMovies);
        MovieAdapter recommendedAdapter = new MovieAdapter(this, recommendedMovies);

        // set layout managers (horizontal)
        trendingRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        popularRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recommendedRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // set adapters to views
        trendingRecycler.setAdapter(trendingAdapter);
        popularRecycler.setAdapter(popularAdapter);
        recommendedRecycler.setAdapter(recommendedAdapter);
    }

    private List<Movie> getRecommendedMovies() {
        // stump - need to implement data fetching
        // data based on recommend api route
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Movie 1", "https://via.placeholder.com/150"));
        movies.add(new Movie("Movie 2", "https://via.placeholder.com/150"));
        movies.add(new Movie("Movie 3", "https://via.placeholder.com/150"));
        movies.add(new Movie("Movie 4", "https://via.placeholder.com/150"));
        movies.add(new Movie("Movie 5", "https://via.placeholder.com/150"));
        movies.add(new Movie("Movie 6", "https://via.placeholder.com/150"));
        movies.add(new Movie("Movie 7", "https://via.placeholder.com/150"));

        return movies;
    }

    private List<Movie> getRandomMovies() {
        // stump - need to implement data fetching
        // we don't need to actually use trending\popular - just random is good
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Movie 1", "https://via.placeholder.com/150"));
        movies.add(new Movie("Movie 2", "https://via.placeholder.com/150"));
        movies.add(new Movie("Movie 3", "https://via.placeholder.com/150"));
        return movies;
    }
}