package com.example.recishop.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class UserViewModel extends ViewModel {
    // All user-based data goes here
    private MutableLiveData<List<Ingredient>> shoppingList;

    public UserViewModel() {
        shoppingList = new MutableLiveData<>();
    }

    public LiveData<List<Ingredient>> getShoppingList() {
        if (shoppingList == null) {
            shoppingList = new MutableLiveData<List<Ingredient>>();
            loadShoppingList();
        }
        return shoppingList;
    }

    private void loadShoppingList() {
        // Currently load the shopping list from harddisk memory


        // TODO: Change parse structure to load from backend
    }
}
