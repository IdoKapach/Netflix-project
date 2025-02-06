package com.example.targil4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.targil4.entity.Category;
import com.example.targil4.viewModels.CategoryViewModel;
import com.example.targil4.viewModels.CategoryViewModelFactory;
import com.example.targil4.viewModels.UserViewModel;

import java.util.ArrayList;
import java.util.List;

public class AdminEditCategoryFragment extends Fragment {

    private List<String> categories;
    private AutoCompleteTextView autoCompleteCategory;
    private CategoryViewModelFactory factory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_edit_category, container, false);

        UserViewModel userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        factory = new CategoryViewModelFactory(userViewModel);
        CategoryViewModel categoryViewModel = new ViewModelProvider(this, factory).get(CategoryViewModel.class);

        EditText editTextMovieTitle = view.findViewById(R.id.editTextMovieTitle);
        Switch promoted = view.findViewById(R.id.switch1);

        autoCompleteCategory = view.findViewById(R.id.autoCompleteCategory);

        categories = new ArrayList<>();
        categoryViewModel.getCategories().observe(getViewLifecycleOwner(), categoryList -> {

            if (categoryList == null) return;
            categories.clear();
            for (Category category : categoryList) {
                categories.add(category.getName());
            }

            // Create an ArrayAdapter with the categories
            ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, categories);

            // Set the adapter to the AutoCompleteTextView
            autoCompleteCategory.setAdapter(adapter);

            autoCompleteCategory.setOnClickListener(v -> autoCompleteCategory.showDropDown());

        });




        Button buttonAddCategory = view.findViewById(R.id.buttonAddCategory);
        buttonAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextMovieTitle.getText().toString();
                boolean promote = promoted.isChecked();
                if (name.isEmpty()) {
                    editTextMovieTitle.setError("Title is required");
                    return;
                }

                String oldCategoryName = autoCompleteCategory.getText().toString();
                if (oldCategoryName.isEmpty()) {
                    autoCompleteCategory.setError("Category is required");
                    return;
                }
                Category newCategory = categoryViewModel.getCategoryByName(oldCategoryName);
                newCategory.setName(name);
                newCategory.setPromoted(promote);
                categoryViewModel.editCategory(newCategory);
                editTextMovieTitle.setText("");
                autoCompleteCategory.setText("");
                promoted.setChecked(false);
            }
        });

        return view;
    }
}
