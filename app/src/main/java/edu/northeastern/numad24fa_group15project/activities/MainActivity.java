package edu.northeastern.numad24fa_group15project.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import edu.northeastern.numad24fa_group15project.R;
import edu.northeastern.numad24fa_group15project.controllers.UserManager;


public class MainActivity extends AppCompatActivity {

    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userManager = UserManager.getInstance();

        if (userManager.isUserLoggedIn()) {
            Intent intent = new Intent(MainActivity.this, BaseActivity.class);
            startActivity(intent);
            finish();
        }

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        Button registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegisterFirstActivity.class);
            startActivity(intent);
        });
    }
}