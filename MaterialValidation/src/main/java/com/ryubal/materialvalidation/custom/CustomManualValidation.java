package com.ryubal.materialvalidation.custom;

/**
 * Interface definition for a callback to be invoked when a custom manual validation should run.
 * This is intended to validate elements that MaterialValidation cannot manipulate. You must implement your own validation processes in the {@link #validate()} method.
 *
 */
public interface CustomManualValidation {
    boolean validate();
}
