package com.example.targil4.adapters;// MovieAdapter.java
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.example.targil4.MovieInfoPage;
import com.example.targil4.R;
import com.example.targil4.R;
import com.example.targil4.entity.Movie;
import com.example.targil4.viewModels.MovieViewModel;
import com.example.targil4.viewModels.UserViewModel;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> movies;
    private Context context;
    private String userToken;
    // default constructor
    public MovieAdapter(Context context, List<Movie> movies, String userToken) {
        this.context = context;
        this.movies = movies;
        this.userToken = userToken;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        // add the movie path to the server url for call
        String imageServerUrl = context.getString(R.string.BaseURL).concat(movie.getImageUrl());
        Log.d("imageServerUrl",  imageServerUrl);

        // add the token to the glide request
        LazyHeaders headers = new LazyHeaders.Builder()
                .addHeader("authorization", "Bearer " + userToken)
                .build();

        // create the glideUrl with the new headers
        GlideUrl glideUrl = new GlideUrl(imageServerUrl, headers);

        // set movie details
        Glide.with(context)
                .load(glideUrl)
                .placeholder(R.drawable.movie_card_placeholder) // display while loading
                .error(R.drawable.movie_card_placeholder) // display on error
                .into(holder.moviePoster);
        holder.movieTitle.setText(movie.getMovieName());

        // set onClickListener for the item (go to movie info page)
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = holder.itemView.getContext();
                Intent intent = new Intent(context, MovieInfoPage.class);
                intent.putExtra("movieTitle", movie.getMovieName());
                intent.putExtra("movieBackdrop", movie.getImageUrl());
                intent.putExtra("movieUrl", movie.getMovieUrl());
                intent.putExtra("movieDescription", movie.getDescription());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void updateMovies(List<Movie> newMovies) {
        this.movies = newMovies;
        notifyDataSetChanged();
    }

    // ViewHolder class for movie items
    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        public ImageView moviePoster;
        public TextView movieTitle;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            moviePoster = itemView.findViewById(R.id.movie_poster);
            movieTitle = itemView.findViewById(R.id.movie_title);
        }
    }
}
