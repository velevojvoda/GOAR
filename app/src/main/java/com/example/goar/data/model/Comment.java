package com.example.goar.data.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Comment {
    private String id;
    private String recipeId;
    private User author;
    private String text;
    private List<String> imageUrls = new ArrayList<>();
    private LocalDateTime createdAt;
    private String parentCommentId;

    public Comment(String id, String recipeId, User author, String text, LocalDateTime createdAt) {
        this.id = id;
        this.recipeId = recipeId;
        this.author = author;
        this.text = text;
        this.createdAt = createdAt;
    }

    public boolean isReply() { return parentCommentId != null; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getRecipeId() { return recipeId; }
    public void setRecipeId(String recipeId) { this.recipeId = recipeId; }

    public User getAuthor() { return author; }
    public void setAuthor(User author) { this.author = author; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public List<String> getImageUrls() { return imageUrls; }
    public void setImageUrls(List<String> imageUrls) { this.imageUrls = imageUrls; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public String getParentCommentId() { return parentCommentId; }
    public void setParentCommentId(String parentCommentId) { this.parentCommentId = parentCommentId; }
}
