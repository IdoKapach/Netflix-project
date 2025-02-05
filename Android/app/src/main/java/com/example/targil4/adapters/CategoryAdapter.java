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

    public CategoryAdapter(Context context, MovieViewModel movieViewModel) {
        this.context = context;
        this.movieViewModel = movieViewModel;
        this.categories = Collections.emptyList();
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

        // set the movies recycler view
        MovieAdapter movieAdapter = new MovieAdapter(context, movieViewModel);
        holder.moviesRecyclerView.setAdapter(movieAdapter);
        holder.moviesRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void updateCategories(List<Category> newCategories) {
        this.categories = newCategories;
        notifyDataSetChanged(); // can be made more efficient by using specific change notifications
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        RecyclerView moviesRecyclerView;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.category_name);
            moviesRecyclerView = itemView.findViewById(R.id.movies_recycler_view);
        }
    }
}
