package com.example.goar.ui.fragments.home;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Remembers which recipes the user opened and for which recipes he started cooking mode.
 * <p>
 * Until we have a database this is kept in memory and filled with test data.
 * Recipe details and cooking mode (Student 1) should call {@link #markViewed(long)}
 * and {@link #markCooked(long)} when they are implemented.
 */
public final class RecentRecipesTracker {

    public static final int RECENTLY_VIEWED_DAYS = 7;

    private static final Map<Long, LocalDateTime> viewed = new LinkedHashMap<>();
    private static final Map<Long, LocalDateTime> cooked = new LinkedHashMap<>();
    private static final Set<Long> pantryIngredientIds = new HashSet<>();

    static {
        // Test data: recipe ids from MockData
        LocalDateTime now = LocalDateTime.now();
        viewed.put(2L, now.minusHours(3));
        viewed.put(3L, now.minusDays(2));
        viewed.put(4L, now.minusDays(10)); // older than 7 days, must not be shown
        cooked.put(1L, now.minusDays(1));
        cooked.put(3L, now.minusDays(5));

        // Test data: ingredient ids the user has in his pantry (Student 2 will provide the real list)
        pantryIngredientIds.add(1L);  // Flour
        pantryIngredientIds.add(2L);  // Milk
        pantryIngredientIds.add(3L);  // Egg
        pantryIngredientIds.add(10L); // Salmon
    }

    private RecentRecipesTracker() {
    }

    public static void markViewed(long recipeId) {
        viewed.remove(recipeId);
        viewed.put(recipeId, LocalDateTime.now());
    }

    public static void markCooked(long recipeId) {
        cooked.remove(recipeId);
        cooked.put(recipeId, LocalDateTime.now());
    }

    /** Ids of recipes viewed in the last 7 days, most recent first. */
    public static List<Long> getRecentlyViewedIds() {
        LocalDateTime limit = LocalDateTime.now().minusDays(RECENTLY_VIEWED_DAYS);
        return idsAfter(viewed, limit);
    }

    /** Ids of recipes for which cooking mode was started, most recent first. */
    public static List<Long> getRecentlyCookedIds() {
        return idsAfter(cooked, LocalDateTime.MIN);
    }

    public static Set<Long> getPantryIngredientIds() {
        return new HashSet<>(pantryIngredientIds);
    }

    private static List<Long> idsAfter(Map<Long, LocalDateTime> source, LocalDateTime limit) {
        List<Map.Entry<Long, LocalDateTime>> entries = new ArrayList<>(source.entrySet());
        entries.sort((a, b) -> b.getValue().compareTo(a.getValue()));
        List<Long> ids = new ArrayList<>();
        for (Map.Entry<Long, LocalDateTime> entry : entries) {
            if (entry.getValue().isAfter(limit)) {
                ids.add(entry.getKey());
            }
        }
        return ids;
    }
}
