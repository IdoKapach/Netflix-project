package com.example.targil4;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.targil4.entity.User;
import com.example.targil4.viewModels.UserViewModel;

public class SignupPage extends AppCompatActivity {
    private UserViewModel viewModel;
    private boolean firstTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        setContentView(R.layout.activity_signup_page);

        // make sure no user is both signed in and in this page
        viewModel.signOut();

        // logic for when sign in button is clicked
        TextView signIn = findViewById(R.id.SignIn);
        signIn.setOnClickListener(view -> {

            // go to the log in page
            Intent intent = new Intent(this, LoginPage.class);
            startActivity(intent);
        });

        EditText username = findViewById(R.id.Username);

        // logic for when the username editText is changed
        username.addTextChangedListener(new TextWatcher() {

            // when text changes Clear the error message
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                TextView errorMassage = findViewById(R.id.errorMassage);
                errorMassage.setText("");
                errorMassage.setTextSize(0);
            }

            // ignore implementation for other cases.
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}
        });

        // logic for when signup button is clicked
        Button btn = findViewById(R.id.SignupButton);
        btn.setOnClickListener(view -> {
            EditText password = findViewById(R.id.Password);

            // if username or password is empty display error to the editText
            if (username.getText().toString().isEmpty()) {
                username.setError(getString(R.string.noUsername));
                if (password.getText().toString().isEmpty()) {
                    password.setError(getString(R.string.noPassword));
                }
            } else if (password.getText().toString().isEmpty()) {
                password.setError(getString(R.string.noPassword));
            }

            // call viewmodel to create the user
            else {
                User user = new User(username.getText().toString(), password.getText().toString());
                viewModel.signup(user);

                // set boolean to true so observer ignore the first one
                firstTime = true;

                // observe if a user is logged on
                viewModel.hasToken().observe(this, loggedIn -> {
                    android.util.Log.d("createUser", "ObservedSomething ");

                    // if its the first time trying to observe return
                    if (firstTime) {
                        firstTime = false;
                        return;
                    }

                    // if user did log in go to the registered main page
                    if (loggedIn) {
                        Intent intent = new Intent(this, MainActivity.class);

                        // remove all previous pages from stack
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }

                    // else display an error massage
                    else {
                        TextView errorMassage = findViewById(R.id.errorMassage);
                        errorMassage.setText(getString(R.string.signupFail));
                        errorMassage.setTextSize(15);
                    }
                });
            }
        });
    }
}