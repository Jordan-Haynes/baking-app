package com.example.android.bakingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bakingapp.Data.RecipesList;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jordanhaynes on 4/14/18.
 */

public class RecipeListFragment extends Fragment {

    private static final String TAG = "RecipeListFragment";
    public static final String RECIPE_DETAILS = "Recipe Details";

    private RecyclerView recyclerView;
    private final RecipesList recipesList = new RecipesList();
    private RecipeAdapter adapter;
    private OnRecipeSelectCallback recipeSelectCallback;

    public interface OnRecipeSelectCallback {
        void onRecipeSelected(int position);
    }

    public RecipeListFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            recipeSelectCallback = (OnRecipeSelectCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnRecipeSelectCallback");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);

        Log.d(TAG, "Creating fragment and getting recipes");

        recyclerView = view.findViewById(R.id.recipe_list_recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new RecipeAdapter();

        recyclerView.setAdapter(adapter);

        return view;
    }

    private class RecipeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView recipeTitleTextView;
        private int position;

        public RecipeHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_recipe, parent, false));
            recipeTitleTextView = itemView.findViewById(R.id.recipe_title);
            itemView.setOnClickListener(this);
        }

        public void bind(int position) {
            this.position = position;

            try {
                JSONObject recipe = recipesList.recipes.getJSONObject(position);
                recipeTitleTextView.setText(recipe.getString("name"));
            } catch (JSONException exception) {
                Log.e(TAG, exception.toString());
            }
        }

        @Override
        public void onClick(View view) {
            Log.d(TAG, "This has been clicked!");
            Toast.makeText(getActivity(), "Recipe title", Toast.LENGTH_SHORT).show();

            recipeSelectCallback.onRecipeSelected(position);
        }
    }

    private class RecipeAdapter extends RecyclerView.Adapter<RecipeHolder> {
        public RecipeAdapter() {
        }

        @Override
        public RecipeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new RecipeHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(RecipeHolder holder, int position) {
            holder.bind(position);
        }

        @Override
        public int getItemCount() {
            Log.d(TAG, "Number of recipes " + recipesList.recipes.length());
            return recipesList.recipes.length();
        }
    }
}
