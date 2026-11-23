package com.example.goar.data.model;

public class CategoryRequest {
    private long id;
    private String ingredientName;
    private String proposedName;
    private String explanation;
    private User requester;
    private RequestStatus status;

    public CategoryRequest(long id, String ingredientName, String proposedName,
                           String explanation, User requester) {
        this.id = id;
        this.ingredientName = ingredientName;
        this.proposedName = proposedName;
        this.explanation = explanation;
        this.requester = requester;
        this.status = RequestStatus.PENDING;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getIngredientName() { return ingredientName; }
    public void setIngredientName(String ingredientName) { this.ingredientName = ingredientName; }

    public String getProposedName() { return proposedName; }
    public void setProposedName(String proposedName) { this.proposedName = proposedName; }

    public String getExplanation() { return explanation; }
    public void setExplanation(String explanation) { this.explanation = explanation; }

    public User getRequester() { return requester; }
    public void setRequester(User requester) { this.requester = requester; }

    public RequestStatus getStatus() { return status; }
    public void setStatus(RequestStatus status) { this.status = status; }
}
