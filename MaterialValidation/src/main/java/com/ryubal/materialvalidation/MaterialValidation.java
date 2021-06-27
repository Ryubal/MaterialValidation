package com.ryubal.materialvalidation;

import com.google.android.material.textfield.TextInputLayout;
import com.ryubal.materialvalidation.custom.CustomManualValidation;
import com.ryubal.materialvalidation.custom.CustomValidation;
import com.ryubal.materialvalidation.validations.Range;
import com.ryubal.materialvalidation.validations.Simple;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class that helps you validate Material Design components - More specifically TextInputEditText contained
 * in a TextInputLayout.
 * You must instantiate this class with no arguments, and add validations afterwards with the "add" methods.
 * Whenever you'd like to actually run the validations, call the {@link #validate()} method. Refer to the documentation
 * for more information
 */
public class MaterialValidation {

    // Holder of all of the validations that were added
    private List<SingleValidation> validationsList;

    // Holder of invalid text input layouts
    private List<TextInputLayout> invalidTextInputLayouts;

    /**
     * Creates a new instance of the validator
     */
    public MaterialValidation() {
        this.validationsList = new ArrayList<>();
        this.invalidTextInputLayouts = new ArrayList<>();
    }

    /**
     * Adds a new custom manual validation
     * @param customManualValidation The actual validation callback. The validation
     *                               will be added to the list of validations of this instance,
     *                               and when the {@link #validate()} method is called, it will be executed
     *                               along the rest of the validations.
     */
    public void add(CustomManualValidation customManualValidation) {
        SingleValidation singleValidation = new SingleValidation(customManualValidation);
        validationsList.add(singleValidation);
    }

    /**
     * Adds a new validation with a regular expression
     * @param textInputLayout A reference to the TextInputLayout. Inside there must be a TextInputEditText.
     * @param regex The regular expression
     * @param errorMsg The error to be shown if the field is invalid
     */
    public void add(TextInputLayout textInputLayout, String regex, String errorMsg) {
        Pattern pattern = Pattern.compile(regex);
        add(textInputLayout, pattern, errorMsg);
    }

    /**
     * Adds a new validation with a compiled pattern
     * @param textInputLayout A reference to the TextInputLayout. Inside there must be a TextInputEditText.
     * @param pattern The compiled pattern
     * @param errorMsg The error to be shown if the field is invalid
     */
    public void add(TextInputLayout textInputLayout, Pattern pattern, String errorMsg) {
        SingleValidation singleValidation = new SingleValidation(textInputLayout, pattern, errorMsg);
        validationsList.add(singleValidation);
    }

    /**
     * Adds a new validation with an int range
     * @param textInputLayout A reference to the TextInputLayout. Inside there must be a TextInputEditText.
     * @param range The range. Input has to be numeric, within this range.
     * @param errorMsg The error to be shown if the field is invalid
     */
    public void add(TextInputLayout textInputLayout, Range range, String errorMsg) {
        SingleValidation singleValidation = new SingleValidation(textInputLayout, range, errorMsg);
        validationsList.add(singleValidation);
    }

    /**
     * Adds a new validation with a simple (pre-defined) validation rule
     * @param textInputLayout A reference to the TextInputLayout. Inside there must be a TextInputEditText.
     * @param simple The pre-defined validation
     * @param errorMsg The error to be shown if the field is invalid
     */
    public void add(TextInputLayout textInputLayout, Simple simple, int arg, String errorMsg) {
        SingleValidation singleValidation = new SingleValidation(textInputLayout, simple, arg, errorMsg);
        validationsList.add(singleValidation);
    }

    /**
     * Adds a new validation with a custom validation
     * @param textInputLayout A reference to the TextInputLayout. Inside there must be a TextInputEditText.
     * @param customValidation The custom validation
     * @param errorMsg The error to be shown if the field is invalid
     */
    public void add(TextInputLayout textInputLayout, CustomValidation customValidation, String errorMsg) {
        SingleValidation singleValidation = new SingleValidation(textInputLayout, customValidation, errorMsg);
        validationsList.add(singleValidation);
    }

    /**
     * Runs all of the validations that were added to this instance.
     * Whenever a validation fails, the associated error will be shown below the corresponding TextInputEditText.
     *
     * @return true if all fields were valid, false if at least one field is invalid
     */
    public boolean validate() {
        boolean isValid = true;

        for(SingleValidation validation: validationsList) {
            // Do we have a manual validation?
            if(validation.getCustomManualValidation() != null) {
                isValid = validateCustomManual(validation) && isValid;

            }else if(validation.getTextInputLayout() != null) {
                // Run validation only is this field is not invalid yet
                // This is because a field can be added more than once. In that case, we'll prevent further
                // validations of the same field when it is invalid
                if (!invalidTextInputLayouts.contains(validation.getTextInputLayout())) {
                    boolean isCurrentValid;

                    if (validation.getValidationType() == SingleValidation.ValidationType.PATTERN)
                        isCurrentValid = validateRegex(validation);
                    else if (validation.getValidationType() == SingleValidation.ValidationType.RANGE)
                        isCurrentValid = validateRange(validation);
                    else if (validation.getValidationType() == SingleValidation.ValidationType.SIMPLE)
                        isCurrentValid = validateSimple(validation);
                    else if (validation.getValidationType() == SingleValidation.ValidationType.CUSTOM)
                        isCurrentValid = validateCustom(validation);
                    else
                        isCurrentValid = true;

                    isValid = isCurrentValid && isValid;

                    // If this input was invalid, add it to the list of invalid inputs
                    if (!isCurrentValid)
                        invalidTextInputLayouts.add(validation.getTextInputLayout());
                }
            }
        }

        return isValid;
    }

    /**
     * After you run the {@link #validate()} method, this will return a list of the fields that were invalid
     *
     * @return List of fields that were invalid
     */
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

    private boolean validateCustomManual(SingleValidation validation) {
        return validation.getCustomManualValidation().validate();
    }
}