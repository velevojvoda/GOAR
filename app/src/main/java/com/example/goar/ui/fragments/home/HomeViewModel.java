package com.example.goar.ui.fragments.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.goar.data.model.PantryItem;
import com.example.goar.data.model.Recipe;
import com.example.goar.data.model.RecipeIngredient;
import com.example.goar.data.repository.MockData;
import com.example.goar.data.repository.PantryRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<HomeSection> selectedSection = new MutableLiveData<>(HomeSection.POPULAR);
    private final MutableLiveData<List<Recipe>> recipes = new MutableLiveData<>(new ArrayList<>());

    public HomeViewModel() {
        loadRecipes();
    }

    public LiveData<HomeSection> getSelectedSection() {
        return selectedSection;
    }

    public LiveData<List<Recipe>> getRecipes() {
        return recipes;
    }

    public void selectSection(HomeSection section) {
        if (section == selectedSection.getValue()) return;
        selectedSection.setValue(section);
        loadRecipes();
    }

    /** Reload the current section, e.g. when the user comes back to the home page. */
    public void refresh() {
        loadRecipes();
    }

    private void loadRecipes() {
        HomeSection section = selectedSection.getValue();
        List<Recipe> all = MockData.getRecipes();
        List<Recipe> result;

        switch (section != null ? section : HomeSection.POPULAR) {
            case NEWEST:
                result = newest(all);
                break;
            case RECENTLY_VIEWED:
                result = byIds(RecentRecipesTracker.getRecentlyViewedIds());
                break;
            case RECENTLY_COOKED:
                result = byIds(RecentRecipesTracker.getRecentlyCookedIds());
                break;
            case CAN_MAKE:
                result = canMake(all, pantryIngredientIds());
                break;
            case POPULAR:
            default:
                result = popular(all);
                break;
        }
        recipes.setValue(result);
    }

    // TODO: when ratings get a date, use only ratings from the last month
    private List<Recipe> popular(List<Recipe> all) {
        List<Recipe> result = new ArrayList<>();
        for (Recipe recipe : all) {
            if (recipe.getRatingCount() > 0) {
                result.add(recipe);
            }
        }
        result.sort((a, b) -> Double.compare(b.getAverageRating(), a.getAverageRating()));
        return result;
    }

    private List<Recipe> newest(List<Recipe> all) {
        List<Recipe> result = new ArrayList<>(all);
        result.sort((a, b) -> {
            int byDate = b.getCreatedAt().compareTo(a.getCreatedAt());
            return byDate != 0 ? byDate : b.getId().compareTo(a.getId());
        });
        return result;
    }

    private List<Recipe> byIds(List<String> ids) {
        List<Recipe> result = new ArrayList<>();
        for (String id : ids) {
            Recipe recipe = MockData.getRecipeById(id);
            if (recipe != null) {
                result.add(recipe);
            }
        }
        return result;
    }

    /** Ids of ingredients the user has in his pantry (Student 2's PantryRepository). */
    private Set<String> pantryIngredientIds() {
        Set<String> ids = new HashSet<>();
        List<PantryItem> items = PantryRepository.getInstance().getItems().getValue();
        if (items != null) {
            for (PantryItem item : items) {
                ids.add(item.getIngredient().getId());
            }
        }
        return ids;
    }

    /**
     * Recipes for which the user has all ingredients.
     * Ingredients without quantity ("salt to taste") are not required.
     */
    private List<Recipe> canMake(List<Recipe> all, Set<String> pantryIds) {
        List<Recipe> result = new ArrayList<>();
        for (Recipe recipe : all) {
            boolean hasAll = true;
            for (RecipeIngredient item : recipe.getIngredients()) {
                boolean required = !item.getIngredient().isQuantityOptional();
                if (required && !pantryIds.contains(item.getIngredient().getId())) {
                    hasAll = false;
                    break;
                }
            }
            if (hasAll) {
                result.add(recipe);
            }
        }
        return result;
    }
}
