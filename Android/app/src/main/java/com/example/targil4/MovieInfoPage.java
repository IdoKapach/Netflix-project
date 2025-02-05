package com.example.targil4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.example.targil4.viewModels.UserViewModel;
import com.google.android.material.button.MaterialButton;

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

        // load movie details from intent
        Intent intent = getIntent();
        if (intent == null) {
            throw new IllegalStateException("movie info Intent cannot be null");
        }
        String movieTitle = intent.getStringExtra("movieTitle");
        String movieUrl = getString(R.string.BaseURL).concat(intent.getStringExtra("movieUrl"));
        String movieBackdrop = getString(R.string.BaseURL).concat(intent.getStringExtra("movieBackdrop"));
        String movieDescription = intent.getStringExtra("movieDescription");

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
            watchIntent.putExtra("MovieUrl", movieUrl);
            // start movie watch activity
            startActivity(watchIntent);
        });

    }
}