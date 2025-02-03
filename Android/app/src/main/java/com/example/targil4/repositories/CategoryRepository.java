package com.example.targil4.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.targil4.MyApplication;
import com.example.targil4.api.CategoryAPI;
import com.example.targil4.api.UserAPI;
import com.example.targil4.entity.Category;
import com.example.targil4.room.AppDB;
import com.example.targil4.room.CategoryDao;
import com.example.targil4.viewModels.UserViewModel;

import java.util.List;

public class CategoryRepository {
    private CategoryDao dao;
    private CategoriesLiveData categoriesLiveData;
    private CategoryAPI categoryAPI;

    public CategoryRepository(UserViewModel userViewModel) {
        AppDB db = Room.databaseBuilder(MyApplication.context,
                        AppDB.class, "FooDB")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries().build();
        this.dao = db.CategoryDao();
        categoriesLiveData = new CategoriesLiveData();
        this.categoryAPI = new CategoryAPI(categoriesLiveData, dao, userViewModel);
        categoryAPI.getCategories();
    }

    class CategoriesLiveData extends MutableLiveData<List<Category>> {
        public CategoriesLiveData() {
            super();
        }

        @Override
        protected void onActive() {
            android.util.Log.d("Categories", "on active activated");
            super.onActive();
        }
    }

    public LiveData<List<Category>> getCategories() {
        android.util.Log.d("Categories", "getting categories");
        return categoriesLiveData;
    }

    public void addCategory(Category category) {
        categoryAPI.addCategory(category);
    }
    public void editCategory(Category category) {
        categoryAPI.editCategory(category.get_id(), category);
    }
    public void deleteCategory(String id) {
        categoryAPI.deleteCategory(id);
    }
}
