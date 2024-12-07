package edu.northeastern.numad24fa_group15project.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import edu.northeastern.numad24fa_group15project.R;
import edu.northeastern.numad24fa_group15project.controllers.UserManager;


public class MainActivity extends AppCompatActivity {

    private UserManager userManager;
    private TextView stumbleTextView, somethingTextView, newTextView;
    private Animation animationLtoR, animationRtoL;

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

        stumbleTextView = findViewById(R.id.stumbleTextView);
        somethingTextView = findViewById(R.id.somethingTextView);
        newTextView = findViewById(R.id.newTextView);

        animationLtoR = AnimationUtils.loadAnimation(this, R.anim.slide_left_to_right);
        animationRtoL = AnimationUtils.loadAnimation(this, R.anim.slide_right_to_left);

        animationLtoR.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                stumbleTextView.startAnimation(animation);
                newTextView.startAnimation(animation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        animationRtoL.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                somethingTextView.startAnimation(animation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        stumbleTextView.startAnimation(animationLtoR);
        somethingTextView.startAnimation(animationRtoL);
        newTextView.startAnimation(animationLtoR);
    }
}