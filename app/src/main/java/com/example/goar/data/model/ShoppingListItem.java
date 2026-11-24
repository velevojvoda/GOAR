package com.example.goar.data.model;

public class ShoppingListItem {
    private String id;
    private Ingredient ingredient;
    private Double quantity;
    private MeasurementUnit unit;
    private boolean purchased;

    public ShoppingListItem(String id, Ingredient ingredient, Double quantity, MeasurementUnit unit) {
        this.id = id;
        this.ingredient = ingredient;
        this.quantity = quantity;
        this.unit = unit;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Ingredient getIngredient() { return ingredient; }
    public void setIngredient(Ingredient ingredient) { this.ingredient = ingredient; }

    public Double getQuantity() { return quantity; }
    public void setQuantity(Double quantity) { this.quantity = quantity; }

    public MeasurementUnit getUnit() { return unit; }
    public void setUnit(MeasurementUnit unit) { this.unit = unit; }

    public boolean isPurchased() { return purchased; }
    public void setPurchased(boolean purchased) { this.purchased = purchased; }
}
