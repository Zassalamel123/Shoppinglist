package com.example.einkaufslisteapp;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.example.einkaufslisteapp.Activities.ManagerListActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ManagerListActivityTest {

    @Rule
    public ActivityScenarioRule<ManagerListActivity> activityRule =
            new ActivityScenarioRule<>(ManagerListActivity.class);

    @Test
    public void createNewList() {
        onView(withId(R.id.createNewListButton)).perform(click());
        onView(withId(R.id.listTitle)).perform(typeText("Real"));
        onView(withId(R.id.toolBarAdd)).perform(click());
        onView(withId(R.id.constraintLayout)).check(matches(withChild(withText(""))));
        onView(allOf(withTagValue(is((Object) "item")), isDisplayed())).perform(typeText("Banana"));
        onView(withId(R.id.toolBarSave)).perform(click());
    }

    @Test
    public void deleteList() {
        onView(withId(R.id.deleteListButton)).perform(click());
    }
}