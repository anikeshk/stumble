package edu.northeastern.numad24fa_group15project.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import edu.northeastern.numad24fa_group15project.R;
import edu.northeastern.numad24fa_group15project.controllers.UserManager;
import edu.northeastern.numad24fa_group15project.models.User;

public class RegisterFirstActivity extends AppCompatActivity {

    private UserManager userManager;
    private TextInputLayout registerFirstName, registerLastName, registerPhoneNumber, registerEmail, registerPassword, registerPasswordConfirm;
    private Button registerNextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_first);

        userManager = UserManager.getInstance();

        registerEmail = findViewById(R.id.registerEmail);
        registerFirstName = findViewById(R.id.registerFirstName);
        registerLastName = findViewById(R.id.registerLastName);
        registerPhoneNumber = findViewById(R.id.registerPhoneNumber);
        registerPassword = findViewById(R.id.registerPassword);
        registerPasswordConfirm = findViewById(R.id.registerPasswordConfirm);
        registerNextButton = findViewById(R.id.registerNextButton);

        registerNextButton.setOnClickListener(v -> attemptRegistration());
    }

    private void attemptRegistration() {
        String email = Objects.requireNonNull(registerEmail.getEditText()).getText().toString().trim();
        String firstName = Objects.requireNonNull(registerFirstName.getEditText()).getText().toString().trim();
        String lastName = Objects.requireNonNull(registerLastName.getEditText()).getText().toString().trim();
        String password = Objects.requireNonNull(registerPassword.getEditText()).getText().toString().trim();
        String passwordConfirm = Objects.requireNonNull(registerPasswordConfirm.getEditText()).getText().toString().trim();
        String phoneNumber = Objects.requireNonNull(registerPhoneNumber.getEditText()).getText().toString().trim();

        if (email.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || password.isEmpty()
                || passwordConfirm.isEmpty() || phoneNumber.isEmpty()) {
            Toast.makeText(this, "Please enter a valid email and password!", Toast.LENGTH_SHORT).show();
            return;
        }

        userManager.registerUser(email, password, new UserManager.OnRegistrationListener() {
            @Override
            public void onSuccess(User user) {
                Map<String, Object> additionalInfo = new HashMap<>();
                additionalInfo.put("firstName", firstName);
                additionalInfo.put("lastName", lastName);
                additionalInfo.put("phone", phoneNumber);

                userManager.updateUserData(additionalInfo, new UserManager.OnUserUpdateListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(RegisterFirstActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterFirstActivity.this, RegisterSecondActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(String error) {
                        Log.v("FIRESTORE", error);
                        Toast.makeText(RegisterFirstActivity.this, "Failed to save user info: " + error, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(RegisterFirstActivity.this, "Registration failed: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }


}