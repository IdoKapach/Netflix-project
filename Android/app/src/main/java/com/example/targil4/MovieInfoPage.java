package com.example.targil4;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.example.targil4.adapters.MovieAdapter;
import com.example.targil4.viewModels.CategoryViewModelFactory;
import com.example.targil4.viewModels.RecommenderViewModel;
import com.example.targil4.viewModels.UserViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class MovieInfoPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_movie_info_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // get references to views
        ImageView backdrop = findViewById(R.id.backdrop);
        TextView title = findViewById(R.id.movieTitleView);
        ImageButton backButton = findViewById(R.id.backButton);
        MaterialButton playButton = findViewById(R.id.playButton);
        TextView description = findViewById(R.id.description);
        RecyclerView recommendList = findViewById(R.id.recommend_list);

        // load movie details from intent
        Intent intent = getIntent();
        if (intent == null) {
            throw new IllegalStateException("movie info Intent cannot be null");
        }
        String movieTitle = intent.getStringExtra("movieTitle");
        String movieBackdrop = getString(R.string.BaseURL).concat(intent.getStringExtra("movieBackdrop"));
        String movieUrl = intent.getStringExtra("movieUrl");
        String movieDescription = intent.getStringExtra("movieDescription"); // TODO: add description to movie model (in the api server)
        String movieId = intent.getStringExtra("movieId");

        // load movie details into views
        title.setText(movieTitle);
        description.setText(movieDescription);

        // get the user auth token for glide image
        UserViewModel userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        String userToken = userViewModel.getToken();

        // create the glide url with the token
        LazyHeaders headers = new LazyHeaders.Builder()
                .addHeader("authorization", "Bearer " + userToken)
                .build();

        GlideUrl glideUrl = new GlideUrl(movieBackdrop, headers);

        Glide.with(this)
                .load(glideUrl)
                .placeholder(R.drawable.movie_card_placeholder)
                .error(R.drawable.movie_card_placeholder)
                .into(backdrop);

        // set click listener for back button
        backButton.setOnClickListener(v -> {
            finish();
        });

        // set click listener for play button
        playButton.setOnClickListener(v -> {
            Intent watchIntent = new Intent(MovieInfoPage.this, MovieWatchPage.class);
            // add movie details to intent
            watchIntent.putExtra("movieTitle", movieTitle);
            watchIntent.putExtra("movieUrl", movieUrl);
            // start movie watch activity
            startActivity(watchIntent);
        });

        // set adapter for recommended movies
        MovieAdapter recommendAdapter = new MovieAdapter(this, new ArrayList<>(), userToken);
        recommendList.setAdapter(recommendAdapter);
        recommendList.setLayoutManager(new GridLayoutManager(this, 4));

        // create recommenderViewModel instance for recommendations
        CategoryViewModelFactory factory = new CategoryViewModelFactory(userViewModel);
        RecommenderViewModel recommenderViewModel = new ViewModelProvider(this, factory).get(RecommenderViewModel.class);
        // signal movie watched
        recommenderViewModel.watchedMovie(movieId);
        // find recommendations
        recommenderViewModel.findRecommendations(movieId);
        // observe recommended movies - and display them
        recommenderViewModel.getRecommendations().observe(this, movies -> {
            if (movies == null) {
                return;
            }
            Log.d("recommended_movies", movies.toString());
            recommendAdapter.updateMovies(movies);
        });

    }
}