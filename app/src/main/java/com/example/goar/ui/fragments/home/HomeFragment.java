package com.example.goar.ui.fragments.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.goar.R;
import com.example.goar.data.model.Recipe;
import com.example.goar.databinding.FragmentHomeBinding;
import com.example.goar.ui.adapters.RecipeCardAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;
    private RecipeCardAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        setupRecyclerView();
        setupSectionChips();

        viewModel.getSelectedSection().observe(getViewLifecycleOwner(), this::showSection);
        viewModel.getRecipes().observe(getViewLifecycleOwner(), this::showRecipes);

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Recently viewed / cooked may have changed while the user was on another screen
        viewModel.refresh();
    }

    private void setupRecyclerView() {
        adapter = new RecipeCardAdapter(this::openRecipe);
        binding.recipesRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recipesRecycler.setAdapter(adapter);
    }

    private void setupSectionChips() {
        binding.sectionChips.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (checkedIds.isEmpty()) return;
            viewModel.selectSection(sectionForChip(checkedIds.get(0)));
        });
    }

    private void showSection(HomeSection section) {
        int chipId = chipForSection(section);
        if (binding.sectionChips.getCheckedChipId() != chipId) {
            binding.sectionChips.check(chipId);
        }
        binding.sectionDescription.setText(descriptionForSection(section));
    }

    private void showRecipes(List<Recipe> recipes) {
        adapter.setRecipes(recipes);
        boolean empty = recipes == null || recipes.isEmpty();
        binding.emptyText.setVisibility(empty ? View.VISIBLE : View.GONE);
        binding.recipesRecycler.setVisibility(empty ? View.GONE : View.VISIBLE);
    }

    private void openRecipe(Recipe recipe) {
        RecentRecipesTracker.markViewed(recipe.getId());
        // TODO: navigate to recipe details when Student 1 adds that screen
        Snackbar.make(binding.getRoot(), getString(R.string.home_recipe_details_soon, recipe.getName()),
                Snackbar.LENGTH_SHORT).show();
    }

    private HomeSection sectionForChip(int chipId) {
        if (chipId == R.id.chip_newest) return HomeSection.NEWEST;
        if (chipId == R.id.chip_recently_viewed) return HomeSection.RECENTLY_VIEWED;
        if (chipId == R.id.chip_recently_cooked) return HomeSection.RECENTLY_COOKED;
        if (chipId == R.id.chip_can_make) return HomeSection.CAN_MAKE;
        return HomeSection.POPULAR;
    }

    private int chipForSection(HomeSection section) {
        switch (section) {
            case NEWEST: return R.id.chip_newest;
            case RECENTLY_VIEWED: return R.id.chip_recently_viewed;
            case RECENTLY_COOKED: return R.id.chip_recently_cooked;
            case CAN_MAKE: return R.id.chip_can_make;
            case POPULAR:
            default: return R.id.chip_popular;
        }
    }

    private int descriptionForSection(HomeSection section) {
        switch (section) {
            case NEWEST: return R.string.home_desc_newest;
            case RECENTLY_VIEWED: return R.string.home_desc_recently_viewed;
            case RECENTLY_COOKED: return R.string.home_desc_recently_cooked;
            case CAN_MAKE: return R.string.home_desc_can_make;
            case POPULAR:
            default: return R.string.home_desc_popular;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
