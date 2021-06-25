package com.ryubal.materialvalidation;

import com.google.android.material.textfield.TextInputLayout;
import com.ryubal.materialvalidation.custom.CustomManualValidation;
import com.ryubal.materialvalidation.custom.CustomValidation;
import com.ryubal.materialvalidation.exceptions.MissingEditTextException;
import com.ryubal.materialvalidation.validations.Range;
import com.ryubal.materialvalidation.validations.Simple;

import java.util.regex.Pattern;

/*
 * Holder of an individual validation. Each object will hold important information about it:
 * the text input layout, pattern, error messag, etc.
 */
public class SingleValidation {
    private TextInputLayout textInputLayout;
    private String errorMsg;
    private Pattern pattern;                        // If applicable
    private Range range;                            // If applicable
    private Simple simpleValidation;                // If applicable
    private int simpleValidationArg;                // If applicable
    private CustomValidation customValidation;      // If applicable
    private CustomManualValidation customManualValidation; // If applicable
    public enum ValidationType {
        PATTERN, RANGE, SIMPLE, CUSTOM, CUSTOM_MANUAL
    };
    private ValidationType validationType;

    public SingleValidation(CustomManualValidation customManualValidation) {
        this.customManualValidation = customManualValidation;
        this.validationType = ValidationType.CUSTOM_MANUAL;
    }

    public SingleValidation(TextInputLayout textInputLayout, Pattern pattern, String errorMsg) {
        this.textInputLayout = textInputLayout;
        this.pattern = pattern;
        this.errorMsg = errorMsg;
        this.validationType = ValidationType.PATTERN;
    }

    public SingleValidation(TextInputLayout textInputLayout, Range range, String errorMsg) {
        this.textInputLayout = textInputLayout;
        this.range = range;
        this.errorMsg = errorMsg;
        this.validationType = ValidationType.RANGE;
    }

    public SingleValidation(TextInputLayout textInputLayout, Simple simpleValidation, int simpleValidationArg, String errorMsg) {
        this.textInputLayout = textInputLayout;
        this.simpleValidation = simpleValidation;
        this.simpleValidationArg = simpleValidationArg;
        this.errorMsg = errorMsg;
        this.validationType = ValidationType.SIMPLE;
    }

    public SingleValidation(TextInputLayout textInputLayout, CustomValidation customValidation, String errorMsg) {
        this.textInputLayout = textInputLayout;
        this.customValidation = customValidation;
        this.errorMsg = errorMsg;
        this.validationType = ValidationType.CUSTOM;
    }

    public String getText() {
        if(textInputLayout == null)
            return null;

        if(textInputLayout.getEditText() == null)
            throw new MissingEditTextException("TextInputEditText must be inside TextInputLayout");

        return textInputLayout.getEditText().getText().toString();
    }

    public ValidationType getValidationType() {
        return validationType;
    }

    public TextInputLayout getTextInputLayout() {
        return textInputLayout;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public Range getRange() {
        return range;
    }

    public Simple getSimpleValidation() {
        return simpleValidation;
    }

    public int getSimpleValidationArg() {
        return simpleValidationArg;
    }

    public CustomValidation getCustomValidation() {
        return customValidation;
    }

    public CustomManualValidation getCustomManualValidation() {
        return customManualValidation;
    }
}