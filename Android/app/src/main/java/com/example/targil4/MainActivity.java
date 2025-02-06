package com.example.targil4;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.targil4.viewModels.UserViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;
    private UserViewModel userViewModel;
    private MenuItem adminItem;
    private MaterialButton logoutButton;
    private MaterialButton darkModeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize ViewModel
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);


        // Set up Bottom Navigation
        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.home) {
                    // Load the Home fragment
                    loadFragment(new HomeFragment());
                    return true;
                } else if (itemId == R.id.search) {
                    // Load the Search fragment
                    loadFragment(new SearchFragment());
                    return true;
                } else if (itemId == R.id.admin) {
                    // Load the Admin fragment
                    loadFragment(new AdminFragment());
                    return true;
                }
                return false;
            }
        });

        // Load the default fragment (HomeFragment)
        loadFragment(new HomeFragment());

        // Check if the logged in user is an admin and show the admin menu item accordingly
        adminItem = bottomNav.getMenu().findItem(R.id.admin);
        if (userViewModel.isAdmin()) {
            adminItem.setVisible(true);
        } else {
            adminItem.setVisible(false);
        }


        if (userViewModel.isDarkMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        // add logout button
        logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(v -> {
            userViewModel.signOut();
            Intent intent = new Intent(MainActivity.this, UnregisteredMainpage.class);
            startActivity(intent);
        });

        darkModeButton = findViewById(R.id.DarkMode);
        darkModeButton.setOnClickListener(v -> {
            int nightMode = AppCompatDelegate.getDefaultNightMode();
            AppCompatDelegate.setDefaultNightMode(
                    nightMode == AppCompatDelegate.MODE_NIGHT_YES ?
                            AppCompatDelegate.MODE_NIGHT_NO :
                            AppCompatDelegate.MODE_NIGHT_YES
            );
            userViewModel.updateDarkMode();
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment); // Replace the fragment in the container
        transaction.commit();
    }
}