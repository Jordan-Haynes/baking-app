package com.example.android.bakingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.bakingapp.Data.RecipesList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by jordanhaynes on 4/3/18.
 */

public class RecipeDetailsFragment extends Fragment {

    private static final String TAG = "RecipeDetailsFragment";

    private JSONObject recipe = null;
    public ListView listView;

    private final RecipesList recipesList = new RecipesList();
    private final ArrayList<String> ingredientsList = new ArrayList<>();

    public RecipeDetailsFragment() {

    }

    public void setPosition(int position) {
        try {
            JSONObject recipe = recipesList.recipes.getJSONObject(position);
            this.recipe = recipe;
        } catch (JSONException exception) {
            Log.e(TAG, exception.toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_details, container, false);

        Log.d(TAG, "Creating fragment to display recipe name and ingredients");

        try {
            // Set recipe name
            String recipeName = recipe.getString("name");
            TextView nameWidget = view.findViewById(R.id.recipe_name);
            nameWidget.setText(recipeName);

            JSONArray ingredients = recipe.getJSONArray("ingredients");
            for (int i=0; i < ingredients.length(); i++) {
                JSONObject obj = ingredients.getJSONObject(i);

                int quantity = obj.getInt("quantity");
                String measure = obj.getString("measure");
                String ingredient = obj.getString("ingredient");

                ingredientsList.add(quantity + " " + measure + " of " + ingredient);
            }

            ListView listView = view.findViewById(R.id.recipe_ingredients);

            listView.setAdapter(new ListAdapter(getActivity()));

            // Set number of servings
            int servingsNumber = recipe.getInt("servings");
            TextView servingWidget = view.findViewById(R.id.recipe_servings);
            servingWidget.setText("Makes about " + servingsNumber + " servings");

            Log.d(TAG, "Recipe is " + recipeName + ", Servings is "
                    + servingsNumber + ", and Ingredients is as follows: " + ingredients);
            Log.d(TAG, "Ingredients list is: " + ingredientsList);
        } catch (JSONException exception) {
            Log.e(TAG, exception.toString());
        }

        return view;
    }

    public class ListAdapter extends BaseAdapter {

        private final Context mContext;

        public ListAdapter(Context context) {
            mContext = context;
        }

        public int getCount() {
            return ingredientsList.size();
        }

        public Object getItem(int position) {
            Log.d(TAG, "ListAdapter getItem " + ingredientsList.get(position));
            return ingredientsList.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView;
            if (convertView == null) {
                textView = new TextView(mContext);
            } else {
                textView = (TextView) convertView;
            }

            textView.setText(ingredientsList.get(position));

            return textView;
        }
    }
}
