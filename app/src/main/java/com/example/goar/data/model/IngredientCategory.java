package com.example.goar.data.model;

import androidx.annotation.NonNull;

public class IngredientCategory {
    private long id;
    private String name;

    public IngredientCategory(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @NonNull
    @Override
    public String toString() { return name; }
}
