package com.example.goar.ui.fragments.shopping;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.goar.data.model.ShoppingList;
import com.example.goar.data.repository.ShoppingListRepository;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListsViewModel extends ViewModel {
    public static final int TAB_ACTIVE = 0;
    public static final int TAB_ARCHIVE = 1;

    private final ShoppingListRepository repository = ShoppingListRepository.getInstance();
    private final MutableLiveData<Integer> selectedTab = new MutableLiveData<>(TAB_ACTIVE);
    private final MediatorLiveData<List<ShoppingList>> visibleLists = new MediatorLiveData<>();

    public ShoppingListsViewModel() {
        visibleLists.addSource(repository.getLists(), lists -> updateVisibleLists());
        visibleLists.addSource(selectedTab, tab -> updateVisibleLists());
    }

    public LiveData<List<ShoppingList>> getVisibleLists() {
        return visibleLists;
    }

    public LiveData<Integer> getSelectedTab() {
        return selectedTab;
    }

    public void selectTab(int tab) {
        selectedTab.setValue(tab);
    }

    private void updateVisibleLists() {
        List<ShoppingList> allLists = repository.getLists().getValue();
        Integer tab = selectedTab.getValue();
        if (allLists == null || tab == null) return;

        boolean showArchived = tab == TAB_ARCHIVE;
        List<ShoppingList> result = new ArrayList<>();
        for (ShoppingList list : allLists) {
            if (list.isArchived() == showArchived) {
                result.add(list);
            }
        }
        visibleLists.setValue(result);
    }
}
