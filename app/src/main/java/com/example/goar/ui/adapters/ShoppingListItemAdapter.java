package com.example.goar.ui.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goar.R;
import com.example.goar.data.model.ShoppingListItem;
import com.example.goar.databinding.ItemShoppingListItemBinding;
import com.google.android.material.color.MaterialColors;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListItemAdapter extends RecyclerView.Adapter<ShoppingListItemAdapter.ItemViewHolder> {

    public interface OnItemActionListener {
        void onPurchasedChanged(ShoppingListItem item, boolean purchased);
        void onItemClick(ShoppingListItem item);
        void onDeleteClick(ShoppingListItem item);
    }

    private final List<ShoppingListItem> items = new ArrayList<>();
    private final OnItemActionListener listener;
    private boolean editable = true;

    public ShoppingListItemAdapter(OnItemActionListener listener) {
        this.listener = listener;
    }

    public void setItems(List<ShoppingListItem> newItems, boolean editable) {
        items.clear();
        items.addAll(newItems);
        this.editable = editable;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemShoppingListItemBinding binding = ItemShoppingListItemBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private final ItemShoppingListItemBinding binding;

        ItemViewHolder(ItemShoppingListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(ShoppingListItem item) {
            Context context = binding.getRoot().getContext();

            binding.textName.setText(item.getIngredient().getName());
            binding.textName.setTextColor(item.getIngredient().isBranded()
                    ? ContextCompat.getColor(context, R.color.branded_ingredient)
                    : MaterialColors.getColor(binding.textName, com.google.android.material.R.attr.colorOnSurface));

            int flags = binding.textName.getPaintFlags();
            binding.textName.setPaintFlags(item.isPurchased()
                    ? flags | Paint.STRIKE_THRU_TEXT_FLAG
                    : flags & ~Paint.STRIKE_THRU_TEXT_FLAG);

            binding.textQuantity.setText(formatQuantity(item));

            binding.checkboxPurchased.setOnCheckedChangeListener(null);
            binding.checkboxPurchased.setChecked(item.isPurchased());
            binding.checkboxPurchased.setEnabled(editable);
            binding.checkboxPurchased.setOnCheckedChangeListener((button, checked) ->
                    listener.onPurchasedChanged(item, checked));

            binding.buttonDelete.setVisibility(editable ? View.VISIBLE : View.GONE);
            binding.buttonDelete.setOnClickListener(v -> listener.onDeleteClick(item));
            binding.getRoot().setEnabled(editable);
            binding.getRoot().setOnClickListener(v -> listener.onItemClick(item));
        }

        private String formatQuantity(ShoppingListItem item) {
            if (item.getQuantity() == null) return "";
            double quantity = item.getQuantity();
            String number = quantity == Math.floor(quantity)
                    ? String.valueOf((long) quantity)
                    : String.valueOf(quantity);
            return item.getUnit() != null ? number + " " + item.getUnit().getSymbol() : number;
        }
    }
}
