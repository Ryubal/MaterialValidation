package com.ryubal.materialvalidation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;

import com.ryubal.materialvalidation.databinding.ActivitySignupValidationBinding;
import com.ryubal.materialvalidation.validations.Range;
import com.ryubal.materialvalidation.validations.Regex;
import com.ryubal.materialvalidation.validations.Simple;

public class SignupValidationActivity extends AppCompatActivity {

    private ActivitySignupValidationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Configure view binding
        binding = ActivitySignupValidationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set title and show "go back" button
        getSupportActionBar().setTitle("Signup validation");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set submit listener
        binding.buttonSubmit.setOnClickListener(view -> onSubmit());
    }

    // When clicking on submit
    private void onSubmit() {
        // Initialize the validator and add rules
        MaterialValidation validator = new MaterialValidation();
        validator.add(binding.textInputName, Regex.LETTERS, "Please enter your name");
        validator.add(binding.textInputUsername, Regex.NON_EMPTY, "Please enter your username");
        validator.add(binding.textInputUsername, Simple.MIN_LENGTH, 4, "Username must be at least 4 characters long");
        validator.add(binding.textInputEmailAddress, Patterns.EMAIL_ADDRESS, "Please enter your email address");
        validator.add(binding.textInputPassword, Regex.NON_EMPTY, "Please enter a password");
        validator.add(binding.textInputPassword, "^.{5,10}$", "Password must be between 5 and 10 characters long");
        validator.add(binding.textInputPasswordConfirmation, Regex.NON_EMPTY, "Please enter password confirmation");

        // Add custom validation - In the callback we'll run our validation, and we'll return
        // the result of it.
        // MaterialValidation will take care of showing/hiding the error
        validator.add(binding.textInputPasswordConfirmation, input -> {
            String password = binding.textInputPassword.getEditText().getText().toString();
            return password.equals(input);
        }, "Password and password confirmation must match");

        // If all fields are valid, show a success alert
        if(validator.validate())
            Utils.showAlert(this, "Ok!", "All fields are valid");
    }

    // When clicking the "go back" button
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();

        return super.onOptionsItemSelected(item);
    }
}