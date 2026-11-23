package com.example.goar.data.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Ingredient {
    private long id;
    private String name;
    private IngredientCategory category;
    private MeasurementUnit defaultUnit;
    private boolean quantityOptional;
    private IngredientType type;
    private List<Allergen> allergens = new ArrayList<>();

    public Ingredient(long id, String name, IngredientCategory category,
                      MeasurementUnit defaultUnit, IngredientType type) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.defaultUnit = defaultUnit;
        this.type = type;
    }

    public boolean isBranded() { return type == IngredientType.BRANDED; }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public IngredientCategory getCategory() { return category; }
    public void setCategory(IngredientCategory category) { this.category = category; }

    public MeasurementUnit getDefaultUnit() { return defaultUnit; }
    public void setDefaultUnit(MeasurementUnit defaultUnit) { this.defaultUnit = defaultUnit; }

    public boolean isQuantityOptional() { return quantityOptional; }
    public void setQuantityOptional(boolean quantityOptional) { this.quantityOptional = quantityOptional; }

    public IngredientType getType() { return type; }
    public void setType(IngredientType type) { this.type = type; }

    public List<Allergen> getAllergens() { return allergens; }
    public void setAllergens(List<Allergen> allergens) { this.allergens = allergens; }

    @NonNull
    @Override
    public String toString() { return name; }
}
