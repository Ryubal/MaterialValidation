package com.ryubal.materialvalidation.examples;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.ryubal.materialvalidation.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Configure view binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        // Set button listeners
        binding.buttonBasicValidation.setOnClickListener(view -> openActivity(BasicValidationActivity.class));
        binding.buttonSignupValidation.setOnClickListener(view -> openActivity(SignupValidationActivity.class));
        binding.buttonFullValidation.setOnClickListener(view -> openActivity(FullValidationActivity.class));

        setContentView(binding.getRoot());
    }

    private void openActivity(Class<?> cls) {
        startActivity(new Intent(this, cls));
    }
}