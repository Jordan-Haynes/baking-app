package com.example.android.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by jordanhaynes on 4/16/18.
 */

public class RecipeMediaActivity extends AppCompatActivity {

    private static final String TAG = "RecipeListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_recipe_media);

        Intent intent = getIntent();
        String stepDetails = intent.getStringExtra(RecipeStepsFragment.STEP_DETAILS);

        FragmentManager fm = getSupportFragmentManager();

        Log.d(TAG, "Start of Media Player activity");

        RecipeMediaFragment fragment = (RecipeMediaFragment) fm.findFragmentById(R.id.media_player);

        if (fragment == null) {
            fragment = new RecipeMediaFragment();

            fragment.setMediaUrl(stepDetails);

            fm.beginTransaction()
                    .add(R.id.media_player, fragment)
                    .commit();
        }
    }
}
