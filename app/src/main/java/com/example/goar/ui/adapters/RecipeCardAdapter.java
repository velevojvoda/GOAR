package com.example.goar.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goar.R;
import com.example.goar.data.model.Allergen;
import com.example.goar.data.model.Recipe;
import com.example.goar.data.model.RecipeIngredient;
import com.example.goar.databinding.ItemRecipeCardBinding;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * Shows recipes as cards with their basic information.
 * Can be reused anywhere a list of recipes is needed (home, search, profile...).
 */
public class RecipeCardAdapter extends RecyclerView.Adapter<RecipeCardAdapter.RecipeViewHolder> {

    public interface OnRecipeClickListener {
        void onRecipeClick(Recipe recipe);
    }

    private final List<Recipe> recipes = new ArrayList<>();
    private final OnRecipeClickListener listener;

    public RecipeCardAdapter(OnRecipeClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRecipeCardBinding binding = ItemRecipeCardBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new RecipeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        holder.bind(recipes.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public void setRecipes(List<Recipe> newRecipes) {
        recipes.clear();
        if (newRecipes != null) {
            recipes.addAll(newRecipes);
        }
        notifyDataSetChanged();
    }

    static class RecipeViewHolder extends RecyclerView.ViewHolder {
        private final ItemRecipeCardBinding binding;

        RecipeViewHolder(@NonNull ItemRecipeCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Recipe recipe, OnRecipeClickListener listener) {
            View root = binding.getRoot();

            binding.recipeName.setText(recipe.getName());
            binding.recipeAuthor.setText(root.getContext().getString(
                    R.string.recipe_author, recipe.getAuthor() != null ? recipe.getAuthor().getUsername() : "-"));

            binding.recipeTypes.setText(root.getContext().getString(R.string.recipe_types,
                    nameOrDash(recipe.getMealType()), nameOrDash(recipe.getDishType()), nameOrDash(recipe.getDietType())));

            binding.recipeTimeServings.setText(root.getContext().getString(R.string.recipe_time_servings,
                    recipe.getTotalTimeMinutes(), recipe.getServings()));

            if (recipe.getRatingCount() > 0) {
                binding.recipeRating.setText(root.getContext().getString(R.string.recipe_rating,
                        String.format(Locale.US, "%.1f", recipe.getAverageRating()), recipe.getRatingCount()));
            } else {
                binding.recipeRating.setText(R.string.recipe_no_ratings);
            }

            Set<String> allergens = allergensOf(recipe);
            if (allergens.isEmpty()) {
                binding.recipeAllergens.setText(R.string.recipe_no_allergens);
            } else {
                binding.recipeAllergens.setText(root.getContext().getString(
                        R.string.recipe_allergens, String.join(", ", allergens)));
            }

            // TODO: load recipe.getMainImageUrl() with Glide when recipes have images
            binding.recipeImage.setImageResource(R.drawable.ic_recipe_placeholder);

            root.setOnClickListener(v -> {
                if (listener != null) listener.onRecipeClick(recipe);
            });
        }

        private static String nameOrDash(Object type) {
            return type != null ? type.toString() : "-";
        }

        /** Allergens are calculated from the recipe ingredients (spec: entity 3). */
        private static Set<String> allergensOf(Recipe recipe) {
            Set<String> names = new LinkedHashSet<>();
            for (RecipeIngredient item : recipe.getIngredients()) {
                for (Allergen allergen : item.getIngredient().getAllergens()) {
                    names.add(allergen.getName());
                }
            }
            return names;
        }
    }
}
