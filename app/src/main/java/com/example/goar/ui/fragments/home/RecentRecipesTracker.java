package com.example.goar.ui.fragments.home;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Remembers which recipes the user opened and for which recipes he started cooking mode.
 * <p>
 * Until we have a database this is kept in memory and filled with test data.
 * Recipe details and cooking mode (Student 1) should call {@link #markViewed(String)}
 * and {@link #markCooked(String)} when they are implemented.
 */
public final class RecentRecipesTracker {

    public static final int RECENTLY_VIEWED_DAYS = 7;

    private static final Map<String, LocalDateTime> viewed = new LinkedHashMap<>();
    private static final Map<String, LocalDateTime> cooked = new LinkedHashMap<>();

    static {
        // Test data: recipe ids from MockData
        LocalDateTime now = LocalDateTime.now();
        viewed.put("2", now.minusHours(3));
        viewed.put("3", now.minusDays(2));
        viewed.put("4", now.minusDays(10)); // older than 7 days, must not be shown
        cooked.put("1", now.minusDays(1));
        cooked.put("3", now.minusDays(5));
    }

    private RecentRecipesTracker() {
    }

    public static void markViewed(String recipeId) {
        viewed.remove(recipeId);
        viewed.put(recipeId, LocalDateTime.now());
    }

    public static void markCooked(String recipeId) {
        cooked.remove(recipeId);
        cooked.put(recipeId, LocalDateTime.now());
    }

    /** Ids of recipes viewed in the last 7 days, most recent first. */
    public static List<String> getRecentlyViewedIds() {
        LocalDateTime limit = LocalDateTime.now().minusDays(RECENTLY_VIEWED_DAYS);
        return idsAfter(viewed, limit);
    }

    /** Ids of recipes for which cooking mode was started, most recent first. */
    public static List<String> getRecentlyCookedIds() {
        return idsAfter(cooked, LocalDateTime.MIN);
    }

    private static List<String> idsAfter(Map<String, LocalDateTime> source, LocalDateTime limit) {
        List<Map.Entry<String, LocalDateTime>> entries = new ArrayList<>(source.entrySet());
        entries.sort((a, b) -> b.getValue().compareTo(a.getValue()));
        List<String> ids = new ArrayList<>();
        for (Map.Entry<String, LocalDateTime> entry : entries) {
            if (entry.getValue().isAfter(limit)) {
                ids.add(entry.getKey());
            }
        }
        return ids;
    }
}
