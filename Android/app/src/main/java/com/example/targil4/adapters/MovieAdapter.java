package com.example.targil4.adapters;// MovieAdapter.java
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.targil4.MovieInfoPage;
import com.example.targil4.R;
import com.example.targil4.R;
import com.example.targil4.entity.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> movieList;
    private Context context;
    private boolean useContainer;

    // default constructor
    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movieList = movies;
        this.useContainer = false;
    }

    // constructor with useContainer parameter
    public MovieAdapter(Context context, List<Movie> movies, boolean useContainer) {
        this.context = context;
        this.movieList = movies;
        this.useContainer = useContainer;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        if (useContainer) {
            itemView = LayoutInflater.from(context).inflate(
                    R.layout.item_movie_container,
                    parent, false);
        } else {
            itemView = LayoutInflater.from(context).inflate(
                    R.layout.item_movie,
                    parent, false);
        }
        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        // set movie details
        Glide.with(context)
                .load(movie.getImageUrl())
                .placeholder(R.drawable.movie_card_placeholder)
                .error(R.drawable.movie_card_placeholder)
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
        return movieList.size();
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
