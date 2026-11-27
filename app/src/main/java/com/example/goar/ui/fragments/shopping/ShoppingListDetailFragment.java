package com.example.goar.ui.fragments.shopping;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import com.example.goar.R;
import com.example.goar.data.model.ShoppingList;
import com.example.goar.data.model.ShoppingListItem;
import com.example.goar.databinding.DialogShoppingItemBinding;
import com.example.goar.databinding.FragmentShoppingListDetailBinding;
import com.example.goar.ui.adapters.ShoppingListItemAdapter;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

public class ShoppingListDetailFragment extends Fragment {

    public static final String ARG_LIST_ID = "listId";

    private FragmentShoppingListDetailBinding binding;
    private ShoppingListDetailViewModel viewModel;
    private ShoppingListItemAdapter adapter;
    private ShoppingList currentList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentShoppingListDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ShoppingListDetailViewModel.class);
        viewModel.setListId(getArguments() != null ? getArguments().getString(ARG_LIST_ID) : null);

        adapter = new ShoppingListItemAdapter(new ShoppingListItemAdapter.OnItemActionListener() {
            @Override
            public void onPurchasedChanged(ShoppingListItem item, boolean purchased) {
                if (viewModel.setPurchased(item, purchased)) {
                    showMessage(R.string.shopping_auto_archived);
                }
            }

            @Override
            public void onItemClick(ShoppingListItem item) {
                showItemDialog(item);
            }

            @Override
            public void onDeleteClick(ShoppingListItem item) {
                viewModel.deleteItem(item);
            }
        });
        binding.recyclerItems.setAdapter(adapter);
        binding.buttonMarkAll.setOnClickListener(v -> {
            if (viewModel.markAllPurchased()) {
                showMessage(R.string.shopping_auto_archived);
            }
        });
        binding.fabAddItem.setOnClickListener(v -> showItemDialog(null));

        setupMenu();
        viewModel.getList().observe(getViewLifecycleOwner(), this::showList);
    }

    private void setupMenu() {
        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.shopping_list_detail_menu, menu);
            }

            @Override
            public void onPrepareMenu(@NonNull Menu menu) {
                boolean archived = currentList != null && currentList.isArchived();
                menu.findItem(R.id.action_archive).setVisible(currentList != null && !archived);
                menu.findItem(R.id.action_reactivate).setVisible(archived);
                menu.findItem(R.id.action_copy).setVisible(archived);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.action_archive) {
                    viewModel.archive();
                    showMessage(R.string.shopping_archived);
                    return true;
                } else if (id == R.id.action_reactivate) {
                    viewModel.reactivate();
                    showMessage(R.string.shopping_reactivated);
                    return true;
                } else if (id == R.id.action_copy) {
                    openCopy(viewModel.createCopy());
                    return true;
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }

    private void openCopy(String newListId) {
        if (newListId == null) return;
        Bundle args = new Bundle();
        args.putString(ARG_LIST_ID, newListId);
        NavOptions options = new NavOptions.Builder()
                .setPopUpTo(R.id.nav_shopping_list_detail, true)
                .build();
        NavHostFragment.findNavController(this).navigate(R.id.nav_shopping_list_detail, args, options);
        showMessage(R.string.shopping_copied);
    }

    private void showMessage(int messageRes) {
        Snackbar.make(binding.getRoot(), messageRes, Snackbar.LENGTH_LONG).show();
    }

    private void showList(ShoppingList list) {
        if (list == null) return;
        currentList = list;
        requireActivity().invalidateMenu();

        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) actionBar.setTitle(list.getName());

        boolean editable = !list.isArchived();
        adapter.setItems(list.getItems(), editable);

        binding.textProgress.setText(getString(R.string.shopping_progress,
                list.getPurchasedCount(), list.getItems().size()));
        binding.textArchived.setVisibility(list.isArchived() ? View.VISIBLE : View.GONE);
        binding.buttonMarkAll.setVisibility(editable && !list.isCompleted() ? View.VISIBLE : View.GONE);
        binding.textEmpty.setVisibility(list.getItems().isEmpty() ? View.VISIBLE : View.GONE);
        if (editable) {
            binding.fabAddItem.show();
        } else {
            binding.fabAddItem.hide();
        }
    }

    private void showItemDialog(@Nullable ShoppingListItem item) {
        DialogShoppingItemBinding dialogBinding = DialogShoppingItemBinding.inflate(getLayoutInflater());

        ArrayAdapter<String> ingredientAdapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_list_item_1, viewModel.getIngredientNames());
        dialogBinding.inputIngredient.setAdapter(ingredientAdapter);
        dialogBinding.inputUnit.setSimpleItems(viewModel.getUnitSymbols());
        dialogBinding.inputIngredient.setOnItemClickListener((parent, v, position, id) -> {
            String unit = viewModel.getDefaultUnitSymbol((String) parent.getItemAtPosition(position));
            if (unit != null && textOf(dialogBinding.inputUnit.getText()).isEmpty()) {
                dialogBinding.inputUnit.setText(unit, false);
            }
        });

        if (item != null) {
            dialogBinding.inputIngredient.setText(item.getIngredient().getName(), false);
            dialogBinding.layoutIngredient.setEnabled(false);
            if (item.getQuantity() != null) {
                dialogBinding.inputQuantity.setText(formatQuantity(item.getQuantity()));
            }
            if (item.getUnit() != null) {
                dialogBinding.inputUnit.setText(item.getUnit().getSymbol(), false);
            }
        }

        AlertDialog dialog = new MaterialAlertDialogBuilder(requireContext())
                .setTitle(item == null ? R.string.shopping_add_item : R.string.shopping_edit_item)
                .setView(dialogBinding.getRoot())
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.shopping_save, null)
                .create();

        dialog.setOnShowListener(d -> dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            dialogBinding.layoutIngredient.setError(null);
            dialogBinding.layoutQuantity.setError(null);

            ShoppingListDetailViewModel.ItemResult result = viewModel.saveItem(
                    item != null ? item.getId() : null,
                    textOf(dialogBinding.inputIngredient.getText()),
                    textOf(dialogBinding.inputQuantity.getText()),
                    textOf(dialogBinding.inputUnit.getText()));

            switch (result) {
                case INVALID_INGREDIENT:
                    dialogBinding.layoutIngredient.setError(getString(R.string.shopping_error_ingredient));
                    break;
                case INVALID_QUANTITY:
                    dialogBinding.layoutQuantity.setError(getString(R.string.shopping_error_quantity));
                    break;
                case SAVED:
                    dialog.dismiss();
                    break;
            }
        }));
        dialog.show();
    }

    private String textOf(CharSequence text) {
        return text == null ? "" : text.toString();
    }

    private String formatQuantity(double quantity) {
        return quantity == Math.floor(quantity)
                ? String.valueOf((long) quantity)
                : String.valueOf(quantity);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
