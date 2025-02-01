package com.example.targil4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.targil4.viewModels.UserViewModel;

public class RegisteredMainpage extends AppCompatActivity {
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_mainpage);

        TextView signout = findViewById(R.id.SignOut);
        signout.setOnClickListener(view -> {

            userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
            userViewModel.signOut();
            Intent intent = new Intent(this, UnregisteredMainpage.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        TextView home = findViewById(R.id.Home);
        home.setOnClickListener(view -> {
            Intent intent = new Intent(this, RegisteredMainpage.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        });

        Switch darkMode = findViewById(R.id.DarkMode);
        darkMode.setOnClickListener(view -> {
            int nightMode = AppCompatDelegate.getDefaultNightMode();
            AppCompatDelegate.setDefaultNightMode(
                    nightMode == AppCompatDelegate.MODE_NIGHT_YES ?
                            AppCompatDelegate.MODE_NIGHT_NO :
                            AppCompatDelegate.MODE_NIGHT_YES
            );

            Intent intent = new Intent(this, RegisteredMainpage.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            overridePendingTransition(0, 0);
            startActivity(intent);
        });
    }
}