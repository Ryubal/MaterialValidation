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

        binding = ActivityBasicValidationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setTitle("Basic validation");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.buttonSubmit.setOnClickListener(view -> onSubmit());
    }

    private void onSubmit() {
        MaterialValidation validator = new MaterialValidation();
        validator.add(binding.textInputUsername, Regex.NON_EMPTY, "Please enter your username");
        validator.add(binding.textInputPassword, Regex.NON_EMPTY, "Please enter your password");

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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();

        return super.onOptionsItemSelected(item);
    }
}