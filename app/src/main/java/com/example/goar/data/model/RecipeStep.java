package com.example.goar.data.model;

import java.util.ArrayList;
import java.util.List;

public class RecipeStep {
    private int order;
    private String title;
    private String description;
    private List<String> imageUrls = new ArrayList<>();
    private PreparationType preparationType;
    private Integer durationMinutes;

    public RecipeStep(int order, String title, String description,
                      PreparationType preparationType, Integer durationMinutes) {
        this.order = order;
        this.title = title;
        this.description = description;
        this.preparationType = preparationType;
        this.durationMinutes = durationMinutes;
    }

    public int getOrder() { return order; }
    public void setOrder(int order) { this.order = order; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<String> getImageUrls() { return imageUrls; }
    public void setImageUrls(List<String> imageUrls) { this.imageUrls = imageUrls; }

    public PreparationType getPreparationType() { return preparationType; }
    public void setPreparationType(PreparationType preparationType) { this.preparationType = preparationType; }

    public Integer getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; }
}
