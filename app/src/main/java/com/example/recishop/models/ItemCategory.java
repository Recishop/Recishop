package com.example.recishop.models;

import androidx.annotation.NonNull;

/**
 * Category enum to be used in Recishop Ingredient
 * This is also used for user-added items to the shopping list
 */
public enum ItemCategory {
    OTHER ("Other"),
    PRODUCE ("Produce"),
    COLD ("Fridged");

    public String label;

    ItemCategory(String label) {
        this.label = label;
    }

    @NonNull
    @Override
    public String toString() {
        return label;
    }
}
