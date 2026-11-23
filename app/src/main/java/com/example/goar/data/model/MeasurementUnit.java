package com.example.goar.data.model;

public enum MeasurementUnit {
    GRAM("g"),
    KILOGRAM("kg"),
    MILLILITER("ml"),
    LITER("l"),
    PIECE("pcs"),
    TEASPOON("tsp"),
    TABLESPOON("tbsp");

    private final String symbol;

    MeasurementUnit(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() { return symbol; }
}
