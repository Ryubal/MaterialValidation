package com.ryubal.materialvalidation;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import androidx.test.espresso.*;
import androidx.test.espresso.contrib.*;
import androidx.test.espresso.intent.*;
import androidx.test.espresso.matcher.*;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.intent.Intents.*;
import static androidx.test.espresso.intent.matcher.IntentMatchers.*;
import static org.hamcrest.Matchers.*;

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