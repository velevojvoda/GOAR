package com.example.goar.ui.fragments.shopping;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.goar.R;
import com.example.goar.databinding.FragmentShoppingListsBinding;
import com.example.goar.ui.adapters.ShoppingListAdapter;
import com.google.android.material.tabs.TabLayout;

public class ShoppingListsFragment extends Fragment {

    private FragmentShoppingListsBinding binding;
    private ShoppingListsViewModel viewModel;
    private ShoppingListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentShoppingListsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ShoppingListsViewModel.class);

        adapter = new ShoppingListAdapter(list -> {
        });
        binding.recyclerLists.setAdapter(adapter);
        binding.fabAdd.setOnClickListener(v -> NavHostFragment.findNavController(this)
                .navigate(R.id.action_shopping_to_list_form));

        setupTabs();

        viewModel.getVisibleLists().observe(getViewLifecycleOwner(), lists -> {
            adapter.setLists(lists);
            binding.textEmpty.setVisibility(lists.isEmpty() ? View.VISIBLE : View.GONE);
        });
    }

    private void setupTabs() {
        Integer selectedTab = viewModel.getSelectedTab().getValue();
        TabLayout.Tab tab = binding.tabLayout.getTabAt(selectedTab != null ? selectedTab : 0);
        if (tab != null) tab.select();

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewModel.selectTab(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        viewModel.getSelectedTab().observe(getViewLifecycleOwner(), position -> {
            boolean isActiveTab = position == ShoppingListsViewModel.TAB_ACTIVE;
            binding.textEmpty.setText(isActiveTab
                    ? R.string.shopping_empty_active
                    : R.string.shopping_empty_archive);
            if (isActiveTab) {
                binding.fabAdd.show();
            } else {
                binding.fabAdd.hide();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
