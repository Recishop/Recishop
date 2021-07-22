package com.example.recishop.models;

import androidx.annotation.NonNull;

/**
 * Measurement enum to be used in Recishop Ingredient
 */
public enum IngredientMeasurement {
    UNITS ("Units"),
    POUNDS ("lbs"),
    OUNCES ("Oz"),
    CUPS ("Cups"),
    TABLESPOONS ("Tbsp"),
    TEASPOONS ("Tsp");

    public String label;

    IngredientMeasurement(String label) {
        this.label = label;
    }

    @NonNull
    @Override
    public String toString() {
        return label;
    }

}
