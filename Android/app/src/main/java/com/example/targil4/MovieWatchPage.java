package com.example.targil4;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.PixelCopy;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.media3.common.MediaItem;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.datasource.DefaultHttpDataSource;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.exoplayer.source.MediaSource;
import androidx.media3.exoplayer.source.ProgressiveMediaSource;
import androidx.media3.ui.PlayerView;

import com.example.targil4.viewModels.UserViewModel;

import java.util.Map;

import okhttp3.OkHttpClient;

public class MovieWatchPage extends AppCompatActivity {
    private ExoPlayer player;
    private PlayerView playerView;
    private ImageButton backButton;
    private TextView videoTitleTextView;
    private long playbackPosition = 0;
    private int currentWindow = 0;
    private boolean playWhenReady = true;
    private final OkHttpClient client = new OkHttpClient();
    private String movieId;
    private String videoUrl;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_movie_watch_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // init user view model for token
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        // Initialize views
        playerView = findViewById(R.id.videoPlayerView);
        backButton = findViewById(R.id.backButton);
        videoTitleTextView = findViewById(R.id.videoTitleTextView);

        // get info from intent
        String videoTitle = getIntent().getStringExtra("videoTitle");
        movieId = getIntent().getStringExtra("movieId");

        // set video title
        videoTitleTextView.setText(videoTitle);

        // set back button click listener
        backButton.setOnClickListener(v -> {
            finish();
        });

        // temp url until movie repo is available
        videoUrl = getString(R.string.BaseURL).concat("media/movies/videos/t.mp4");
        Log.d("MovieWatchPage", "Video URL: " + videoUrl);
        initializePlayer();

        // restore playback state
        if (savedInstanceState != null) {
            playbackPosition = savedInstanceState.getLong("playbackPosition", 0);
            currentWindow = savedInstanceState.getInt("currentWindow", 0);
            playWhenReady = savedInstanceState.getBoolean("playWhenReady", true);
            player.seekTo(currentWindow, playbackPosition);
            player.setPlayWhenReady(playWhenReady);
        }
    }

    // prevent video restart on orientation change (flip)
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onStart() {
        super.onStart();
        playerView.onResume();
    }

    @Override
    protected void onResume() {
        super.onResume();
        playerView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        playerView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        playerView.onPause();
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentMediaItemIndex();
            playWhenReady = player.getPlayWhenReady();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
            player = null;
        }
    }

    @UnstableApi
    private void initializePlayer() {
        // check if url is inited (should come from room)
        if (videoUrl == null || videoUrl.isEmpty()) {
            Log.e("MovieWatchPage", "Video URL is null or empty");
            return;
        }

        // get user token for auth
        String userToken = userViewModel.getToken();

        // create an http data source factory with the auth token attached
        DefaultHttpDataSource.Factory dataSourceFactory = new DefaultHttpDataSource.Factory()
                .setDefaultRequestProperties(Map.of("authorization", "Bearer " + userToken));

        // create a media source that uses the data source with token attached
        MediaSource.Factory mediaSourceFactory = new ProgressiveMediaSource.Factory(dataSourceFactory);

        // create the exoplayer with the custom media source
        player = new ExoPlayer.Builder(this)
                .setMediaSourceFactory(mediaSourceFactory)
                .build();

        // set the player in the view to use the exo player
        playerView.setPlayer(player);

        // build movie video file
        MediaItem mediaItem = MediaItem.fromUri(videoUrl);
        player.setMediaItem(mediaItem);
        player.prepare();
        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);
    }
}

