package com.example.goar.ui.fragments.pantry;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.goar.R;
import com.example.goar.data.model.PantryItem;
import com.example.goar.databinding.FragmentPantryItemFormBinding;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class PantryItemFormFragment extends Fragment {

    public static final String ARG_ITEM_ID = "itemId";

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("d MMM yyyy", Locale.ENGLISH);

    private FragmentPantryItemFormBinding binding;
    private PantryItemFormViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentPantryItemFormBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(PantryItemFormViewModel.class);

        setupIngredientField();
        binding.inputUnit.setSimpleItems(viewModel.getUnitSymbols());
        setupExpiryField();

        String itemId = getArguments() != null ? getArguments().getString(ARG_ITEM_ID) : null;
        PantryItem item = viewModel.loadItem(itemId);
        if (item != null) {
            fillForm(item);
        }
        if (viewModel.isEditMode()) {
            ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
            if (actionBar != null) actionBar.setTitle(R.string.pantry_edit);
        }

        binding.buttonSave.setOnClickListener(v -> save());
    }

    private void setupIngredientField() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_list_item_1, viewModel.getIngredientNames());
        binding.inputIngredient.setAdapter(adapter);
        binding.inputIngredient.setOnItemClickListener((parent, v, position, id) -> {
            String name = (String) parent.getItemAtPosition(position);
            String unit = viewModel.getDefaultUnitSymbol(name);
            if (unit != null && textOf(binding.inputUnit.getText()).isEmpty()) {
                binding.inputUnit.setText(unit, false);
            }
            binding.layoutIngredient.setError(null);
        });
    }

    private void setupExpiryField() {
        viewModel.getExpiryDate().observe(getViewLifecycleOwner(), date ->
                binding.inputExpiry.setText(date == null ? "" : date.format(DATE_FORMAT)));
        binding.inputExpiry.setOnClickListener(v -> showDatePicker());
        binding.layoutExpiry.setEndIconOnClickListener(v -> viewModel.setExpiryDate(null));
    }

    private void showDatePicker() {
        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker()
                .setTitleText(R.string.pantry_expiry_date);
        LocalDate current = viewModel.getExpiryDate().getValue();
        if (current != null) {
            builder.setSelection(current.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli());
        }
        MaterialDatePicker<Long> picker = builder.build();
        picker.addOnPositiveButtonClickListener(selection -> viewModel.setExpiryDate(
                Instant.ofEpochMilli(selection).atZone(ZoneOffset.UTC).toLocalDate()));
        picker.show(getChildFragmentManager(), "expiry_date");
    }

    private void fillForm(PantryItem item) {
        binding.inputIngredient.setText(item.getIngredient().getName(), false);
        binding.inputBrand.setText(item.getBrand());
        if (item.getQuantity() != null) {
            binding.inputQuantity.setText(formatQuantity(item.getQuantity()));
        }
        if (item.getUnit() != null) {
            binding.inputUnit.setText(item.getUnit().getSymbol(), false);
        }
        binding.inputBarcode.setText(item.getBarcode());
    }

    private void save() {
        binding.layoutIngredient.setError(null);
        binding.layoutQuantity.setError(null);

        PantryItemFormViewModel.SaveResult result = viewModel.save(
                textOf(binding.inputIngredient.getText()),
                textOf(binding.inputBrand.getText()),
                textOf(binding.inputQuantity.getText()),
                textOf(binding.inputUnit.getText()),
                textOf(binding.inputBarcode.getText()));

        switch (result) {
            case INVALID_INGREDIENT:
                binding.layoutIngredient.setError(getString(R.string.pantry_error_ingredient));
                break;
            case INVALID_QUANTITY:
                binding.layoutQuantity.setError(getString(R.string.pantry_error_quantity));
                break;
            case SAVED:
                NavHostFragment.findNavController(this).navigateUp();
                break;
        }
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
