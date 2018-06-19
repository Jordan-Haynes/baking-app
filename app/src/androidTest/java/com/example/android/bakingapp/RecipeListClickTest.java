package com.example.android.bakingapp;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by jordanhaynes on 4/25/18.
 */

@RunWith(AndroidJUnit4.class)
public class RecipeListClickTest {
    @Rule
    public ActivityTestRule<RecipeListActivity> mActivityTestRule = new ActivityTestRule<>(RecipeListActivity.class);

    @Test
    public void clickIncrementButton_ChangesQuantityAndCost() {
        // Steps 1 and 2 of espresso testing
        onView((withId(R.id.recipe_list_recycler_view))).perform(click());

        // Step 3:
        onView(withId(R.id.recipe_ingredients)).check(matches(isDisplayed()));
        onView(withId(R.id.recipe_steps)).check(matches(isDisplayed()));
    }
}
