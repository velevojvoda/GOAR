package com.example.goar.data.model;

public class User {
    private String id;
    private String username;
    private String email;
    private String profileImageUrl;
    private String region;
    private String bio;
    private Role role;

    public User(String id, String username, String email, String region, Role role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.region = region;
        this.role = role;
    }

    public boolean isAdmin() { return role == Role.ADMIN; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsername() { return username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getProfileImageUrl() { return profileImageUrl; }
    public void setProfileImageUrl(String profileImageUrl) { this.profileImageUrl = profileImageUrl; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}
