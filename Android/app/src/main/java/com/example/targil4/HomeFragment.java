package com.example.targil4;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.targil4.adapters.CategoryAdapter;
import com.example.targil4.adapters.MovieAdapter;
import com.example.targil4.api.MovieAPI;
import com.example.targil4.api.WebServiceAPI;
import com.example.targil4.entity.Movie;
import com.example.targil4.viewModels.CategoryViewModelFactory;
import com.example.targil4.viewModels.MovieViewModel;
import com.example.targil4.viewModels.UserViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView categoriesRecyclerView;
    private CategoryAdapter categoryAdapter;
    private MovieViewModel movieViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        categoriesRecyclerView = view.findViewById(R.id.categories_recycler_view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // init the userViewModel (for the token for the movie view model)
        UserViewModel userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        // Initialize the MovieViewModel
        CategoryViewModelFactory factory = new CategoryViewModelFactory(userViewModel);
        movieViewModel = new ViewModelProvider(this, factory).get(MovieViewModel.class);

        // set the layout manager
        categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // set the adapter
        categoryAdapter = new CategoryAdapter(getContext(), movieViewModel);
        categoriesRecyclerView.setAdapter(categoryAdapter);

        // observe the categories live data
        movieViewModel.getPromotedCategories().observe(
                getViewLifecycleOwner(),
                categories -> {
                    Log.d("Categories", "got the categories!, size: " + categories.size());
                    if (categories != null) { categoryAdapter.updateCategories(categories); }
                }
        );
    }
}