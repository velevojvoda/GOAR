package com.example.goar.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goar.R;
import com.example.goar.data.model.PantryItem;
import com.example.goar.databinding.ItemPantryBinding;
import com.google.android.material.color.MaterialColors;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.PantryViewHolder> {

    public interface OnItemActionListener {
        void onItemClick(PantryItem item);
        void onDeleteClick(PantryItem item);
    }

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("d MMM yyyy", Locale.ENGLISH);
    private static final int EXPIRY_WARNING_DAYS = 7;

    private final List<PantryItem> items = new ArrayList<>();
    private final OnItemActionListener listener;

    public PantryAdapter(OnItemActionListener listener) {
        this.listener = listener;
    }

    public void setItems(List<PantryItem> newItems) {
        items.clear();
        items.addAll(newItems);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PantryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPantryBinding binding = ItemPantryBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new PantryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PantryViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class PantryViewHolder extends RecyclerView.ViewHolder {
        private final ItemPantryBinding binding;

        PantryViewHolder(ItemPantryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(PantryItem item) {
            Context context = binding.getRoot().getContext();

            binding.textName.setText(item.getIngredient().getName());
            binding.textName.setTextColor(item.getIngredient().isBranded()
                    ? ContextCompat.getColor(context, R.color.branded_ingredient)
                    : MaterialColors.getColor(binding.textName, com.google.android.material.R.attr.colorOnSurface));

            String details = buildDetails(item);
            binding.textDetails.setText(details);
            binding.textDetails.setVisibility(details.isEmpty() ? View.GONE : View.VISIBLE);

            LocalDate expiryDate = item.getExpiryDate();
            if (expiryDate == null) {
                binding.textExpiry.setVisibility(View.GONE);
            } else {
                LocalDate today = LocalDate.now();
                String date = expiryDate.format(DATE_FORMAT);
                binding.textExpiry.setText(expiryDate.isBefore(today)
                        ? context.getString(R.string.pantry_expired, date)
                        : context.getString(R.string.pantry_expires, date));
                boolean expiringSoon = !expiryDate.isAfter(today.plusDays(EXPIRY_WARNING_DAYS));
                binding.textExpiry.setTextColor(MaterialColors.getColor(binding.textExpiry, expiringSoon
                        ? androidx.appcompat.R.attr.colorError
                        : com.google.android.material.R.attr.colorOnSurfaceVariant));
                binding.textExpiry.setVisibility(View.VISIBLE);
            }

            binding.getRoot().setOnClickListener(v -> listener.onItemClick(item));
            binding.buttonDelete.setOnClickListener(v -> listener.onDeleteClick(item));
        }

        private String buildDetails(PantryItem item) {
            List<String> parts = new ArrayList<>();
            if (item.getBrand() != null && !item.getBrand().isEmpty()) {
                parts.add(item.getBrand());
            }
            if (item.getQuantity() != null) {
                String unit = item.getUnit() != null ? " " + item.getUnit().getSymbol() : "";
                parts.add(formatQuantity(item.getQuantity()) + unit);
            }
            return String.join(" · ", parts);
        }

        private String formatQuantity(double quantity) {
            return quantity == Math.floor(quantity)
                    ? String.valueOf((long) quantity)
                    : String.valueOf(quantity);
        }
    }
}
