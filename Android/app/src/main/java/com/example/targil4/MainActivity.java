package com.example.targil4;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.VideoView;
import android.net.Uri;


import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.datasource.DataSource;
import androidx.media3.datasource.DefaultHttpDataSource;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.exoplayer.source.MediaSource;
import androidx.media3.exoplayer.source.ProgressiveMediaSource;
import androidx.media3.ui.PlayerView;

import com.example.targil4.entity.Movie;
import com.example.targil4.viewModels.CategoryViewModel;
import com.example.targil4.viewModels.CategoryViewModelFactory;
import com.example.targil4.viewModels.MovieViewModel;
import com.example.targil4.viewModels.UserViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;
    private UserViewModel userViewModel;
    private MenuItem adminItem;
    private MaterialButton logoutButton;
    private PlayerView videoView;
    private ExoPlayer topPlayer;
    private MaterialButton darkModeButton;

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

        // Initialize ViewModel
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        // Set up Bottom Navigation
        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.home) {
                    // play random video
                    setupRandomVideo();
                    // Load the Home fragment
                    loadFragment(new HomeFragment());
                    return true;
                } else if (itemId == R.id.search) {
                    // pause the random video
                    topPlayer.release();
                    // Load the Search fragment
                    loadFragment(new SearchFragment());
                    return true;
                } else if (itemId == R.id.admin) {
                    // pause the random video
                    topPlayer.release();
                    // Load the Admin fragment
                    loadFragment(new AdminFragment());
                    return true;
                }
                return false;
            }
        });

        // Load the default fragment (HomeFragment)
        loadFragment(new HomeFragment());

        // Check if the logged in user is an admin and show the admin menu item accordingly
        adminItem = bottomNav.getMenu().findItem(R.id.admin);
        if (userViewModel.isAdmin()) {
            adminItem.setVisible(true);
        } else {
            adminItem.setVisible(false);
        }


        if (userViewModel.isDarkMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        // add logout button
        logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(v -> {
            topPlayer.release();
            userViewModel.signOut();
            Intent intent = new Intent(MainActivity.this, UnregisteredMainpage.class);
            startActivity(intent);
        });

        // set up the random video in top player
        Log.d("MainActivity", "setupRandomVideo starting");
        setupRandomVideo();
        Log.d("MainActivity", "setupRandomVideo finished");

        darkModeButton = findViewById(R.id.DarkMode);
        darkModeButton.setOnClickListener(v -> {
            int nightMode = AppCompatDelegate.getDefaultNightMode();
            AppCompatDelegate.setDefaultNightMode(
                    nightMode == AppCompatDelegate.MODE_NIGHT_YES ?
                            AppCompatDelegate.MODE_NIGHT_NO :
                            AppCompatDelegate.MODE_NIGHT_YES
            );
            userViewModel.updateDarkMode();
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment); // Replace the fragment in the container
        transaction.commit();
    }

    @OptIn(markerClass = UnstableApi.class)
    private void setupRandomVideo() {
        // get the video view
        topPlayer = new ExoPlayer.Builder(this).build();
        videoView = findViewById(R.id.backdrop_video);
        videoView.setPlayer(topPlayer);
        // remove player controls and sound
        topPlayer.setVolume(0);
        videoView.setUseController(false);
        videoView.setShowBuffering(PlayerView.SHOW_BUFFERING_NEVER);

        // get the video title view
        TextView randomVideoTitle = findViewById(R.id.random_video_title);

        // get random video from view model
        CategoryViewModelFactory factory = new CategoryViewModelFactory(userViewModel);
        MovieViewModel movieViewModel = new ViewModelProvider(this, factory).get(MovieViewModel.class);
        LiveData<List<Movie>> allMovies = movieViewModel.getHomePageMovies();

        // play the movie on observe
        allMovies.observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                if (movies != null && !movies.isEmpty()) {
                    Movie randomMovie = movies.get((int) (Math.random() * movies.size()));
                    randomVideoTitle.setText(randomMovie.getMovieName());
                    String moviePath = randomMovie.getMovieUrl();
                    String movieUrl = getString(R.string.BaseURL) + moviePath;
                    playVideo(movieUrl);
                }
            }
        });
    }

    @OptIn(markerClass = UnstableApi.class)
    private void playVideo(String movieUrl) {
        // Create a DataSource.Factory that adds the token to the headers
        DataSource.Factory dataSourceFactory = new DefaultHttpDataSource.Factory()
                .setDefaultRequestProperties(
                        java.util.Collections.singletonMap("Authorization", "Bearer " + userViewModel.getToken())
                );

        // Create a MediaSource using the custom DataSource.Factory
        MediaSource mediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(Uri.parse(movieUrl)));

        // Set the media source to be played.
        topPlayer.setMediaSource(mediaSource);
        // Prepare the player.
        topPlayer.prepare();
        // Start the playback.
        topPlayer.play();
    }
}


