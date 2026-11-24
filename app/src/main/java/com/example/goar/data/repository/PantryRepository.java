package com.example.goar.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.goar.data.model.Ingredient;
import com.example.goar.data.model.MeasurementUnit;
import com.example.goar.data.model.PantryItem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class PantryRepository {
    private static PantryRepository instance;

    private final List<PantryItem> items = new ArrayList<>();
    private final MutableLiveData<List<PantryItem>> itemsLiveData = new MutableLiveData<>();

    private PantryRepository() {
        items.add(createItem(MockData.MILK, null, 1000.0, MeasurementUnit.MILLILITER, LocalDate.now().plusDays(3)));
        items.add(createItem(MockData.EGG, null, 6.0, MeasurementUnit.PIECE, LocalDate.now().plusDays(14)));
        items.add(createItem(MockData.YOGURT_BRANDED, "Imlek", 400.0, MeasurementUnit.GRAM, LocalDate.now().minusDays(1)));
        items.add(createItem(MockData.FLOUR, null, 1.0, MeasurementUnit.KILOGRAM, null));
        items.add(createItem(MockData.SALT, null, null, null, null));
        publish();
    }

    public static PantryRepository getInstance() {
        if (instance == null) {
            instance = new PantryRepository();
        }
        return instance;
    }

    public LiveData<List<PantryItem>> getItems() {
        return itemsLiveData;
    }

    public PantryItem getItemById(String id) {
        for (PantryItem item : items) {
            if (item.getId().equals(id)) return item;
        }
        return null;
    }

    public void add(PantryItem item) {
        item.setId(UUID.randomUUID().toString());
        items.add(item);
        publish();
    }

    public void update(PantryItem item) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId().equals(item.getId())) {
                items.set(i, item);
                break;
            }
        }
        publish();
    }

    public void delete(String id) {
        items.removeIf(item -> item.getId().equals(id));
        publish();
    }

    private void publish() {
        itemsLiveData.setValue(new ArrayList<>(items));
    }

    private static PantryItem createItem(Ingredient ingredient, String brand, Double quantity,
                                         MeasurementUnit unit, LocalDate expiryDate) {
        PantryItem item = new PantryItem(UUID.randomUUID().toString(), ingredient);
        item.setBrand(brand);
        item.setQuantity(quantity);
        item.setUnit(unit);
        item.setExpiryDate(expiryDate);
        return item;
    }
}
