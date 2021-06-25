package com.ryubal.materialvalidation;

import android.text.Editable;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.ryubal.materialvalidation.custom.CustomManualValidation;
import com.ryubal.materialvalidation.custom.CustomValidation;
import com.ryubal.materialvalidation.validations.Range;
import com.ryubal.materialvalidation.validations.Simple;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.regex.Pattern;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/*
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Matchers.*;
*/

@RunWith(MockitoJUnitRunner.class)
public class MaterialValidationTest {
    @Mock TextInputLayout textInputLayout;
    @Mock TextInputEditText textInputEditText;
    @Mock Editable editable;

    @Spy MaterialValidation materialValidation;

    private final CustomValidation validCustomValidation = input -> true;
    private final CustomValidation invalidCustomValidation = input -> false;

    @Mock CustomManualValidation customManualValidation;

    @Before
    public void setup() {
        when(textInputLayout.getEditText()).thenReturn(textInputEditText);
        when(textInputEditText.getText()).thenReturn(editable);
    }

    @Test
    public void testValidRegex() {
        String testString = "1234";
        when(editable.toString()).thenReturn(testString);

        materialValidation.add(textInputLayout, "^[0-9]+$", "error_msg");
        assertTrue(materialValidation.validate());
        verify(textInputLayout, times(1)).setError(null);
    }

    @Test
    public void testInvalidRegex() {
        String testString = "1234a";
        when(editable.toString()).thenReturn(testString);

        materialValidation.add(textInputLayout, "^[0-9]+$", "error_msg");
        assertFalse(materialValidation.validate());
        verify(textInputLayout, times(1)).setError("error_msg");
    }

    @Test
    public void testValidPattern() {
        String testString = "1234";
        when(editable.toString()).thenReturn(testString);
        Pattern pattern = Pattern.compile("^[0-9]+$");

        materialValidation.add(textInputLayout, pattern, "error_msg");
        assertTrue(materialValidation.validate());
        verify(textInputLayout, times(1)).setError(null);
    }

    @Test
    public void testInvalidPattern() {
        String testString = "1234a";
        when(editable.toString()).thenReturn(testString);
        Pattern pattern = Pattern.compile("^[0-9]+$");

        materialValidation.add(textInputLayout, pattern, "error_msg");
        assertFalse(materialValidation.validate());
        verify(textInputLayout, times(1)).setError("error_msg");
    }

    @Test
    public void testValidRange() {
        String testString = "150";
        when(editable.toString()).thenReturn(testString);
        Range range = new Range(100, 200);

        materialValidation.add(textInputLayout, range, "error_msg");
        assertTrue(materialValidation.validate());
        verify(textInputLayout, times(1)).setError(null);
    }

    @Test
    public void testInvalidRange() {
        String testString = "201";
        when(editable.toString()).thenReturn(testString);
        Range range = new Range(100, 200);

        materialValidation.add(textInputLayout, range, "error_msg");
        assertFalse(materialValidation.validate());
        verify(textInputLayout, times(1)).setError("error_msg");
    }

    @Test
    public void testInvalidRangeWithNonIntegerInput() {
        String testString = "abc";
        when(editable.toString()).thenReturn(testString);
        Range range = new Range(100, 200);

        materialValidation.add(textInputLayout, range, "error_msg");
        assertFalse(materialValidation.validate());
        verify(textInputLayout, times(1)).setError("error_msg");
    }

    @Test
    public void testValidSimpleMinLength() {
        String testString = "abc";
        when(editable.toString()).thenReturn(testString);

        materialValidation.add(textInputLayout, Simple.MIN_LENGTH, 3, "error_msg");
        assertTrue(materialValidation.validate());
        verify(textInputLayout, times(1)).setError(null);
    }

    @Test
    public void testInvalidSimpleMinLength() {
        String testString = "ab";
        when(editable.toString()).thenReturn(testString);

        materialValidation.add(textInputLayout, Simple.MIN_LENGTH, 3, "error_msg");
        assertFalse(materialValidation.validate());
        verify(textInputLayout, times(1)).setError("error_msg");
    }

    @Test
    public void testValidSimpleMaxLength() {
        String testString = "abc";
        when(editable.toString()).thenReturn(testString);

        materialValidation.add(textInputLayout, Simple.MAX_LENGTH, 3, "error_msg");
        assertTrue(materialValidation.validate());
        verify(textInputLayout, times(1)).setError(null);
    }

    @Test
    public void testInvalidSimpleMaxLength() {
        String testString = "abcd";
        when(editable.toString()).thenReturn(testString);

        materialValidation.add(textInputLayout, Simple.MAX_LENGTH, 3, "error_msg");
        assertFalse(materialValidation.validate());
        verify(textInputLayout, times(1)).setError("error_msg");
    }

    @Test
    public void testValidCustomValidation() {
        materialValidation.add(textInputLayout, validCustomValidation, "error_msg");
        assertTrue(materialValidation.validate());
        verify(textInputLayout, times(1)).setError(null);
    }

    @Test
    public void testInvalidCustomValidation() {
        materialValidation.add(textInputLayout, invalidCustomValidation, "error_msg");
        assertFalse(materialValidation.validate());
        verify(textInputLayout, times(1)).setError("error_msg");
    }

    @Test
    public void testCustomManualValidation() {
        materialValidation.add(customManualValidation);
        when(customManualValidation.validate()).thenReturn(true);

        assertTrue(materialValidation.validate());
        verify(customManualValidation, times(1)).validate();
    }
}
