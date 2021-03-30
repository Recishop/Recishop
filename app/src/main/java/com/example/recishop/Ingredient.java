package com.example.recishop;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Ingredient")
public class Ingredient extends ParseObject {

    public static final String KEY_ITEM = "item";
    public static final String KEY_QUANTITY = "quantity";
    public static final String KEY_RECIPE = "recipe";
    public static final String KEY_MEASUREMENT = "measurement";

    public String getItem(){
        return getString(KEY_ITEM);
    }

    public void setItem(String item){
        put(KEY_ITEM, item);
    }

    public Double getQuantity(){
        return getDouble(KEY_QUANTITY);
    }

    public void setQuantity(Double quantity){
        put(KEY_QUANTITY, quantity);
    }

    public ParseObject getRecipe(){
        return getParseObject(KEY_RECIPE);
    }

    public void setRecipe(ParseObject recipe){
        put(KEY_RECIPE, recipe);
    }

    public String getMeasurement(){
        return getString(KEY_MEASUREMENT);
    }

    public void setMeasurement(String measurement){
        put(KEY_MEASUREMENT, measurement);
    }
}
