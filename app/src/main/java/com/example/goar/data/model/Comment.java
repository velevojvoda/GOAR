package com.example.goar.data.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Comment {
    private long id;
    private long recipeId;
    private User author;
    private String text;
    private List<String> imageUrls = new ArrayList<>();
    private LocalDateTime createdAt;
    private Long parentCommentId;

    public Comment(long id, long recipeId, User author, String text, LocalDateTime createdAt) {
        this.id = id;
        this.recipeId = recipeId;
        this.author = author;
        this.text = text;
        this.createdAt = createdAt;
    }

    public boolean isReply() { return parentCommentId != null; }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getRecipeId() { return recipeId; }
    public void setRecipeId(long recipeId) { this.recipeId = recipeId; }

    public User getAuthor() { return author; }
    public void setAuthor(User author) { this.author = author; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public List<String> getImageUrls() { return imageUrls; }
    public void setImageUrls(List<String> imageUrls) { this.imageUrls = imageUrls; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Long getParentCommentId() { return parentCommentId; }
    public void setParentCommentId(Long parentCommentId) { this.parentCommentId = parentCommentId; }
}
