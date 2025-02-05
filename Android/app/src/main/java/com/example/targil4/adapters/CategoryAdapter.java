package com.example.targil4.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.targil4.R;
import com.example.targil4.entity.Category;
import com.example.targil4.entity.Movie;
import com.example.targil4.viewModels.MovieViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private Context context;
    private List<Category> categories;
    private MovieViewModel movieViewModel;
    private List<Movie> allMovies = new ArrayList<>();
    private String userToken;

    public CategoryAdapter(Context context, MovieViewModel movieViewModel, String userToken) {
        this.context = context;
        this.movieViewModel = movieViewModel;
        this.categories = Collections.emptyList();
        this.userToken = userToken;
    }

    @NonNull
    @Override
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // set the horizontal recycler view for movies
        View view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategoryViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.categoryName.setText(category.getName());

        // get the movie ids
        List<String> movieIds = category.getMovies();

        // get the movies
        List<Movie> movies = new ArrayList<>();
        for (String movieId : movieIds) {
            Movie movie = getMovieFromId(movieId);
            if (movie != null) {
                movies.add(movie);
            }
        }

        if (holder.movieAdapter == null) {
            holder.movieAdapter = new MovieAdapter(context, movies, userToken);
            holder.moviesRecyclerView.setAdapter(holder.movieAdapter);
            holder.moviesRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        } else {
            // If the adapter already exists, update its data
            holder.movieAdapter.updateMovies(movies);
        }

//        // set the movies recycler view
//        MovieAdapter movieAdapter = new MovieAdapter(context, movies);
//        holder.moviesRecyclerView.setAdapter(movieAdapter);
//        holder.moviesRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void updateCategories(List<Category> newCategories) {
        this.categories = newCategories;
        notifyDataSetChanged(); // can be made more efficient by using specific change notifications
    }

    public void updateMovies(List<Movie> newMovies) {
        this.allMovies = newMovies;
        notifyDataSetChanged(); // can be made more efficient by using specific change notifications
    }

    private Movie getMovieFromId(String movieId) {
        for (Movie movie : allMovies) {
            if (movie.get_id().equals(movieId)) {
                return movie;
            }
        }
        return null;
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        RecyclerView moviesRecyclerView;
        MovieAdapter movieAdapter;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.category_name);
            moviesRecyclerView = itemView.findViewById(R.id.movies_recycler_view);
        }

        public void updateMovies(List<Movie> movies) {
            if (movieAdapter != null) {
                movieAdapter.updateMovies(movies);
            }
        }
    }
}
