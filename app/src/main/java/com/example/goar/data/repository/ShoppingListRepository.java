package com.example.goar.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.goar.data.model.Ingredient;
import com.example.goar.data.model.MeasurementUnit;
import com.example.goar.data.model.ShoppingList;
import com.example.goar.data.model.ShoppingListItem;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class ShoppingListRepository {
    private static ShoppingListRepository instance;

    private final List<ShoppingList> lists = new ArrayList<>();
    private final MutableLiveData<List<ShoppingList>> listsLiveData = new MutableLiveData<>();

    private ShoppingListRepository() {
        ShoppingList weekly = createList("Weekly groceries", LocalDateTime.now().minusDays(2));
        weekly.setPlannedDateTime(LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(18, 0)));
        weekly.getItems().add(createItem(MockData.MILK, 2.0, MeasurementUnit.LITER, true));
        weekly.getItems().add(createItem(MockData.EGG, 10.0, MeasurementUnit.PIECE, false));
        weekly.getItems().add(createItem(MockData.FLOUR, 1.0, MeasurementUnit.KILOGRAM, true));
        weekly.getItems().add(createItem(MockData.TOMATO, 6.0, MeasurementUnit.PIECE, false));
        lists.add(weekly);

        ShoppingList basics = createList("Basics", LocalDateTime.now().minusDays(20));
        basics.setPermanent(true);
        basics.getItems().add(createItem(MockData.SALT, null, null, false));
        basics.getItems().add(createItem(MockData.PEPPER, null, null, false));
        basics.getItems().add(createItem(MockData.YOGURT_BRANDED, 400.0, MeasurementUnit.GRAM, false));
        lists.add(basics);

        ShoppingList pancakeParty = createList("Pancake party", LocalDateTime.now().minusDays(10));
        pancakeParty.setArchived(true);
        pancakeParty.getItems().add(createItem(MockData.FLOUR, 500.0, MeasurementUnit.GRAM, true));
        pancakeParty.getItems().add(createItem(MockData.MILK, 1.0, MeasurementUnit.LITER, true));
        pancakeParty.getItems().add(createItem(MockData.EGG, 6.0, MeasurementUnit.PIECE, true));
        lists.add(pancakeParty);

        publish();
    }

    public static ShoppingListRepository getInstance() {
        if (instance == null) {
            instance = new ShoppingListRepository();
        }
        return instance;
    }

    public LiveData<List<ShoppingList>> getLists() {
        return listsLiveData;
    }

    public ShoppingList getListById(String id) {
        for (ShoppingList list : lists) {
            if (list.getId().equals(id)) return list;
        }
        return null;
    }

    public boolean setItemPurchased(String listId, String itemId, boolean purchased) {
        ShoppingList list = getListById(listId);
        if (list == null) return false;
        for (ShoppingListItem item : list.getItems()) {
            if (item.getId().equals(itemId)) {
                item.setPurchased(purchased);
                break;
            }
        }
        boolean archived = archiveIfCompleted(list);
        publish();
        return archived;
    }

    public void addItem(String listId, ShoppingListItem item) {
        ShoppingList list = getListById(listId);
        if (list == null) return;
        item.setId(UUID.randomUUID().toString());
        list.getItems().add(item);
        publish();
    }

    public void updateItem(String listId, String itemId, Double quantity, MeasurementUnit unit) {
        ShoppingList list = getListById(listId);
        if (list == null) return;
        for (ShoppingListItem item : list.getItems()) {
            if (item.getId().equals(itemId)) {
                item.setQuantity(quantity);
                item.setUnit(unit);
                break;
            }
        }
        publish();
    }

    public void deleteItem(String listId, String itemId) {
        ShoppingList list = getListById(listId);
        if (list == null) return;
        list.getItems().removeIf(item -> item.getId().equals(itemId));
        publish();
    }

    public boolean markAllPurchased(String listId) {
        ShoppingList list = getListById(listId);
        if (list == null) return false;
        for (ShoppingListItem item : list.getItems()) {
            item.setPurchased(true);
        }
        boolean archived = archiveIfCompleted(list);
        publish();
        return archived;
    }

    public void setArchived(String listId, boolean archived) {
        ShoppingList list = getListById(listId);
        if (list == null) return;
        list.setArchived(archived);
        publish();
    }

    public String copyList(String listId) {
        ShoppingList original = getListById(listId);
        if (original == null) return null;

        ShoppingList copy = createList(original.getName(), LocalDateTime.now());
        copy.setPermanent(original.isPermanent());
        for (ShoppingListItem item : original.getItems()) {
            copy.getItems().add(createItem(item.getIngredient(), item.getQuantity(), item.getUnit(), false));
        }
        lists.add(copy);
        publish();
        return copy.getId();
    }

    private boolean archiveIfCompleted(ShoppingList list) {
        if (list.isCompleted() && !list.isPermanent() && !list.isArchived()) {
            list.setArchived(true);
            return true;
        }
        return false;
    }

    public void add(ShoppingList list) {
        list.setId(UUID.randomUUID().toString());
        lists.add(list);
        publish();
    }

    private void publish() {
        listsLiveData.setValue(new ArrayList<>(lists));
    }

    private static ShoppingList createList(String name, LocalDateTime createdAt) {
        return new ShoppingList(UUID.randomUUID().toString(), name, createdAt);
    }

    private static ShoppingListItem createItem(Ingredient ingredient, Double quantity,
                                               MeasurementUnit unit, boolean purchased) {
        ShoppingListItem item = new ShoppingListItem(UUID.randomUUID().toString(), ingredient, quantity, unit);
        item.setPurchased(purchased);
        return item;
    }
}
