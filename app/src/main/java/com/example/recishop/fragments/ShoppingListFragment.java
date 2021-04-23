package com.example.recishop.fragments;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recishop.ItemsAdapter;
import com.example.recishop.MainActivity;
import com.example.recishop.R;
import com.example.recishop.models.Ingredient;
import com.example.recishop.models.UserViewModel;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ShoppingListFragment extends Fragment {

    private static final String TAG = ShoppingListFragment.class.getSimpleName();

    public static final String KEY_ITEM_TEXT = "item_text";
    public static final String KEY_ITEM_POSITION = "item_position";
    public static final int EDIT_TEXT_CODE = 20;

    List<String> itemsList = new ArrayList<>();

    Button btnAdd;
    EditText etItem;
    RecyclerView rvShoppingItems;
    ItemsAdapter itemsAdapter;
    private ItemsAdapter.OnClickListener onClickListener;

    UserViewModel userViewModel;

    public ShoppingListFragment( ) {
        // Empty constructor for fragment requirements
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.shopping_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ItemsAdapter.OnLongClickListener onLongClickListener = new ItemsAdapter.OnLongClickListener() {
            @Override
            public void onItemLongClicked(int position) {
                Log.i(TAG, "Long press on item: " + userViewModel.getShoppingList().getValue().get(position));

                // Remove from view Model
                Toast.makeText(getContext(), String.format("%s was removed", userViewModel.getShoppingList().getValue().get(position)), Toast.LENGTH_SHORT).show();
                userViewModel.removeItemFromShoppingList(position);
            }
        };

        // View Model
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        userViewModel.getShoppingList().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> ingredients) {
                Log.i(TAG, "Shopping list was updated to: " + Arrays.toString(ingredients.toArray()));
                itemsAdapter = new ItemsAdapter(ingredients, onLongClickListener, onClickListener);
                rvShoppingItems.setAdapter(itemsAdapter);
            }
        });

        // Variable Assignment //
        btnAdd = view.findViewById(R.id.btnAdd);
        etItem = view.findViewById(R.id.etItem);
        rvShoppingItems = view.findViewById(R.id.rvItems);

        //itemsAdapter = new ItemsAdapter(userViewModel.getShoppingList().getValue(), onLongClickListener, onClickListener);
        //rvShoppingItems.setAdapter(itemsAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvShoppingItems.setLayoutManager(linearLayoutManager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(rvShoppingItems.getContext(), linearLayoutManager.getOrientation());
        rvShoppingItems.addItemDecoration(itemDecoration);

        // Listeners //
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String customItem = etItem.getText().toString();
                etItem.setText("");

                Toast.makeText(getContext(), String.format("%s was added to the list", customItem), Toast.LENGTH_SHORT).show();
                userViewModel.addCustomItemToShoppingList(customItem);
            }
        });
    }
}
