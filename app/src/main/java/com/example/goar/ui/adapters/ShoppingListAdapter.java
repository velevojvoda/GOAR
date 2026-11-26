package com.example.goar.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goar.R;
import com.example.goar.data.model.ShoppingList;
import com.example.goar.databinding.ItemShoppingListBinding;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ShoppingListViewHolder> {

    public interface OnListClickListener {
        void onListClick(ShoppingList list);
    }

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("d MMM yyyy", Locale.ENGLISH);

    private final List<ShoppingList> lists = new ArrayList<>();
    private final OnListClickListener listener;

    public ShoppingListAdapter(OnListClickListener listener) {
        this.listener = listener;
    }

    public void setLists(List<ShoppingList> newLists) {
        lists.clear();
        lists.addAll(newLists);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ShoppingListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemShoppingListBinding binding = ItemShoppingListBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ShoppingListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingListViewHolder holder, int position) {
        holder.bind(lists.get(position));
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    class ShoppingListViewHolder extends RecyclerView.ViewHolder {
        private final ItemShoppingListBinding binding;

        ShoppingListViewHolder(ItemShoppingListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(ShoppingList list) {
            Context context = binding.getRoot().getContext();

            binding.textName.setText(list.getName());
            binding.textPermanent.setVisibility(list.isPermanent() ? View.VISIBLE : View.GONE);
            binding.textCreated.setText(context.getString(R.string.shopping_created,
                    list.getCreatedAt().format(DATE_FORMAT)));

            if (list.getPlannedDateTime() == null) {
                binding.textPlanned.setVisibility(View.GONE);
            } else {
                binding.textPlanned.setText(context.getString(R.string.shopping_planned,
                        list.getPlannedDateTime().format(DATE_FORMAT)));
                binding.textPlanned.setVisibility(View.VISIBLE);
            }

            binding.textProgress.setText(context.getString(R.string.shopping_progress,
                    list.getPurchasedCount(), list.getItems().size()));

            binding.getRoot().setOnClickListener(v -> listener.onListClick(list));
        }
    }
}
