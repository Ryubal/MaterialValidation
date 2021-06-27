package com.ryubal.materialvalidation.custom;

/**
 * Interface definition for a callback to be invoked when a custom validation should run.
 * The {@link #validate(String)} method must return the result of the validation, and MaterialValidation will take care of showing the error if applicable.
 *
 */
public interface CustomValidation {
    boolean validate(String input);
}
