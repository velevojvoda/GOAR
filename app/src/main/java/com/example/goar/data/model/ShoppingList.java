package com.example.goar.data.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ShoppingList {
    private long id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime plannedDateTime;
    private boolean permanent;
    private boolean archived;
    private List<ShoppingListItem> items = new ArrayList<>();

    public ShoppingList(long id, String name, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
    }

    public boolean isCompleted() {
        if (items.isEmpty()) return false;
        for (ShoppingListItem item : items) {
            if (!item.isPurchased()) return false;
        }
        return true;
    }

    public int getPurchasedCount() {
        int count = 0;
        for (ShoppingListItem item : items) {
            if (item.isPurchased()) count++;
        }
        return count;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getPlannedDateTime() { return plannedDateTime; }
    public void setPlannedDateTime(LocalDateTime plannedDateTime) { this.plannedDateTime = plannedDateTime; }

    public boolean isPermanent() { return permanent; }
    public void setPermanent(boolean permanent) { this.permanent = permanent; }

    public boolean isArchived() { return archived; }
    public void setArchived(boolean archived) { this.archived = archived; }

    public List<ShoppingListItem> getItems() { return items; }
    public void setItems(List<ShoppingListItem> items) { this.items = items; }
}
