package com.example.goar.data.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Recipe {
    private long id;
    private String name;
    private User author;
    private MealType mealType;
    private DishType dishType;
    private DietType dietType;
    private int servings;
    private int totalTimeMinutes;
    private String mainImageUrl;
    private double averageRating;
    private int ratingCount;

    private List<String> additionalImageUrls = new ArrayList<>();
    private String videoUrl;
    private List<RecipeIngredient> ingredients = new ArrayList<>();
    private List<RecipeStep> steps = new ArrayList<>();
    private Origin origin;
    private String story;
    private String authorRegion;
    private LocalDateTime createdAt;

    private Long originalRecipeId;
    private String modificationNote;

    public Recipe(long id, String name, User author, MealType mealType, DishType dishType,
                  DietType dietType, int servings, int totalTimeMinutes) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.mealType = mealType;
        this.dishType = dishType;
        this.dietType = dietType;
        this.servings = servings;
        this.totalTimeMinutes = totalTimeMinutes;
        this.authorRegion = author != null ? author.getRegion() : null;
        this.createdAt = LocalDateTime.now();
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public User getAuthor() { return author; }
    public void setAuthor(User author) { this.author = author; }

    public MealType getMealType() { return mealType; }
    public void setMealType(MealType mealType) { this.mealType = mealType; }

    public DishType getDishType() { return dishType; }
    public void setDishType(DishType dishType) { this.dishType = dishType; }

    public DietType getDietType() { return dietType; }
    public void setDietType(DietType dietType) { this.dietType = dietType; }

    public int getServings() { return servings; }
    public void setServings(int servings) { this.servings = servings; }

    public int getTotalTimeMinutes() { return totalTimeMinutes; }
    public void setTotalTimeMinutes(int totalTimeMinutes) { this.totalTimeMinutes = totalTimeMinutes; }

    public String getMainImageUrl() { return mainImageUrl; }
    public void setMainImageUrl(String mainImageUrl) { this.mainImageUrl = mainImageUrl; }

    public double getAverageRating() { return averageRating; }
    public void setAverageRating(double averageRating) { this.averageRating = averageRating; }

    public int getRatingCount() { return ratingCount; }
    public void setRatingCount(int ratingCount) { this.ratingCount = ratingCount; }

    public List<String> getAdditionalImageUrls() { return additionalImageUrls; }
    public void setAdditionalImageUrls(List<String> additionalImageUrls) { this.additionalImageUrls = additionalImageUrls; }

    public String getVideoUrl() { return videoUrl; }
    public void setVideoUrl(String videoUrl) { this.videoUrl = videoUrl; }

    public List<RecipeIngredient> getIngredients() { return ingredients; }
    public void setIngredients(List<RecipeIngredient> ingredients) { this.ingredients = ingredients; }

    public List<RecipeStep> getSteps() { return steps; }
    public void setSteps(List<RecipeStep> steps) { this.steps = steps; }

    public Origin getOrigin() { return origin; }
    public void setOrigin(Origin origin) { this.origin = origin; }

    public String getStory() { return story; }
    public void setStory(String story) { this.story = story; }

    public String getAuthorRegion() { return authorRegion; }
    public void setAuthorRegion(String authorRegion) { this.authorRegion = authorRegion; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Long getOriginalRecipeId() { return originalRecipeId; }
    public void setOriginalRecipeId(Long originalRecipeId) { this.originalRecipeId = originalRecipeId; }

    public String getModificationNote() { return modificationNote; }
    public void setModificationNote(String modificationNote) { this.modificationNote = modificationNote; }
}
