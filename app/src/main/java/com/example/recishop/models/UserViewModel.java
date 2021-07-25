package com.example.recishop.models;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.recishop.MainActivity;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * UserViewModel contains all model-related information and is preserved
 * across UI/Fragment changes.
 *
 * @author tallt
 */
public class UserViewModel extends ViewModel {
    private static final String TAG = UserViewModel.class.getSimpleName();

    /**
     * The shopping list that contains everything the user wants to
     * put on their shopping list.  Can be populated via recipes and manually
     */
    private MutableLiveData<List<String>> shoppingList = new MutableLiveData<>();

    /**
     * List of known ingredients to the user.  Load this list upon user login
     */
    private List<String> knownIngredients = new ArrayList<>();

    public UserViewModel() {
        // Could consider doing database work here.  Would need to pass in data then?
    }

    public LiveData<List<String>> getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(List<String> list) {
        shoppingList.setValue(list);
    }

    public void addRecipeIngredientsToShoppingList(Recipe recipe) {
//        getIngredientsFromRecipe(recipe);
    }

    public void addCustomItemToShoppingList(String item) {
        List<String> temp = (shoppingList.getValue() == null) ? new ArrayList<>() : shoppingList.getValue();
        temp.add(item);
        shoppingList.setValue(temp);
    }

//    private void getIngredientsFromRecipe(Recipe recipe) {
//        Log.i(TAG, String.format("UserViewModel) Fetching ingredients from recipe [%s]", recipe.getName()));
//        ParseQuery<ParseIngredient> ingredientParseQuery = ParseQuery.getQuery(ParseIngredient.class);
//
//        // Filter for only ingredients associated with the given recipe
//        ingredientParseQuery.include(ParseIngredient.KEY_RECIPE);
//        ingredientParseQuery.whereEqualTo(ParseIngredient.KEY_RECIPE, recipe);
//
//        ingredientParseQuery.findInBackground(new FindCallback<ParseIngredient>() {
//            @Override
//            public void done(List<ParseIngredient> objects, ParseException e) {
//                if (e == null) {
//                    Log.i(TAG, String.format("Successfully pulled ingredients for [%s]", recipe.getName()));
//                    List<String> temp = (shoppingList.getValue() == null) ? new ArrayList<>() : shoppingList.getValue();
//                    for (int i = 0; i < objects.size(); i++) {
//                        temp.add(objects.get(i).getItem());
//                    }
//                    shoppingList.setValue(temp);
//                } else {
//                    Log.d(TAG, String.format("Error fetching ingredients for [%s]", recipe.getName()) + e);
//                    return;
//                }
//            }
//        });
//    }

    public void removeItemFromShoppingList(int position) {
        if (shoppingList.getValue() == null) {
            Log.i(TAG, "Shopping list was empty.  Nothing to remove");
        } else {
            Log.i(TAG, "Removed item " + shoppingList.getValue().get(position));
            List<String> temp = shoppingList.getValue();
            temp.remove(position);
            shoppingList.setValue(temp);
        }
    }

    public List<String> getKnownIngredients() {
        return knownIngredients;
    }

    public void setKnownIngredients(List<String> knownIngredients) {
        this.knownIngredients = knownIngredients;
    }
}
