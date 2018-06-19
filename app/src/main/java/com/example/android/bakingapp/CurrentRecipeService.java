package com.example.android.bakingapp;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

/**
 * Created by jordanhaynes on 6/5/18.
 */

public class CurrentRecipeService extends IntentService {

    private static final String ACTION_UPDATE_RECIPE = "com.example.android.bakingapp.action.update_widget";
    private static final String RECIPE_INDEX = "Recipe Index";


    public CurrentRecipeService() {
        super("CurrentRecipeService");
    }

    public static void startActionUpdateRecipe(Context context, int position) {
        Intent intent = new Intent(context, CurrentRecipeService.class);
        intent.setAction(ACTION_UPDATE_RECIPE);
        intent.putExtra(RECIPE_INDEX, position);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_RECIPE.equals(action)) {
                int position = intent.getIntExtra("Recipe Index", 0);
                handleActionUpdateWidget(position);
            }
        }
    }

    private void handleActionUpdateWidget(int position) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeWidgetProvider.class));

        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list_view);

        RecipeWidgetProvider.updateRecipeWidgets(this, appWidgetManager, position, appWidgetIds);
    }

}
