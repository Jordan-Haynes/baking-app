package com.example.android.bakingapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class RecipeListActivity extends AppCompatActivity implements RecipeListFragment.OnRecipeSelectCallback {

    private static final String TAG = "RecipeListActivity";
    private static final String RECIPE_DETAILS = "Recipe Details";

    private boolean mTwoPane;

    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        if(findViewById(R.id.recipe_list_linear_layout) != null) {
            mTwoPane = true;
            Log.d(TAG, "Now running on two pane.");
            if(savedInstanceState == null) {
                FragmentManager fm = getSupportFragmentManager();

                Log.d(TAG, "Start of main activity");

                RecipeListFragment fragment = (RecipeListFragment) fm.findFragmentById(R.id.fragment_container);

                if (fragment == null) {
                    fragment = new RecipeListFragment();
                    fm.beginTransaction()
                            .add(R.id.fragment_container, fragment)
                            .commit();
                }

                RecipeDetailsFragment topFragment = (RecipeDetailsFragment) fm.findFragmentById(R.id.recipe_details_layout);
                RecipeStepsFragment lowerFragment = (RecipeStepsFragment) fm.findFragmentById(R.id.recipe_steps_layout);


                if (topFragment == null && lowerFragment == null) {
                    topFragment = new RecipeDetailsFragment();
                    lowerFragment = new RecipeStepsFragment();
                    topFragment.setPosition(0);
                    lowerFragment.setPosition(0);
                    fm.beginTransaction()
                            .add(R.id.recipe_details_layout, topFragment)
                            .add(R.id.recipe_steps_layout, lowerFragment)
                            .commit();
                }
            }
        } else {
            mTwoPane = false;

            FragmentManager fm = getSupportFragmentManager();

            Log.d(TAG, "Start of main activity");

            RecipeListFragment fragment = (RecipeListFragment) fm.findFragmentById(R.id.fragment_container);

            if (fragment == null) {
                fragment = new RecipeListFragment();
                fm.beginTransaction()
                        .add(R.id.fragment_container, fragment)
                        .commit();
            }
        }
    }

    public void onRecipeSelected(int position) {
        if (mTwoPane) {

            Log.d(TAG, "Update recipeSteps for recipe " + position);
            FragmentManager fm = getSupportFragmentManager();

            RecipeDetailsFragment topFragment = new RecipeDetailsFragment();
            RecipeStepsFragment lowerFragment = new RecipeStepsFragment();
            topFragment.setPosition(position);
            lowerFragment.setPosition(position);
            fm.beginTransaction()
                    .replace(R.id.recipe_details_layout, topFragment)
                    .replace(R.id.recipe_steps_layout, lowerFragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, RecipeDetailsActivity.class);
            intent.putExtra(RECIPE_DETAILS, position);
            startActivity(intent);
        }

        CurrentRecipeService.startActionUpdateRecipe(this, position);
    }
}
