package com.example.targil4;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

import java.net.URI;

public class MovieWatchPage extends AppCompatActivity {
    private ExoPlayer player;
    private PlayerView playerView;
    private ImageButton backButton;
    private TextView videoTitleTextView;
    private long playbackPosition = 0;
    private int currentWindow = 0;
    private boolean playWhenReady = true;

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

        // Initialize views
        playerView = findViewById(R.id.videoPlayerView);
        backButton = findViewById(R.id.backButton);
        videoTitleTextView = findViewById(R.id.videoTitleTextView);

        // get info from intent
        String videoTitle = getIntent().getStringExtra("videoTitle");
        String videoUrl = getIntent().getStringExtra("videoUrl");

        // set video title
        videoTitleTextView.setText(videoTitle);

        // Initialize ExoPlayer
        player = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(player);

        // build movie video file
        MediaItem mediaItem;
        if (videoUrl != null) {
            mediaItem = MediaItem.fromUri(videoUrl);
        } else {
            // use placeholder video if no url is provided
            Uri placeholderUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.sample_video);
            mediaItem = MediaItem.fromUri(placeholderUri);
        }
        player.setMediaItem(mediaItem);
        player.prepare();
        player.play();

        // set back button click listener
        backButton.setOnClickListener(v -> {
            finish();
        });

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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release();
    }
}