package com.example.goar.data.model;

public class Rating {
    private long recipeId;
    private long userId;
    private int value;

    public Rating(long recipeId, long userId, int value) {
        this.recipeId = recipeId;
        this.userId = userId;
        this.value = value;
    }

    public long getRecipeId() { return recipeId; }
    public void setRecipeId(long recipeId) { this.recipeId = recipeId; }

    public long getUserId() { return userId; }
    public void setUserId(long userId) { this.userId = userId; }

    public int getValue() { return value; }
    public void setValue(int value) { this.value = value; }
}
