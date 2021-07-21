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

import com.example.recishop.R;
import com.example.recishop.databinding.FragmentRecipeCreationBindingImpl;
import com.example.recishop.models.Recipe;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

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
//                    ParseUser currentUser = ParseUser.getCurrentUser();
//                    saveRecipe(recipeName, currentUser);
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
     * on the Positive Action button from the AlertDialog
     */
    @Override
    public void finishRecipe() {
        // TODO: Make recipe and add it to the database from here.
        Toast.makeText(getContext(), "Finish recipe call back called!", Toast.LENGTH_SHORT).show();
    }

//    private void saveRecipe(String recipeName, ParseUser currentUser) {
//        Recipe recipe = new Recipe();
//        recipe.setName(recipeName);
//        recipe.setChef(currentUser);
//        recipe.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(ParseException e) {
//                if (e!=null){
//                    Log.e(TAG, "Error while saving", e);
//                    Toast.makeText(getContext(), "Error while saving", Toast.LENGTH_SHORT).show();
//                }
//                Log.i(TAG, "Recipe start was successful");
//                fragmentRecipeCreationBinding.etRecipeName.setText("");
//                Intent i = new Intent(getContext(), NewRecipeForm.class);
//                i.putExtra("recipe", recipe);
//                startActivity(i);
//            }
//        });
//    }
}