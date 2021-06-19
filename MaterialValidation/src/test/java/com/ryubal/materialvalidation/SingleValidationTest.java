package com.ryubal.materialvalidation;

import android.text.Editable;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.ryubal.materialvalidation.custom.CustomValidation;
import com.ryubal.materialvalidation.exceptions.MissingEditTextException;
import com.ryubal.materialvalidation.validations.Range;
import com.ryubal.materialvalidation.validations.Simple;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SingleValidationTest {
    @Mock TextInputLayout textInputLayout;
    @Mock TextInputEditText textInputEditText;
    @Mock Editable editable;
    @Mock Pattern pattern;
    @Mock Range range;
    @Mock Simple simple;
    @Mock CustomValidation customValidation;

    private SingleValidation singleValidationWithPattern;
    private SingleValidation singleValidationWithRange;
    private SingleValidation singleValidationWithSimple;
    private SingleValidation singleValidationWithCustom;

    @Before
    public void setup() {
        singleValidationWithPattern = new SingleValidation(textInputLayout, pattern, "");
        singleValidationWithRange = new SingleValidation(textInputLayout, range, "");
        singleValidationWithSimple = new SingleValidation(textInputLayout, simple, 0, "");
        singleValidationWithCustom = new SingleValidation(textInputLayout, customValidation, "");
    }

    @Test
    public void isSingleValidationTypeRegex() {
        assertEquals(singleValidationWithPattern.getValidationType(), SingleValidation.ValidationType.PATTERN);
    }

    @Test
    public void isSingleValidationTypeRange() {
        assertEquals(singleValidationWithRange.getValidationType(), SingleValidation.ValidationType.RANGE);
    }

    @Test
    public void isSingleValidationTypeSimple() {
        assertEquals(singleValidationWithSimple.getValidationType(), SingleValidation.ValidationType.SIMPLE);
    }

    @Test
    public void isSingleValidationTypeCustom() {
        assertEquals(singleValidationWithCustom.getValidationType(), SingleValidation.ValidationType.CUSTOM);
    }

    @Test(expected = MissingEditTextException.class)
    public void doesGetTextWithoutEditTextThrowException() {
        singleValidationWithPattern.getText();
    }

    @Test
    public void doesGetTextReturnText() {
        String testString = "1234";

        when(textInputLayout.getEditText()).thenReturn(textInputEditText);
        when(textInputEditText.getText()).thenReturn(editable);
        when(editable.toString()).thenReturn(testString);

        assertEquals(singleValidationWithPattern.getText(), testString);
    }
}
