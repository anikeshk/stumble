package edu.northeastern.numad24fa_group15project.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import edu.northeastern.numad24fa_group15project.R;
import edu.northeastern.numad24fa_group15project.controllers.ChipGroupManager;
import edu.northeastern.numad24fa_group15project.controllers.UserManager;

public class RegisterSecondActivity extends AppCompatActivity {


    private UserManager  userManager;
    private ChipGroupManager chipGroupManager;

    private TextInputLayout registerSchool;
    private ChipGroup chipGroupInterests, chipGroupDietRes;
    Button registerConfirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_second);

        userManager = UserManager.getInstance();

        registerSchool = findViewById(R.id.registerSchool);
        chipGroupInterests = findViewById(R.id.chipGroupInterests);
        chipGroupDietRes = findViewById(R.id.chipGroupDietRes);
        registerConfirmButton = findViewById(R.id.registerConfirmButton);

        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) registerSchool.getEditText();
        String[] schools = getResources().getStringArray(R.array.schools);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, schools);
        autoCompleteTextView.setAdapter(adapter);

        chipGroupManager = new ChipGroupManager(this);

        String[] interests = getResources().getStringArray(R.array.interests);
        String[] dietaryRestrictions = getResources().getStringArray(R.array.diets);

        chipGroupManager.createChips(chipGroupInterests, ChipGroupManager.ChipGroupType.INTERESTS, interests);
        chipGroupManager.createChips(chipGroupDietRes, ChipGroupManager.ChipGroupType.DIETARY_RESTRICTIONS, dietaryRestrictions);

        registerConfirmButton.setOnClickListener(v -> updateUserPreferences());
    }

    private void updateUserPreferences() {
        String school = Objects.requireNonNull(registerSchool.getEditText()).getText().toString().trim();
        Map<String, List<String>> allSelections = chipGroupManager.getAllSelections();


        if (school.isEmpty()) {
            Toast.makeText(this, "Please select a school!", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> additionalInfo = new HashMap<>();
        additionalInfo.put("school", school);
        additionalInfo.put("interests", allSelections.get("interests"));
        additionalInfo.put("dietaryRestrictions", allSelections.get("dietary_restrictions"));

        userManager.updateUserData(additionalInfo, new UserManager.OnUserUpdateListener() {
            @Override
            public void onSuccess() {
                View rootLayout = findViewById(R.id.registerSchool);
                Snackbar.make(rootLayout,"Preferences saved.", Snackbar.LENGTH_SHORT)
                        .show();
                Intent intent = new Intent(RegisterSecondActivity.this, BaseActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(RegisterSecondActivity.this, "Failed to save user info: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}