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
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.intent.Intents.*;
import static androidx.test.espresso.intent.matcher.IntentMatchers.*;
import static org.hamcrest.Matchers.*;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    // Test buttons
    @Test
    public void testBasicValidationButton() {
        Intents.init();
        performClick(R.id.buttonBasicValidation);
        intended(hasComponent(BasicValidationActivity.class.getName()));
        Intents.release();
    }

    @Test
    public void testSignupValidationButton() {
        Intents.init();
        performClick(R.id.buttonSignupValidation);
        intended(hasComponent(SignupValidationActivity.class.getName()));
        Intents.release();
    }

    @Test
    public void testFullValidationButton() {
        Intents.init();
        performClick(R.id.buttonFullValidation);
        intended(hasComponent(FullValidationActivity.class.getName()));
        Intents.release();
    }

    private void performClick(int id) {
        onView(withId(id)).perform(click());
    }
}