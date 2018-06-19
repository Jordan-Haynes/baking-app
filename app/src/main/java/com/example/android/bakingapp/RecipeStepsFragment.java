package com.example.android.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.bakingapp.Data.RecipesList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by jordanhaynes on 4/15/18.
 */

public class RecipeStepsFragment extends Fragment {

    private static final String TAG = "RecipeStepsFragment";
    private static final String STEP_POSITION = "Step Position";
    public static final String STEP_DETAILS = "Step Details Array";


    private JSONObject recipe = null;
    private final RecipesList recipesList = new RecipesList();
    private int position;

    ArrayList<String> stepsList = new ArrayList<>();

    private final ArrayList<StepDetails> stepDetails =  new ArrayList<>();


    public RecipeStepsFragment(){}

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

        Log.d(TAG, "Creating fragment to display recipe steps");

        try {
            JSONArray steps = recipe.getJSONArray("steps");
            for (int i=0; i < steps.length(); i++) {
                JSONObject obj = steps.getJSONObject(i);

                String shortDescription = obj.getString("shortDescription");
                String description = obj.getString("description");
                String video = obj.getString("videoURL");

                stepDetails.add(new StepDetails(shortDescription, description, video));
            }

            final ListView listView = view.findViewById(R.id.recipe_steps);

            listView.setAdapter(new StepsAdapter(getActivity()));

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Object listItem = listView.getItemAtPosition(position);

                    Log.d(TAG, "listItem is: " + listItem);

                    Log.d(TAG, "Clicked on " + position + ". " + stepDetails.get(position).shortDescription);

                    Intent intent = new Intent(getActivity(), RecipeMediaActivity.class);
                    intent.putExtra(STEP_DETAILS, stepDetails.get(position).videoURL);
                    startActivity(intent);
                }
            });

            Log.d(TAG, "Steps list is: " + stepDetails);
        } catch (JSONException exception) {
            Log.e(TAG, exception.toString());
        }

        return view;
    }

    public class StepsAdapter extends BaseAdapter {

        private final Context mContext;

        public StepsAdapter(Context context) {
            mContext = context;
        }

        public int getCount() {
            return stepDetails.size();
        }

        public Object getItem(int position) {
            return stepDetails.get(position);
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

            textView.setText(stepDetails.get(position).shortDescription);

            return textView;
        }
    }
}
