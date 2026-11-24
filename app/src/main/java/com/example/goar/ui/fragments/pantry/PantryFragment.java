package com.example.goar.ui.fragments.pantry;

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
import com.example.goar.data.model.PantryItem;
import com.example.goar.databinding.FragmentPantryBinding;
import com.example.goar.ui.adapters.PantryAdapter;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class PantryFragment extends Fragment {

    private FragmentPantryBinding binding;
    private PantryViewModel viewModel;
    private PantryAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentPantryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(PantryViewModel.class);

        adapter = new PantryAdapter(new PantryAdapter.OnItemActionListener() {
            @Override
            public void onItemClick(PantryItem item) {
                openForm(item.getId());
            }

            @Override
            public void onDeleteClick(PantryItem item) {
                confirmDelete(item);
            }
        });
        binding.recyclerPantry.setAdapter(adapter);
        binding.fabAdd.setOnClickListener(v -> openForm(null));

        viewModel.getItems().observe(getViewLifecycleOwner(), items -> {
            adapter.setItems(items);
            binding.textEmpty.setVisibility(items.isEmpty() ? View.VISIBLE : View.GONE);
        });
    }

    private void openForm(String itemId) {
        Bundle args = new Bundle();
        args.putString(PantryItemFormFragment.ARG_ITEM_ID, itemId);
        NavHostFragment.findNavController(this).navigate(R.id.action_pantry_to_item_form, args);
    }

    private void confirmDelete(PantryItem item) {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle(getString(R.string.pantry_delete_title, item.getIngredient().getName()))
                .setMessage(R.string.pantry_delete_message)
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.pantry_delete, (dialog, which) -> viewModel.delete(item))
                .show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
