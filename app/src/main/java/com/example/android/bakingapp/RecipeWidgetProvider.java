package com.example.android.bakingapp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.android.bakingapp.Data.RecipesList;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    private static final String TAG = "RecipeWidgetProvider";
    private static final String CURRENT_RECIPE = "Current Recipe";

    private static final RecipesList recipesList = new RecipesList();

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int recipe,
                                int appWidgetId) {

        Intent intent = new Intent(context, WidgetRemoteViewsService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        // This is the CURRENT recipe
        intent.putExtra(CURRENT_RECIPE, recipe);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);

        try {
            JSONObject recipeTitle = recipesList.recipes.getJSONObject(recipe);
            views.setTextViewText(R.id.widget_recipe_title, recipeTitle.getString("name"));
        } catch (JSONException exception) {
            Log.e(TAG, exception.toString());
        }

        views.setRemoteAdapter(appWidgetId, R.id.widget_list_view, intent);
        views.setEmptyView(R.id.widget_list_view, R.id.empty_view);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.d(TAG, "Calling provider onUpdate");

        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, 0, appWidgetId);
        }
    }

    public static void updateRecipeWidgets(Context context, AppWidgetManager appWidgetManager, int recipe, int[] appWidgetIds) {
        Log.d(TAG, "Calling provider updateRecipeWidgets");

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, recipe, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

