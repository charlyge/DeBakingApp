package com.charlyge.android.debakingapp;


import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.charlyge.android.debakingapp.fragments.SelectRecipeDetailFragment;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RecipesActivityUiTest {
    @Rule
    public ActivityTestRule<RecipesActivity> activityTestRule =
            new ActivityTestRule<>(RecipesActivity.class);

    @Test
    public void recycler_viewOnItemClickOpens_SelectRecipeDetailViewActivity(){

        onView(withId(R.id.recycler_view_main))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.ingredient_tv)).check(matches(isDisplayed()));

    }
}
