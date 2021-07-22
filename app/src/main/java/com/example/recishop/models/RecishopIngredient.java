package com.example.recishop.models;

/**
 * Recishop ingredients are ingredient objects that contain meta data about
 * ParseIngredients
 *
 * ParseIngredient
 * Quantity
 * Measurement
 * Category
 *
 * @author tallt
 */
public class RecishopIngredient {

    /**
     * Ingredient name
     */
    private String ingredientName;

    /**
     * Quantity of ingredient needed
     */
    private Double quantity;

    /**
     * Measurement of the quantity
     */
    private IngredientMeasurement ingredientMeasurement;

    /**
     * The classification of the ingredient
     */
    private ItemCategory itemCategory;

    public RecishopIngredient(String ingredientName, Double quantity, IngredientMeasurement ingredientMeasurement, ItemCategory itemCategory) {
        this.ingredientName = ingredientName;
        this.quantity = quantity;
        this.ingredientMeasurement = ingredientMeasurement;
        this.itemCategory = itemCategory;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public IngredientMeasurement getIngredientMeasurement() {
        return ingredientMeasurement;
    }

    public void setIngredientMeasurement(IngredientMeasurement ingredientMeasurement) {
        this.ingredientMeasurement = ingredientMeasurement;
    }

    public ItemCategory getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(ItemCategory itemCategory) {
        this.itemCategory = itemCategory;
    }
}
