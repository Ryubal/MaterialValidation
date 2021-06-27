package com.ryubal.materialvalidation;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

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