package com.example.targil4;

import static android.content.ClipData.newIntent;

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
import com.example.targil4.api.UserAPI;
import com.example.targil4.entity.User;
import com.example.targil4.viewModels.UserViewModel;

public class LoginPage extends AppCompatActivity {

    private UserViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        Button btn = findViewById(R.id.LoginButton);
        btn.setOnClickListener(view -> {
            EditText password = findViewById(R.id.Password);
            EditText email = findViewById(R.id.Email);
            if (email.getText().toString().isEmpty()) {
                email.setError("Email is required!");
                if (password.getText().toString().isEmpty()) {
                    password.setError("Password is required!");
                }
            }
            else if (password.getText().toString().isEmpty()) {
                password.setError("Password is required!");
            } else {
                User user = new User(email.getText().toString(), password.getText().toString());
                if (user.isLoginSuccessful()) {
                    Intent intent = new Intent(this, RegisteredMainpage.class);
                    startActivity(intent);
                }
                else {
                    TextView errorMassage = findViewById(R.id.errorMassage);
                    errorMassage.setText("Username Taken Kapach!");
                    errorMassage.setTextSize(10);
                }
            }
        });
    }
}