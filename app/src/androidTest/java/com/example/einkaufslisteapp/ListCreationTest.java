package com.example.einkaufslisteapp;

import android.content.Context;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.einkaufslisteapp.Activities.ListCreationActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ListCreationTest {

    @Rule
    public ActivityScenarioRule<ListCreationActivity> activityRule =
            new ActivityScenarioRule<>(ListCreationActivity.class);

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.einkaufslisteapp", appContext.getPackageName());
    }

//    @Test
//    public void listIsSavedInDatabase() {
//        ManagerList managerList = new ManagerList(new DatabaseWriter());
//        onView(withId(R.id.listTitle)).perform(typeText("Kaufland"));
//        onView(withId(R.id.item1)).perform(typeText("Banane"));
//        onView(withId(R.id.item2)).perform(typeText("Apfel"));
//        onView(withId(R.id.saveListButton)).perform(click());
//
//    }
}