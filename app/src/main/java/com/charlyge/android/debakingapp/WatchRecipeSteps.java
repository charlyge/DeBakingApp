package com.charlyge.android.debakingapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.charlyge.android.debakingapp.fragments.WatchRecipeStepsFragment;
import com.charlyge.android.debakingapp.model.Steps;

import java.util.ArrayList;

import static com.charlyge.android.debakingapp.RecipesActivity.STEPS_KEY;
import static com.charlyge.android.debakingapp.fragments.SelectRecipeDetailFragment.ADAPTER_POSITION;

public class WatchRecipeSteps extends AppCompatActivity {
    private final String FRAGMENT_TAG = "myfragmenttag";
    private ArrayList<Steps> stepsArrayList;
    private WatchRecipeStepsFragment watchRecipeStepsFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_recipe_step);
        if(savedInstanceState!=null){
            watchRecipeStepsFragment = (WatchRecipeStepsFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        }
        else if(watchRecipeStepsFragment==null) {
            watchRecipeStepsFragment = new WatchRecipeStepsFragment();
        }
        watchRecipeStepsFragment = new WatchRecipeStepsFragment();
        Intent intent = getIntent();

        if(intent.hasExtra(STEPS_KEY)){
            stepsArrayList= (ArrayList<Steps>) intent.getSerializableExtra(STEPS_KEY);
            watchRecipeStepsFragment.setStepsArrayList(stepsArrayList);

        }

        if(intent.hasExtra(ADAPTER_POSITION)){
            int AdapterPosition = intent.getIntExtra(ADAPTER_POSITION,9);
            watchRecipeStepsFragment.setAdapterPosition(AdapterPosition);
        }

        if(!watchRecipeStepsFragment.isInLayout()){
            getSupportFragmentManager().beginTransaction().replace(R.id.watch_recipe_frag_container,watchRecipeStepsFragment,FRAGMENT_TAG).commit();
        }

    }
}
