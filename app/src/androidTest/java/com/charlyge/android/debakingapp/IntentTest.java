package com.charlyge.android.debakingapp;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.charlyge.android.debakingapp.RecipesActivity.INGREDIENT_KEY;
import static com.charlyge.android.debakingapp.RecipesActivity.STEPS_KEY;
import static com.charlyge.android.debakingapp.fragments.SelectRecipeDetailFragment.ADAPTER_POSITION;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class IntentTest {
    @Rule
    public IntentsTestRule<RecipesActivity> intentsTestRule = new IntentsTestRule<>(RecipesActivity.class);

    @Before
    public void stubAllExternalIntents() {

        intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));

    }

    @Test
    public void intentTest(){
        onView(withId(R.id.recycler_view_main)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        intended(allOf(hasExtraWithKey(STEPS_KEY),hasExtraWithKey(INGREDIENT_KEY)));


        onView(withId(R.id.recycler_select)).perform(RecyclerViewActions.scrollToPosition(3)).perform(click());
        intended(allOf(hasExtraWithKey(ADAPTER_POSITION),hasExtra(ADAPTER_POSITION,3)));

    }
}
