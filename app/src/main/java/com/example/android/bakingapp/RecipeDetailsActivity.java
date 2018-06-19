package com.example.android.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by jordanhaynes on 4/14/18.
 */

public class RecipeDetailsActivity extends AppCompatActivity {
    private static final String TAG = "RecipeListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_recipe_details);

        Intent intent = getIntent();
        int recipePosition = intent.getIntExtra(RecipeListFragment.RECIPE_DETAILS, 0);

        Log.d(TAG, "New activity started in RecipeDetailsActivity. Recipe position is " + recipePosition);

        FragmentManager fm = getSupportFragmentManager();

        Log.d(TAG, "Start of Details activity");

        RecipeDetailsFragment topFragment = (RecipeDetailsFragment) fm.findFragmentById(R.id.recipe_details_layout);
        RecipeStepsFragment lowerFragment = (RecipeStepsFragment) fm.findFragmentById(R.id.recipe_steps_layout);


        if (topFragment == null && lowerFragment == null) {
            topFragment = new RecipeDetailsFragment();
            lowerFragment = new RecipeStepsFragment();
            topFragment.setPosition(recipePosition);
            lowerFragment.setPosition(recipePosition);
            fm.beginTransaction()
                    .add(R.id.recipe_details_layout, topFragment)
                    .add(R.id.recipe_steps_layout, lowerFragment)
                    .commit();
        }
    }
}
