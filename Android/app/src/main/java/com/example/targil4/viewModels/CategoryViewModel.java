package com.example.targil4.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.targil4.entity.Category;
import com.example.targil4.repositories.CategoryRepository;

import java.util.List;

public class CategoryViewModel extends ViewModel {
    private LiveData<List<Category>> categories;
    private CategoryRepository categoryRepo;

    CategoryViewModel(UserViewModel userViewModel) {
        categoryRepo = new CategoryRepository(userViewModel);
        categories = categoryRepo.getCategories();
    }

    public Category getCategory(String id) {
        if (categories == null || categories.getValue() == null) {
            return null; // Prevents crash if categories is not yet loaded
        }
        List<Category> categoryList = categories.getValue();
        if (categoryList != null) {
            for (Category category : categoryList) {
                if (category.get_id().equals(id)) {
                    return category;
                }
            }
        }
        return null;
    }

    public LiveData<List<Category>> getCategories() {
        return categories;
    }

    public void addCategory(Category category) {
        categoryRepo.addCategory(category);
    }

    public void editCategory(Category category) {
        categoryRepo.editCategory(category);
    }

    public void deleteCategory(Category category) {
        categoryRepo.deleteCategory(category.get_id());
    }
}

