package com.example.recishop.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("ParseIngredient")
public class ParseIngredient extends ParseObject {

    public static final String KEY_INGREDIENT_NAME = "ingredientName";

    public String getKeyIngredientName() { return getString(KEY_INGREDIENT_NAME); }

    public void setKeyIngredientName(String name) { put(KEY_INGREDIENT_NAME, name); }
}