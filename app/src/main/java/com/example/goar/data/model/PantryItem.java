package com.example.goar.data.model;

import java.time.LocalDate;

public class PantryItem {
    private long id;
    private Ingredient ingredient;
    private String brand;
    private Double quantity;
    private MeasurementUnit unit;
    private LocalDate expiryDate;
    private String barcode;

    public PantryItem(long id, Ingredient ingredient) {
        this.id = id;
        this.ingredient = ingredient;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public Ingredient getIngredient() { return ingredient; }
    public void setIngredient(Ingredient ingredient) { this.ingredient = ingredient; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public Double getQuantity() { return quantity; }
    public void setQuantity(Double quantity) { this.quantity = quantity; }

    public MeasurementUnit getUnit() { return unit; }
    public void setUnit(MeasurementUnit unit) { this.unit = unit; }

    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }

    public String getBarcode() { return barcode; }
    public void setBarcode(String barcode) { this.barcode = barcode; }
}
