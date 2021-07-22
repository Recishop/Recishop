package com.example.recishop.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("ParseRecipe")
public class ParseRecipe extends ParseObject {

    public static final String KEY_RECIPE_NAME = "recipeName";
    public static final String KEY_RECIPE_INGREDIENTS = "recipeIngredients";
    public static final String KEY_RECIPE_OWNER = "recipeOwner";

    public ParseRecipe() {
        super();
    }

    public String getKeyRecipeName() { return getString(KEY_RECIPE_NAME); }

    public void setKeyRecipeName(String name) { put(KEY_RECIPE_NAME, name); }

    public String getKeyRecipeIngredients() { return getString(KEY_RECIPE_INGREDIENTS); }

    public void setKeyRecipeIngredients(String recipe) { put(KEY_RECIPE_INGREDIENTS, recipe); }

    public void setKeyRecipeOwner(ParseUser owner) { put(KEY_RECIPE_OWNER, owner); }

    public String getKeyRecipeOwner() { return getString(KEY_RECIPE_OWNER); }
}