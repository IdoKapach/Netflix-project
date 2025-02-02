package com.example.targil4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.targil4.viewModels.UserViewModel;

public class UnregisteredMainpage extends AppCompatActivity {

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        LiveData<Boolean> hasToken = userViewModel.hasToken();
        hasToken.observe(this, loggedIn -> {
            android.util.Log.d("createUser", "ObservedSomething ");
            if (loggedIn) {
                Intent intent = new Intent(this, RegisteredMainpage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
            hasToken.removeObservers(this);
        });

        setContentView(R.layout.activity_unregistered_mainpage);
        TextView signIn = findViewById(R.id.SignIn);
        signIn.setOnClickListener(view -> {
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