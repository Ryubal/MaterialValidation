package com.ryubal.materialvalidation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.textfield.TextInputLayout;
import com.ryubal.materialvalidation.databinding.ActivityBasicValidationBinding;
import com.ryubal.materialvalidation.validations.Regex;

import java.util.List;

public class BasicValidationActivity extends AppCompatActivity {

    private ActivityBasicValidationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Configure view binding
        binding = ActivityBasicValidationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set title and show "go back" button
        getSupportActionBar().setTitle("Basic validation");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set submit listener
        binding.buttonSubmit.setOnClickListener(view -> onSubmit());
    }

    // When clicking on submit
    private void onSubmit() {
        // Initialize the validator and add two rules
        MaterialValidation validator = new MaterialValidation();
        validator.add(binding.textInputUsername, Regex.NON_EMPTY, "Please enter your username");
        validator.add(binding.textInputPassword, Regex.NON_EMPTY, "Please enter your password");

        // If the fields are valid, show a success alert
        // If one or more fields are invalid, show which one
        if(validator.validate())
            Utils.showAlert(this, "Ok!", "All fields are valid");
        else {
            List<TextInputLayout> invalidFields = validator.getInvalidFields();

            if(invalidFields.size() == 2)
                Utils.showAlert(this, "Oops", "Both fields are invalid!");
            else if(invalidFields.get(0) == binding.textInputUsername)
                Utils.showAlert(this, "Oops", "Username is invalid!");
            else if(invalidFields.get(0) == binding.textInputPassword)
                Utils.showAlert(this, "Oops", "Password is invalid!");
        }
    }

    // When clicking the "go back" button
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}