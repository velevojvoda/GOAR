package com.example.goar.ui.fragments.pantry;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.goar.data.model.PantryItem;
import com.example.goar.data.repository.PantryRepository;

import java.util.List;

public class PantryViewModel extends ViewModel {
    private final PantryRepository repository = PantryRepository.getInstance();

    public LiveData<List<PantryItem>> getItems() {
        return repository.getItems();
    }

    public void delete(PantryItem item) {
        repository.delete(item.getId());
    }
}
