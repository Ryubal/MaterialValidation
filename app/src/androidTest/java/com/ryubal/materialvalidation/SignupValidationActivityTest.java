package com.ryubal.materialvalidation;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class SignupValidationActivityTest {
    @Rule
    public ActivityScenarioRule<SignupValidationActivity> activityScenarioRule = new ActivityScenarioRule<>(SignupValidationActivity.class);

    @Test
    public void testInvalidFields() {
        performClick(R.id.buttonSubmit);

        // Check errors
        onView(withText("Please enter your name")).check(matches(isDisplayed()));
        onView(withText("Please enter your username")).check(matches(isDisplayed()));
        onView(withText("Please enter your email address")).check(matches(isDisplayed()));
        onView(withText("Please enter a password")).check(matches(isDisplayed()));
        onView(withText("Please enter password confirmation")).check(matches(isDisplayed()));

        // Check error with invalid username
        typeOnTextField(R.id.editTextUsername, "123");
        performClick(R.id.buttonSubmit);
        onView(withText("Username must be at least 4 characters long")).check(matches(isDisplayed()));

        // Check error with invalid password
        typeOnTextField(R.id.editTextPassword, "1234");
        performClick(R.id.buttonSubmit);
        onView(withText("Password must be between 5 and 10 characters long")).check(matches(isDisplayed()));

        // Check error with missmatching passwords
        typeOnTextField(R.id.editTextPassword, "12345");
        typeOnTextField(R.id.editTextPasswordConfirmation, "54321");
        performClick(R.id.buttonSubmit);
        onView(withText("Password and password confirmation must match")).check(matches(isDisplayed()));
    }

    @Test
    public void testValidFields() {
        typeOnTextField(R.id.editTextName, "Example");
        typeOnTextField(R.id.editTextUsername, "username");
        typeOnTextField(R.id.editTextEmailAddress, "example@example.com");
        typeOnTextField(R.id.editTextPassword, "12345");
        typeOnTextField(R.id.editTextPasswordConfirmation, "12345");

        performClick(R.id.buttonSubmit);

        onView(withText("All fields are valid")).check(matches(isDisplayed()));
    }

    private void performClick(int id) {
        onView(withId(id)).perform(click());
    }

    private void typeOnTextField(int id, String text) {
        onView(withId(id)).perform(scrollTo(), typeText(text), closeSoftKeyboard());
    }

    private void clearAndTypeOnTextField(int id, String text) {
        onView(withId(id)).perform(clearText(), scrollTo(), typeText(text), closeSoftKeyboard());
    }
}
