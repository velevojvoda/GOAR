package com.example.goar.ui.fragments.shopping;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.goar.data.model.ShoppingList;
import com.example.goar.data.repository.ShoppingListRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ShoppingListFormViewModel extends ViewModel {
    private final ShoppingListRepository repository = ShoppingListRepository.getInstance();
    private final MutableLiveData<LocalDate> plannedDate = new MutableLiveData<>();

    public LiveData<LocalDate> getPlannedDate() {
        return plannedDate;
    }

    public void setPlannedDate(LocalDate date) {
        plannedDate.setValue(date);
    }

    public boolean save(String name, boolean permanent) {
        if (name.trim().isEmpty()) return false;

        ShoppingList list = new ShoppingList(null, name.trim(), LocalDateTime.now());
        LocalDate date = plannedDate.getValue();
        if (date != null) {
            list.setPlannedDateTime(date.atStartOfDay());
        }
        list.setPermanent(permanent);
        repository.add(list);
        return true;
    }
}
