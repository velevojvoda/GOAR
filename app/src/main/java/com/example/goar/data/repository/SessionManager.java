package com.example.goar.data.repository;

import com.example.goar.data.model.User;

public final class SessionManager {
    private static User currentUser = MockData.getCurrentUser();

    private SessionManager() {
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static boolean isLoggedIn() {
        return currentUser != null;
    }

    public static boolean isAdmin() {
        return currentUser != null && currentUser.isAdmin();
    }

    public static void logout() {
        currentUser = null;
    }
}
