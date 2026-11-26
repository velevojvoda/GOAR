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
import com.example.goar.databinding.FragmentShoppingListFormBinding;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class ShoppingListFormFragment extends Fragment {

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("d MMM yyyy", Locale.ENGLISH);

    private FragmentShoppingListFormBinding binding;
    private ShoppingListFormViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentShoppingListFormBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ShoppingListFormViewModel.class);

        viewModel.getPlannedDate().observe(getViewLifecycleOwner(), date ->
                binding.inputPlannedDate.setText(date == null ? "" : date.format(DATE_FORMAT)));
        binding.inputPlannedDate.setOnClickListener(v -> showDatePicker());
        binding.layoutPlannedDate.setEndIconOnClickListener(v -> viewModel.setPlannedDate(null));

        binding.buttonSave.setOnClickListener(v -> save());
    }

    private void showDatePicker() {
        MaterialDatePicker<Long> picker = MaterialDatePicker.Builder.datePicker()
                .setTitleText(R.string.shopping_planned_date)
                .build();
        picker.addOnPositiveButtonClickListener(selection -> viewModel.setPlannedDate(
                Instant.ofEpochMilli(selection).atZone(ZoneOffset.UTC).toLocalDate()));
        picker.show(getChildFragmentManager(), "planned_date");
    }

    private void save() {
        CharSequence name = binding.inputName.getText();
        boolean saved = viewModel.save(name == null ? "" : name.toString(),
                binding.switchPermanent.isChecked());
        if (saved) {
            NavHostFragment.findNavController(this).navigateUp();
        } else {
            binding.layoutName.setError(getString(R.string.shopping_error_name));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
