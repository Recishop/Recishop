package com.example.recishop.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recishop.MainActivity;
import com.example.recishop.R;
import com.example.recishop.RecipesAdapter;
import com.example.recishop.RecyclerViewListener;
import com.example.recishop.models.Recipe;
import com.example.recishop.models.UserViewModel;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    private static final String TAG = ProfileFragment.class.getSimpleName();
    private static final String WELCOME_MESSAGE = "Welcome back, ";
    private static final String ADD_RECIPE_CONFIRMATION = "RecipeConfirmation";

    private UserViewModel userViewModel;

    TextView tvUsername;
    RecyclerView rvUserRecipes;
    ImageView ivProfilePicture;
    List<Recipe> recipeList;
    RecipesAdapter recipesAdapter;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.i(TAG, "Current User: " + ParseUser.getCurrentUser().getUsername());

        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        tvUsername = view.findViewById(R.id.tvWelcomeMessage);
        ivProfilePicture = view.findViewById(R.id.ivProfilePicture);
        rvUserRecipes = view.findViewById(R.id.rvUserRecipes);

        ivProfilePicture.setImageResource(R.drawable.ic_baseline_person_24);
        tvUsername.setText(WELCOME_MESSAGE + ParseUser.getCurrentUser().getUsername() + "!");

        recipeList = new ArrayList<>();
        recipesAdapter = new RecipesAdapter(recipeList, getContext());
        recipesAdapter.setRecyclerViewListener(new RecyclerViewListener() {
            @Override
            public void onItemClick(int position) {
                Recipe recipe = recipeList.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(String.format("Add the ingredients for %s to the shopping list?", recipe.getName()));
                builder.setPositiveButton("OK!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        userViewModel.addRecipeIngredientsToShoppingList(recipe);
                        Toast.makeText(getContext(), String.format("Added %s ingredients to list!", recipe.getName()), Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {@Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing and dismiss the dialog

                    }
                });
                builder.create().show();
            }
        });
        rvUserRecipes.setAdapter(recipesAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvUserRecipes.setLayoutManager(linearLayoutManager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(rvUserRecipes.getContext(), linearLayoutManager.getOrientation());
        rvUserRecipes.addItemDecoration(itemDecoration);

        queryRecipes();
    }

    protected void queryRecipes() {
        ParseQuery<Recipe> recipeParseQuery = ParseQuery.getQuery(Recipe.class);

        // Specify any kind of filtering
        recipeParseQuery.include(Recipe.KEY_CHEF);
        recipeParseQuery.whereEqualTo(Recipe.KEY_CHEF, ParseUser.getCurrentUser());

        recipeParseQuery.findInBackground(new FindCallback<Recipe>() {
            @Override
            public void done(List<Recipe> objects, ParseException e) {
                if (e == null) {
                    recipeList.clear();
                    recipeList.addAll(objects);
                    recipesAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "UNSUCCESSFULLY DID NOT PULL recipes!", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Recipe query error: " + e);
                    return;
                }
            }
        });
    }

    // FOR CUSTOM ACTION BAR ACTIONS FOR FRAGMENT //

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_profile_fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_menuBtnSignout: {
                ((MainActivity) getActivity()).logoutAndBackToLoginScreen();
            }
            case R.id.action_menuBtnNewRecipe: {
                // Toast.makeText(getContext(), "Create new recipe pressed!", Toast.LENGTH_SHORT).show();
                ((MainActivity)getActivity()).goToCreateRecipe();
            }
        }
        return super.onOptionsItemSelected(item);
    }

}