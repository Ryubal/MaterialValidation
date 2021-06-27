package com.ryubal.materialvalidation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.ryubal.materialvalidation.databinding.ActivityFullValidationBinding;
import com.ryubal.materialvalidation.validations.Regex;

import java.util.ArrayList;
import java.util.List;

public class FullValidationActivity extends AppCompatActivity {

    private ActivityFullValidationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Configure view binding
        binding = ActivityFullValidationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set title and show "go back" button
        getSupportActionBar().setTitle("Full validation");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set list of genders
        List<String> genders = new ArrayList<>();
        genders.add("Male");
        genders.add("Female");
        ArrayAdapter<List<String>> gendersAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, genders);
        ((AutoCompleteTextView) binding.textInputGender.getEditText()).setAdapter(gendersAdapter);

        // Set submit listener
        binding.buttonSubmit.setOnClickListener(view -> onSubmit());
    }

    // When clicking on submit
    private void onSubmit() {
        // Initialize the validator and add rules
        MaterialValidation validator = new MaterialValidation();
        validator.add(binding.textInputName, Regex.LETTERS, "Please enter your name");
        validator.add(binding.textInputGender, Regex.NON_EMPTY, "Please select a gender");

        // Add custom manual validations - We need to take care of validation and showing/hiding
        // the error.
        // Custom manual validations are meant to be used with elements that MaterialValidation
        // does not know how to use
        validator.add(() -> {
            boolean isSelected = binding.checkBox.isChecked();
            if (!isSelected)
                showErrorAlert("Please select the checkbox");
            return isSelected;
        });

        validator.add(() -> {
            boolean isSelected = binding.radioButton2.isChecked();
            if (!isSelected)
                showErrorAlert("Please select option 2");
            return isSelected;
        });

        validator.add(() -> {
            boolean isSelected = binding.elemSwitch.isChecked();
            if (!isSelected)
                showErrorAlert("Please select the switch");
            return isSelected;
        });

        // If all fields are valid, show a success alert
        if (validator.validate())
            Utils.showAlert(this, "Ok!", "All fields are valid");
    }

    private AlertDialog errorAlert;

    private void showErrorAlert(String msg) {
        if (errorAlert == null || !errorAlert.isShowing()) {
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
            builder.setTitle("Error");
            builder.setMessage(msg);
            builder.setPositiveButton(android.R.string.ok, null);
            errorAlert = builder.show();
        }
    }

    // When clicking the "go back" button
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();

        return super.onOptionsItemSelected(item);
    }
}