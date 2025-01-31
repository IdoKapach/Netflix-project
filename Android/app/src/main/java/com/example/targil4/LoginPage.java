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
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.targil4.api.UserAPI;
import com.example.targil4.entity.User;
import com.example.targil4.viewModels.UserViewModel;

public class LoginPage extends AppCompatActivity {

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
            } else if (password.getText().toString().isEmpty()) {
                password.setError("Password is required!");
            } else {

                User user = new User(email.getText().toString(), password.getText().toString());
                signedIn = viewModel.signin(user);
                signedIn.observe(this, loggedIn -> {
                    android.util.Log.d("createUser", "ObservedSomething ");
                    if (loggedIn) {
                        Intent intent = new Intent(this, RegisteredMainpage.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        TextView errorMassage = findViewById(R.id.errorMassage);
                        errorMassage.setText("Username and Password are not Kapach Enough!");
                        errorMassage.setTextSize(10);
                    }
                });


            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (signedIn != null) {
            signedIn.removeObservers(this);
        }
    }
}