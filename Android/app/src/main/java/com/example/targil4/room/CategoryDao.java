package com.example.targil4.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

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

    @Query("DELETE FROM Category WHERE _id = :id")
    void deleteCategory(String id);

    @Query("UPDATE Category SET name = :newName WHERE _id = :id")
    void updateCategoryName(String id, String newName);

    @Query("UPDATE Category SET promoted = :promoted WHERE _id = :id")
    void updateCategoryPromoted(String id, boolean promoted);





}
