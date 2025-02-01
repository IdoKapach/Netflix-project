package com.example.targil4.adapters;// MovieAdapter.java
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.targil4.R;
import com.example.targil4.R;
import com.example.targil4.entity.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> movieList;
    private Context context;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movieList = movies;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate your item layout. Ensure item_movie.xml exists in res/layout
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        // Use Glide (or any image loading library) to load the image into the ImageView.
        Glide.with(context)
                .load(movie.getImageUrl())
                .placeholder(R.drawable.movie_card_placeholder)
                .error(R.drawable.movie_card_placeholder) // Placeholder in case of error
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    // ViewHolder class for movie items
    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            // Make sure your item_movie.xml has an ImageView with id "movie_image"
            imageView = itemView.findViewById(R.id.movie_image);
        }
    }
}
