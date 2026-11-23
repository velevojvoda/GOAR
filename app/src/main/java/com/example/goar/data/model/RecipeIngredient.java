package com.example.goar.data.model;

public class RecipeIngredient {
    private Ingredient ingredient;
    private Double quantity;
    private MeasurementUnit unit;

    public RecipeIngredient(Ingredient ingredient, Double quantity, MeasurementUnit unit) {
        this.ingredient = ingredient;
        this.quantity = quantity;
        this.unit = unit;
    }

    public Ingredient getIngredient() { return ingredient; }
    public void setIngredient(Ingredient ingredient) { this.ingredient = ingredient; }

    public Double getQuantity() { return quantity; }
    public void setQuantity(Double quantity) { this.quantity = quantity; }

    public MeasurementUnit getUnit() { return unit; }
    public void setUnit(MeasurementUnit unit) { this.unit = unit; }
}
