package com.example.android.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.bakingapp.Data.RecipesList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by jordanhaynes on 4/25/18.
 */

public class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private static final String TAG = "ListRemoteViewsFactory";

    private final Context context;
    private final int appWidgetId;
    private final RecipesList recipesList = new RecipesList();
    public static ArrayList<String> ingredientsList = new ArrayList<>();

    private final int recipe; // this is the current recipe
    private JSONArray listIngredients;

    public ListRemoteViewsFactory(Context context, Intent intent) {
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        recipe = intent.getIntExtra("Current Recipe", 0);
        try {
            JSONObject currentRecipe = recipesList.recipes.getJSONObject(recipe);
            listIngredients = currentRecipe.getJSONArray("ingredients");
        } catch (JSONException exception) {
            Log.e(TAG, exception.toString());
        }

    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return listIngredients.length();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        Log.d(TAG, "Calling factory getViewAt for position " + position);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredient_widget);

        try {
            JSONObject ingredients = listIngredients.getJSONObject(position);

            int quantity = ingredients.getInt("quantity");
            String measure = ingredients.getString("measure");
            String ingredient = ingredients.getString("ingredient");

            String summaryIngredients = quantity + " " + measure + " of " + ingredient;
            views.setTextViewText(R.id.widget_list_view_ingredient, summaryIngredients);
        } catch (JSONException exception) {
            Log.e(TAG, exception.toString());
        }

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
