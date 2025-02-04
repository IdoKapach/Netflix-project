package com.example.targil4.viewModels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.targil4.entity.User;

public class CategoryViewModelFactory implements ViewModelProvider.Factory {
    private final UserViewModel userViewModel;

    public CategoryViewModelFactory(UserViewModel userViewModel) {
        this.userViewModel = userViewModel;

    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CategoryViewModel.class)) {
            return (T) new CategoryViewModel(userViewModel);
        }
        else if (modelClass.isAssignableFrom(QueryViewModel.class)) {
            return (T) new QueryViewModel(userViewModel);
        }
        else if (modelClass.isAssignableFrom(MovieViewModel.class)) {
            return (T) new MovieViewModel(userViewModel);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
