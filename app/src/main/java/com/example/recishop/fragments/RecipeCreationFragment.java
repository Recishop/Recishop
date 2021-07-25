package com.example.recishop.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProvider;
import com.example.recishop.R;
import com.example.recishop.databinding.FragmentRecipeCreationBindingImpl;
import com.example.recishop.models.ParseIngredient;
import com.example.recishop.models.ParseRecipe;
import com.example.recishop.models.RecishopIngredient;
import com.example.recishop.models.UserViewModel;
import com.google.gson.Gson;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

/**
 * The entry point to making recipe.  Here, we will simply take a recipe name and will
 * build an AlertDialog that allows the user to input the required materials for their recipe.
 *
 * @author tallt
 */
public class RecipeCreationFragment extends Fragment implements RecipeCreationDialog.RecipeDialogInterfaceListeners  {
    private static final String TAG = RecipeCreationFragment.class.getSimpleName();
    private static final String RECIPE_CREATION_TAG = RecipeCreationDialog.class.getSimpleName();

    /**
     * Handle to UI bindings for Recipe Creation Fragment screen
     */
    FragmentRecipeCreationBindingImpl fragmentRecipeCreationBinding;

    /**
     * Dialog for inputting recipe materials.
     */
    RecipeCreationDialog recipeCreationDialog;

    /**
     * UserViewModel contains information about the user required across fragments
     */
    UserViewModel userViewModel;

    /**
     * Request code for the RecipeCreationDialog AlertDialog
     */
    private static final int RECIPE_CREATION_REQUEST_CODE = 100;


    public RecipeCreationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentRecipeCreationBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_creation, container, false);
        fragmentRecipeCreationBinding.setLifecycleOwner(this);

        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        return fragmentRecipeCreationBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fragmentRecipeCreationBinding.btnStartCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recipeName = fragmentRecipeCreationBinding.etRecipeName.getText().toString();
                if(recipeName.isEmpty()){
                    Toast.makeText(getContext(), "First, give your recipe a name", Toast.LENGTH_SHORT).show();
                }
                else{
                    recipeCreationDialog = new RecipeCreationDialog();
                    recipeCreationDialog.setRecipeName(recipeName);
                    recipeCreationDialog.setTargetFragment(RecipeCreationFragment.this, RECIPE_CREATION_REQUEST_CODE);
                    recipeCreationDialog.show(getParentFragmentManager(), RECIPE_CREATION_TAG);
                }
            }
        });
    }

    /**
     * Dialog interface methods from RecipeCreationDialog.  This method is called
     * on the Positive Action button from the AlertDialog.
     *
     * Creates ParseObject to push to the database out of the ingredients list and
     * recipe name
     */
    @Override
    public void finishRecipe(List<RecishopIngredient> recishopIngredientList) {
        // TODO: Make recipe and add it to the database from here.
        Toast.makeText(getContext(), "Finish recipe call back called!", Toast.LENGTH_SHORT).show();

        String recipeJson = new Gson().toJson(recishopIngredientList, recishopIngredientList.getClass());

        ParseObject parseRecipe = ParseObject.create("ParseRecipe");
        parseRecipe.put(ParseRecipe.KEY_RECIPE_NAME, fragmentRecipeCreationBinding.etRecipeName.getText().toString());
        parseRecipe.put(ParseRecipe.KEY_RECIPE_INGREDIENTS, recipeJson);
        parseRecipe.put(ParseRecipe.KEY_RECIPE_OWNER, ParseUser.getCurrentUser());

        parseRecipe.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error while saving parseRecipe: " + e.getMessage());
                    Toast.makeText(getContext(), "Error saving recipe.", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Log.d(TAG, "Saving recipe was successful");
                    fragmentRecipeCreationBinding.etRecipeName.setText("");
                }
            }
        });

        // Save unknown ingredients to database
        for (RecishopIngredient recishopIngredient : recishopIngredientList) {
            if (!userViewModel.getKnownIngredients().contains(recishopIngredient.getIngredientName())) {
                ParseObject parseIngredient = ParseObject.create("ParseIngredient");
                parseIngredient.put(ParseIngredient.KEY_INGREDIENT_NAME, recishopIngredient.getIngredientName());
                parseIngredient.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e != null) {
                            Log.e(TAG, "Could not save ingredient to database: " + recishopIngredient.getIngredientName() + " | " + e.getMessage());
                            Toast.makeText(getContext(), "Error saving one or more ingredients", Toast.LENGTH_SHORT);
                            return;
                        } else {
                            Log.d(TAG, "Successfully saved ingredient: " + recishopIngredient.getIngredientName());
                        }
                    }
                });
            }
        }
    }
}