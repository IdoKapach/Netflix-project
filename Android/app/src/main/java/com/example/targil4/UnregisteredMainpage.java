package com.example.targil4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.targil4.viewModels.UserViewModel;

public class UnregisteredMainpage extends AppCompatActivity {

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        if (userViewModel.hasToken()) {
            Intent intent = new Intent(this, RegisteredMainpage.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        setContentView(R.layout.activity_unregistered_mainpage);
        TextView signin = findViewById(R.id.SignIn);
        signin.setOnClickListener(view -> {
            Intent intent = new Intent(this, LoginPage.class);
            startActivity(intent);
        });

        Button signup = findViewById(R.id.SignUp);
        signup.setOnClickListener(view -> {
            Intent intent = new Intent(this, SignupPage.class);
            startActivity(intent);
        });
    }
}