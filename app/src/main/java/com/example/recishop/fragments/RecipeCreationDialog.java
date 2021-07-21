package com.example.recishop.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.recishop.IngredientsAdapter;
import com.example.recishop.R;
import com.example.recishop.databinding.NewRecipeFormBindingImpl;
import com.example.recishop.models.Ingredient;

import java.util.ArrayList;
import java.util.List;

/**
 * RecipeCreationDialog is a dialog that appears when a user creates a recipe
 * This dialog will display a custom view that walks the user through the process
 * of creating a recipe.
 *
 * At the end of the dialog, the positive click button will call the interface method
 * that is defined in the CreationActivity class
 */
public class RecipeCreationDialog extends DialogFragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private static final String TAG = RecipeCreationDialog.class.getSimpleName();

    /**
     * Handle to UI for activity_creation. In other words, this is the binding to the AlertView
     * that is used to build the recipe. Use this to assign the recipe name.
     */
    private NewRecipeFormBindingImpl newRecipeFormBinding;

    /**
     * Name of new recipe to create. This is set in the RecipeCreationFragment and appears
     * at the top of the AlertDialog.
     */
    private String recipeName;

    /**
     * Adapter for placing ingredients into Ingredients RecyclerView
     */
    private IngredientsAdapter adapter;

    /**
     * List to house all of the ingredients and work with the Ingredients RecyclerView
     * We will add all new ingredients from this list to the database upon each
     * newly created recipe
     */
    private List<Ingredient> allIngredients;

    // TODO: Might not need the interface if I just wind up implementing all of the functionality in the Dialog.
    /**
     * This interface is implemented by the RecipeCreationFragment.
     */
    public interface RecipeDialogInterfaceListeners {
        public void finishRecipe();
    }

    private RecipeDialogInterfaceListeners callback;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setCancelable(false);

        try {
            callback = (RecipeDialogInterfaceListeners) getTargetFragment();
        } catch (ClassCastException e) {
            Log.e(TAG, "Calling fragment must implement the RecipeDialogInterfaceListener: " + e);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        initialize();

        Dialog createRecipeDialog = new AlertDialog.Builder(getActivity())
                .setView(newRecipeFormBinding.getRoot())
                .setPositiveButton("Finish", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callback.finishRecipe();
                    }
                })
                .setNegativeButton("Quit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                })
                .create();
        return createRecipeDialog;
    }

    private void initialize() {
        newRecipeFormBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.new_recipe_form, null, false);
        newRecipeFormBinding.tvNewRecipeName.setText(recipeName);

        // Click Listeners
        newRecipeFormBinding.btnAddIngredient.setOnClickListener(this);

        // Ingredient Measurement Spinner
        ArrayAdapter<CharSequence> spinIngredientMeasurementAdapter = ArrayAdapter.createFromResource(getContext(), R.array.Ingredient_Measurements, android.R.layout.simple_spinner_item);
        spinIngredientMeasurementAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        newRecipeFormBinding.spinMeasurement.setAdapter(spinIngredientMeasurementAdapter);
        newRecipeFormBinding.spinMeasurement.setSelection(0);
        newRecipeFormBinding.spinMeasurement.setOnItemSelectedListener(this);

        // Ingredient Category Spinner
        ArrayAdapter<CharSequence> spinIngredientCategoryAdapter = ArrayAdapter.createFromResource(getContext(), R.array.Ingredients_Category, android.R.layout.simple_spinner_item);
        spinIngredientCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        newRecipeFormBinding.spinCategory.setAdapter(spinIngredientCategoryAdapter);
        newRecipeFormBinding.spinCategory.setSelection(0);
        newRecipeFormBinding.spinCategory.setOnItemSelectedListener(this);

        // Ingredient RecyclerView
        allIngredients = new ArrayList<>();
        adapter = new IngredientsAdapter(getContext(), allIngredients);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        newRecipeFormBinding.rvIngredients.setLayoutManager(layoutManager);
        newRecipeFormBinding.rvIngredients.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddIngredient:
                handleAddIngredient();
                break;
        }
    }

    public void handleAddIngredient() {
        Log.i(TAG,"Add ingredient button clicked");

        if (newRecipeFormBinding.etIngrName.getText().toString().isEmpty() || newRecipeFormBinding.etQuantity.getText().toString().isEmpty()) {
            Log.d(TAG, "Either the ingredient or the quantity was empty.  Returning.");
            Toast.makeText(getContext(), "Name and quantity fields cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinMeasurement:
                break;
            case R.id.spinCategory:
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Do nothing
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }
}
