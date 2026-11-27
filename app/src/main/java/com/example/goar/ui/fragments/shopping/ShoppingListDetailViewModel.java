package com.example.goar.ui.fragments.shopping;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.goar.data.model.Ingredient;
import com.example.goar.data.model.MeasurementUnit;
import com.example.goar.data.model.ShoppingList;
import com.example.goar.data.model.ShoppingListItem;
import com.example.goar.data.repository.MockData;
import com.example.goar.data.repository.ShoppingListRepository;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListDetailViewModel extends ViewModel {

    public enum ItemResult { SAVED, INVALID_INGREDIENT, INVALID_QUANTITY }

    private final ShoppingListRepository repository = ShoppingListRepository.getInstance();
    private final List<Ingredient> ingredients = MockData.getIngredients();
    private final MutableLiveData<String> listId = new MutableLiveData<>();
    private final MediatorLiveData<ShoppingList> list = new MediatorLiveData<>();

    public ShoppingListDetailViewModel() {
        list.addSource(repository.getLists(), lists -> updateList());
        list.addSource(listId, id -> updateList());
    }

    public void setListId(String id) {
        if (id != null && !id.equals(listId.getValue())) {
            listId.setValue(id);
        }
    }

    public LiveData<ShoppingList> getList() {
        return list;
    }

    public boolean setPurchased(ShoppingListItem item, boolean purchased) {
        return repository.setItemPurchased(listId.getValue(), item.getId(), purchased);
    }

    public boolean markAllPurchased() {
        return repository.markAllPurchased(listId.getValue());
    }

    public void archive() {
        repository.setArchived(listId.getValue(), true);
    }

    public void reactivate() {
        repository.setArchived(listId.getValue(), false);
    }

    public String createCopy() {
        return repository.copyList(listId.getValue());
    }

    public void deleteItem(ShoppingListItem item) {
        repository.deleteItem(listId.getValue(), item.getId());
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

    public ItemResult saveItem(String itemId, String ingredientName, String quantityText, String unitSymbol) {
        Ingredient ingredient = findIngredient(ingredientName);
        if (ingredient == null) return ItemResult.INVALID_INGREDIENT;

        Double quantity = null;
        if (quantityText.trim().isEmpty()) {
            if (!ingredient.isQuantityOptional()) return ItemResult.INVALID_QUANTITY;
        } else {
            try {
                quantity = Double.parseDouble(quantityText.trim());
            } catch (NumberFormatException e) {
                return ItemResult.INVALID_QUANTITY;
            }
            if (quantity <= 0) return ItemResult.INVALID_QUANTITY;
        }

        MeasurementUnit unit = findUnit(unitSymbol);
        if (quantity != null && unit == null) {
            unit = ingredient.getDefaultUnit();
        }

        if (itemId == null) {
            repository.addItem(listId.getValue(), new ShoppingListItem(null, ingredient, quantity, unit));
        } else {
            repository.updateItem(listId.getValue(), itemId, quantity, unit);
        }
        return ItemResult.SAVED;
    }

    private void updateList() {
        String id = listId.getValue();
        if (id != null) {
            list.setValue(repository.getListById(id));
        }
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
}
