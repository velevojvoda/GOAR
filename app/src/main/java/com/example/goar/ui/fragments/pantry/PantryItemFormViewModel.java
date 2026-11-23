package com.example.goar.ui.fragments.pantry;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.goar.data.model.Ingredient;
import com.example.goar.data.model.MeasurementUnit;
import com.example.goar.data.model.PantryItem;
import com.example.goar.data.repository.MockData;
import com.example.goar.data.repository.PantryRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PantryItemFormViewModel extends ViewModel {

    public enum SaveResult { SAVED, INVALID_INGREDIENT, INVALID_QUANTITY }

    private final PantryRepository repository = PantryRepository.getInstance();
    private final List<Ingredient> ingredients = MockData.getIngredients();
    private final MutableLiveData<LocalDate> expiryDate = new MutableLiveData<>();

    private boolean initialized;
    private String itemId;

    public PantryItem loadItem(String id) {
        if (initialized) return null;
        initialized = true;
        itemId = id;
        if (id == null) return null;

        PantryItem item = repository.getItemById(id);
        if (item != null) {
            expiryDate.setValue(item.getExpiryDate());
        }
        return item;
    }

    public boolean isEditMode() {
        return itemId != null;
    }

    public List<String> getIngredientNames() {
        List<String> names = new ArrayList<>();
        for (Ingredient ingredient : ingredients) {
            names.add(ingredient.getName());
        }
        return names;
    }

    public String[] getUnitSymbols() {
        MeasurementUnit[] units = MeasurementUnit.values();
        String[] symbols = new String[units.length];
        for (int i = 0; i < units.length; i++) {
            symbols[i] = units[i].getSymbol();
        }
        return symbols;
    }

    public String getDefaultUnitSymbol(String ingredientName) {
        Ingredient ingredient = findIngredient(ingredientName);
        return ingredient != null ? ingredient.getDefaultUnit().getSymbol() : null;
    }

    public LiveData<LocalDate> getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate date) {
        expiryDate.setValue(date);
    }

    public SaveResult save(String ingredientName, String brand, String quantityText,
                           String unitSymbol, String barcode) {
        Ingredient ingredient = findIngredient(ingredientName);
        if (ingredient == null) return SaveResult.INVALID_INGREDIENT;

        Double quantity = null;
        if (!quantityText.trim().isEmpty()) {
            try {
                quantity = Double.parseDouble(quantityText.trim());
            } catch (NumberFormatException e) {
                return SaveResult.INVALID_QUANTITY;
            }
            if (quantity <= 0) return SaveResult.INVALID_QUANTITY;
        }

        MeasurementUnit unit = findUnit(unitSymbol);
        if (quantity != null && unit == null) {
            unit = ingredient.getDefaultUnit();
        }

        PantryItem item = new PantryItem(itemId, ingredient);
        item.setBrand(emptyToNull(brand));
        item.setQuantity(quantity);
        item.setUnit(unit);
        item.setExpiryDate(expiryDate.getValue());
        item.setBarcode(emptyToNull(barcode));

        if (isEditMode()) {
            repository.update(item);
        } else {
            repository.add(item);
        }
        return SaveResult.SAVED;
    }

    private Ingredient findIngredient(String name) {
        for (Ingredient ingredient : ingredients) {
            if (ingredient.getName().equalsIgnoreCase(name.trim())) return ingredient;
        }
        return null;
    }

    private MeasurementUnit findUnit(String symbol) {
        for (MeasurementUnit unit : MeasurementUnit.values()) {
            if (unit.getSymbol().equals(symbol)) return unit;
        }
        return null;
    }

    private String emptyToNull(String text) {
        return text == null || text.trim().isEmpty() ? null : text.trim();
    }
}
