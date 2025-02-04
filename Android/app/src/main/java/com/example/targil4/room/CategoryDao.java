package com.example.targil4.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.targil4.entity.Category;

import java.util.List;

@Dao
public interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCategory(Category category);

    @Query("SELECT * FROM Category")
    List<Category> getCategories();

    @Query("DELETE FROM Category")
    void clearCategoryData();
}
