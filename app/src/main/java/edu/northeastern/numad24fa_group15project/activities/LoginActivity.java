package edu.northeastern.numad24fa_group15project.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import edu.northeastern.numad24fa_group15project.R;
import edu.northeastern.numad24fa_group15project.controllers.UserManager;
import edu.northeastern.numad24fa_group15project.models.User;

public class LoginActivity extends AppCompatActivity {

    private UserManager userManager;
    private TextInputLayout loginEmail, loginPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userManager = UserManager.getInstance();

        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);
        Button loginConfirmButton = findViewById(R.id.loginConfirmButton);

        loginConfirmButton.setOnClickListener(v -> attemptLogin());
    }

    private void attemptLogin() {
        String email = Objects.requireNonNull(loginEmail.getEditText()).getText().toString().trim();
        String password = Objects.requireNonNull(loginPassword.getEditText()).getText().toString().trim();

        View rootLayout = findViewById(R.id.loginEmail);
        if (email.isEmpty() || password.isEmpty()) {
            Snackbar.make(rootLayout,"Please enter a valid email and password!", Snackbar.LENGTH_SHORT)
                    .show();
            return;
        }

        userManager.loginUser(email, password, new UserManager.OnLoginListener() {
            @Override
            public void onSuccess(User user) {
                Toast.makeText(LoginActivity.this, "Login successful.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, BaseActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(LoginActivity.this, "Login failed: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}