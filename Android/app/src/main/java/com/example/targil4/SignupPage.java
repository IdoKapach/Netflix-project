package com.example.targil4;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.targil4.entity.User;
import com.example.targil4.viewModels.UserViewModel;

public class SignupPage extends AppCompatActivity {
    private UserViewModel viewModel;
    private LiveData<Boolean> signedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);

        /*if (viewModel.hasToken()) {
            Intent intent = new Intent(this, RegisteredMainpage.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }*/

        viewModel.signOut();

        setContentView(R.layout.activity_signup_page);

        TextView signin = findViewById(R.id.SignIn);
        signin.setOnClickListener(view -> {
            Intent intent = new Intent(this, LoginPage.class);
            startActivity(intent);
        });

        EditText email = findViewById(R.id.Email);
        email.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Clear the error message as soon as the user starts typing
                TextView errorMassage = findViewById(R.id.errorMassage);
                errorMassage.setText("");
                errorMassage.setTextSize(0);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        Button btn = findViewById(R.id.SignupButton);
        btn.setOnClickListener(view -> {
            EditText password = findViewById(R.id.Password);
            if (email.getText().toString().isEmpty()) {
                email.setError("Email is required!");
                if (password.getText().toString().isEmpty()) {
                    password.setError("Password is required!");
                }
            } else if (password.getText().toString().isEmpty()) {
                password.setError("Password is required!");
            } else {

                User user = new User(email.getText().toString(), password.getText().toString());
                signedIn = viewModel.signup(user);
                signedIn.observe(this, loggedIn -> {
                    android.util.Log.d("createUser", "ObservedSomething ");
                    if (loggedIn) {
                        Intent intent = new Intent(this, RegisteredMainpage.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        signedIn.removeObservers(this);
                    } else {
                        TextView errorMassage = findViewById(R.id.errorMassage);
                        errorMassage.setText("Username Taken Kapach!");
                        errorMassage.setTextSize(10);
                    }
                });
            }
        });
    }
}