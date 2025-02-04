package com.example.targil4.api;

import androidx.lifecycle.MutableLiveData;

import com.example.targil4.MyApplication;
import com.example.targil4.R;
import com.example.targil4.entity.Category;
import com.example.targil4.room.CategoryDao;
import com.example.targil4.room.UserDao;
import com.example.targil4.viewModels.UserViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CategoryAPI {
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;
    private MutableLiveData<List<Category>> categoriesLiveData;
    private CategoryDao dao;
    private UserViewModel userViewModel;
    private String token;

    public CategoryAPI(MutableLiveData<List<Category>> categoriesLiveData, CategoryDao dao, UserViewModel userViewModel) {
        retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.BaseURL).concat("api/"))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
        this.categoriesLiveData = categoriesLiveData;
        this.dao = dao;
        this.userViewModel = userViewModel;
        this.token = "Bearer " + userViewModel.getToken();
    }

    public void getCategories() {
        Call<List<Category>> call = webServiceAPI.getCategories(token);
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                android.util.Log.d("Categories", "getting Categories success! " + response.code());
                if (response.body() != null) {
                    android.util.Log.d("Categories", "not null");
                    List<Category> categories = response.body();
                    android.util.Log.d("Categories", "now in variable!");
                    categoriesLiveData.postValue(categories);
                }
                else {
                    android.util.Log.d("Categories", "Empty!");
                }
                android.util.Log.d("Categories", "posting Categories success!");
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                android.util.Log.d("Category", "getting Categories falied! " + t.getMessage());
            }
        });
    }

    public void addCategory(Category category) {
        Call<Category> call = webServiceAPI.addCategory(token, category);
        call.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                android.util.Log.d("Categories", "adding Category success! " + response.code());
                getCategories();
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {

            }
        });
    }

    public void editCategory(String id, Category category) {
        Call<Category> call = webServiceAPI.editCategory(token, id, category);
        call.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                android.util.Log.d("Categories", "edit Category success! " + response.code());
                getCategories();
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                android.util.Log.d("Categories", "edit Category failed! " + t.getMessage());
            }
        });
    }

    public void deleteCategory(String id) {
        Call<Category> call = webServiceAPI.deleteCategory(token, id);
        call.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                android.util.Log.d("Categories", "edit Category success! " + response.code());
                getCategories();
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                android.util.Log.d("Categories", "edit Category failed! " + t.getMessage());
            }
        });
    }
}
