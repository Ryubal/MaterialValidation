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
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class BasicValidationActivityTest {
    @Rule
    public ActivityScenarioRule<BasicValidationActivity> activityScenarioRule = new ActivityScenarioRule<>(BasicValidationActivity.class);

    @Test
    public void testInvalidFields() {
        performClick(R.id.buttonSubmit);

        // Alert displayed? Dismiss it
        onView(withText("Both fields are invalid!")).check(matches(isDisplayed()));
        onView(withText(android.R.string.ok)).perform(click());

        // Check errors
        onView(withText("Please enter your username")).check(matches(isDisplayed()));
        onView(withText("Please enter your password")).check(matches(isDisplayed()));
    }

    @Test
    public void testValidFields() {
        typeOnTextField(R.id.editTextUsername, "username");
        typeOnTextField(R.id.editTextPassword, "password");
        performClick(R.id.buttonSubmit);

        // Alert displayed? Dismiss it
        onView(withText("All fields are valid")).check(matches(isDisplayed()));
        onView(withText(android.R.string.ok)).perform(click());

        // Check no errors are displaying
        onView(withText("Please enter your username")).check(doesNotExist());
        onView(withText("Please enter your password")).check(doesNotExist());
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