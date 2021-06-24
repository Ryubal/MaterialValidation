package com.ryubal.materialvalidation;

import com.google.android.material.textfield.TextInputLayout;
import com.ryubal.materialvalidation.custom.CustomValidation;
import com.ryubal.materialvalidation.validations.Range;
import com.ryubal.materialvalidation.validations.Simple;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MaterialValidation {

    // Holder of all of the validations that were added
    private List<SingleValidation> validationsList;

    // Holder of invalid text input layouts
    private List<TextInputLayout> invalidTextInputLayouts;

    // Constructor
    public MaterialValidation() {
        this.validationsList = new ArrayList<>();
        this.invalidTextInputLayouts = new ArrayList<>();
    }

    // Add a new rule with Regex as String
    public void add(TextInputLayout textInputLayout, String regex, String errorMsg) {
        Pattern pattern = Pattern.compile(regex);
        add(textInputLayout, pattern, errorMsg);
    }

    // Add a new rule with Regex as Pattern
    public void add(TextInputLayout textInputLayout, Pattern pattern, String errorMsg) {
        SingleValidation singleValidation = new SingleValidation(textInputLayout, pattern, errorMsg);
        validationsList.add(singleValidation);
    }

    // Add a new rule with Range -- It will validate the text is a number between "from" and "to" in Range
    public void add(TextInputLayout textInputLayout, Range range, String errorMsg) {
        SingleValidation singleValidation = new SingleValidation(textInputLayout, range, errorMsg);
        validationsList.add(singleValidation);
    }

    // Add a new rule with Simple validation -- Is is a pre-defined validation with an argument
    public void add(TextInputLayout textInputLayout, Simple simple, int arg, String errorMsg) {
        SingleValidation singleValidation = new SingleValidation(textInputLayout, simple, arg, errorMsg);
        validationsList.add(singleValidation);
    }

    // Add a new rule with a Custom validation
    public void add(TextInputLayout textInputLayout, CustomValidation customValidation, String errorMsg) {
        SingleValidation singleValidation = new SingleValidation(textInputLayout, customValidation, errorMsg);
        validationsList.add(singleValidation);
    }

    // Run validation
    public boolean validate() {
        boolean isValid = true;

        for(SingleValidation validation: validationsList) {
            // Run validation only is this field is not invalid yet
            // This is because a field can be added more than once. In that case, we'll prevent further
            // validations of the same field when it is invalid
            if(!invalidTextInputLayouts.contains(validation.getTextInputLayout())) {
                boolean isCurrentValid;

                // PATTERN, RANGE, SIMPLE, CUSTOM
                if(validation.getValidationType() == SingleValidation.ValidationType.PATTERN)
                    isCurrentValid = validateRegex(validation);
                else if(validation.getValidationType() == SingleValidation.ValidationType.RANGE)
                    isCurrentValid = validateRange(validation);
                else if(validation.getValidationType() == SingleValidation.ValidationType.SIMPLE)
                    isCurrentValid = validateSimple(validation);
                else if(validation.getValidationType() == SingleValidation.ValidationType.CUSTOM)
                    isCurrentValid = validateCustom(validation);
                else
                    isCurrentValid = true;

                isValid = isCurrentValid && isValid;

                // If this input was invalid, add it to the list of invalid inputs
                if(!isCurrentValid)
                    invalidTextInputLayouts.add(validation.getTextInputLayout());
            }
        }

        return isValid;
    }

    public List<TextInputLayout> getInvalidFields() {
        return this.invalidTextInputLayouts;
    }

    private boolean validateRegex(SingleValidation validation) {
        Matcher matcher = validation.getPattern().matcher(validation.getText());
        if(!matcher.matches()) {
            validation.getTextInputLayout().setError(validation.getErrorMsg());
            return false;
        }

        validation.getTextInputLayout().setError(null);
        validation.getTextInputLayout().setErrorEnabled(false);
        return true;
    }

    private boolean validateRange(SingleValidation validation) {
        String errorMsg = null;

        try {
            int intVal = Integer.parseInt(validation.getText());
            boolean result = intVal >= validation.getRange().from && intVal <= validation.getRange().to;

            if(!result)
                errorMsg = validation.getErrorMsg();

        }catch(NumberFormatException e) {
            errorMsg = validation.getErrorMsg();
        }

        validation.getTextInputLayout().setError(errorMsg);

        if(errorMsg == null)
            validation.getTextInputLayout().setErrorEnabled(false);

        return errorMsg == null;
    }

    private boolean validateSimple(SingleValidation validation) {
        String errorMsg = null;

        if(validation.getSimpleValidation() == Simple.MIN_LENGTH) {
            boolean result = validation.getText().length() >= validation.getSimpleValidationArg();

            if(!result)
                errorMsg = validation.getErrorMsg();

        }else if(validation.getSimpleValidation() == Simple.MAX_LENGTH) {
            boolean result = validation.getText().length() <= validation.getSimpleValidationArg();

            if(!result)
                errorMsg = validation.getErrorMsg();
        }

        validation.getTextInputLayout().setError(errorMsg);

        if(errorMsg == null)
            validation.getTextInputLayout().setErrorEnabled(false);

        return errorMsg == null;
    }

    private boolean validateCustom(SingleValidation validation) {
        boolean result = validation.getCustomValidation().validate(validation.getText());

        if(result) {
            validation.getTextInputLayout().setError(null);
            validation.getTextInputLayout().setErrorEnabled(false);
        }else
            validation.getTextInputLayout().setError(validation.getErrorMsg());

        return result;
    }
}