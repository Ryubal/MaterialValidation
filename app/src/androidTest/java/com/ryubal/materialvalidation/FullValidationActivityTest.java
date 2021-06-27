package com.ryubal.materialvalidation;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onData;
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
import static org.hamcrest.Matchers.anything;

@RunWith(AndroidJUnit4.class)
public class FullValidationActivityTest {
    @Rule
    public ActivityScenarioRule<FullValidationActivity> activityScenarioRule = new ActivityScenarioRule<>(FullValidationActivity.class);

    @Test
    public void testInvalidFields() {
        performClick(R.id.buttonSubmit);

        // Alert displayed? Dismiss it
        onView(withText("Please select the checkbox")).check(matches(isDisplayed()));
        onView(withText(android.R.string.ok)).perform(click());

        // Check checkbox
        onView(withId(R.id.checkBox)).perform(scrollTo(), click());

        // Test again
        performClick(R.id.buttonSubmit);
        onView(withText("Please select option 2")).check(matches(isDisplayed()));
        onView(withText(android.R.string.ok)).perform(click());

        // Check option 2
        performClick(R.id.radioButton2);

        // Test again
        performClick(R.id.buttonSubmit);
        onView(withText("Please select the switch")).check(matches(isDisplayed()));
        onView(withText(android.R.string.ok)).perform(click());

        // Select switch
        performClick(R.id.elemSwitch);

        // Test again
        onView(withText("Please enter your name")).check(matches(isDisplayed()));
        onView(withText("Please select a gender")).check(matches(isDisplayed()));
    }

    @Test
    public void testValidFields() {
        // Fill textfield
        typeOnTextField(R.id.editTextName, "Example");

        // Select index 0 from dropdown
        performClick(R.id.autoCompleteGender);
        onData(anything()).atPosition(0).inRoot(RootMatchers.isPlatformPopup()).perform(click());

        // Check elements
        performClick(R.id.checkBox);
        performClick(R.id.radioButton2);
        performClick(R.id.elemSwitch);

        // Test
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
