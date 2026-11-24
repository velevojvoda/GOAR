package com.example.goar.data.model;

public class Rating {
    private String recipeId;
    private String userId;
    private int value;

    public Rating(String recipeId, String userId, int value) {
        this.recipeId = recipeId;
        this.userId = userId;
        this.value = value;
    }

    public String getRecipeId() { return recipeId; }
    public void setRecipeId(String recipeId) { this.recipeId = recipeId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public int getValue() { return value; }
    public void setValue(int value) { this.value = value; }
}
