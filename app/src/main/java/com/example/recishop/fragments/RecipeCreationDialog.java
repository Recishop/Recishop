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
import com.example.recishop.models.IngredientMeasurement;
import com.example.recishop.models.ItemCategory;
import com.example.recishop.models.ParseIngredient;
import com.example.recishop.models.RecishopIngredient;

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
    private List<RecishopIngredient> allIngredients;

    /**
     * This interface is implemented by the RecipeCreationFragment.
     */
    public interface RecipeDialogInterfaceListeners {
        public void finishRecipe(List<RecishopIngredient> recipeList);
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
                        callback.finishRecipe(allIngredients);
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

        // ParseIngredient Measurement Spinner
        ArrayAdapter<IngredientMeasurement> spinIngredientMeasurementAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, IngredientMeasurement.values());
        spinIngredientMeasurementAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        newRecipeFormBinding.spinMeasurement.setAdapter(spinIngredientMeasurementAdapter);
        newRecipeFormBinding.spinMeasurement.setSelection(0);
        newRecipeFormBinding.spinMeasurement.setOnItemSelectedListener(this);

        // ParseIngredient Category Spinner
        ArrayAdapter<ItemCategory> spinIngredientCategoryAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, ItemCategory.values());
        spinIngredientCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        newRecipeFormBinding.spinCategory.setAdapter(spinIngredientCategoryAdapter);
        newRecipeFormBinding.spinCategory.setSelection(0);
        newRecipeFormBinding.spinCategory.setOnItemSelectedListener(this);

        // ParseIngredient RecyclerView
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

        String recipeName = newRecipeFormBinding.etIngrName.getText().toString();
        Double quantity = Double.valueOf(newRecipeFormBinding.etQuantity.getText().toString());
        IngredientMeasurement ingredientMeasurement = (IngredientMeasurement) newRecipeFormBinding.spinMeasurement.getAdapter().getItem(newRecipeFormBinding.spinMeasurement.getSelectedItemPosition());
        ItemCategory itemCategory = (ItemCategory) newRecipeFormBinding.spinCategory.getAdapter().getItem(newRecipeFormBinding.spinCategory.getSelectedItemPosition());

        RecishopIngredient recishopIngredient = new RecishopIngredient(recipeName, quantity, ingredientMeasurement, itemCategory);

        allIngredients.add(recishopIngredient);
        adapter.notifyDataSetChanged();

        newRecipeFormBinding.etIngrName.setText("");
        newRecipeFormBinding.etQuantity.setText("");
        newRecipeFormBinding.etIngrName.requestFocus();
        newRecipeFormBinding.spinMeasurement.setSelection(0);
        newRecipeFormBinding.spinCategory.setSelection(0);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // Currently don't have to do anything - All we want to do is scrape the current enum from it
        // at the time the user presses the "Finish" button

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
