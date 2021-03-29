package com.example.recishop.fragments;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.recishop.EditActivity;
import com.example.recishop.ItemsAdapter;
import com.example.recishop.R;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


public class ShoppingListFragment extends Fragment {

    public static final String KEY_ITEM_TEXT = "item_text";
    public static final String KEY_ITEM_POSITION = "item_position";
    public static final int EDIT_TEXT_CODE = 20;

    List<String> items;

    Button btnAdd;
    EditText etItem;
    RecyclerView rvItems;
    ItemsAdapter itemsAdapter;

    public ShoppingListFragment( ) {

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.shopping_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnAdd = view.findViewById(R.id.btnAdd);
        etItem = view.findViewById(R.id.etItem);
        rvItems = view.findViewById(R.id.rvItems);

        loadItems();

        ItemsAdapter.OnLongClickListener onLongClickListener = new ItemsAdapter.OnLongClickListener() {
            @Override
            public void onItemLongClicked(int position) {
                // Delete the item from the model
                items.remove(position);
                // Notify the adapter
                itemsAdapter.notifyItemRemoved(position);
                Toast.makeText(getContext(), "Item was removed", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        };

//        ItemsAdapter.OnClickListener onClickListener = new ItemsAdapter.OnClickListener() {
//            @Override
//            public void onItemClicked(int position) {
//                Log.d("MainActivity", "Single click at position" + position);
//                // Create the new activity
//                Intent i = new Intent(ShoppingListFragment.this, EditActivity.class);
//                // Pass the relevant data
//                i.putExtra(KEY_ITEM_TEXT, items.get(position));
//                i.putExtra(KEY_ITEM_POSITION, position);
//                // Display the activity
//                startActivityForResult(i, EDIT_TEXT_CODE);
//            }
//        };

//        itemsAdapter = new ItemsAdapter(items, onLongClickListener, onClickListener);
        rvItems.setAdapter(itemsAdapter);
        rvItems.setLayoutManager(new LinearLayoutManager(getContext()));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todoItem = etItem.getText().toString();
                // Add item to model
                items.add(todoItem);
                // Notify adaptor that an item is inserted
                itemsAdapter.notifyItemInserted(items.size() - 1);
                etItem.setText("");
                Toast.makeText(getContext(), "Item was added", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        });
    }


    // Handle the result of the edit activity
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if (resultCode == RESULT_OK && requestCode == EDIT_TEXT_CODE) {
//            // Retrieve the updated text value
//            String itemText = data.getStringExtra(KEY_ITEM_TEXT);
//            // Extract the original position of the edited item from the position key
//            int position = data.getExtras().getInt(KEY_ITEM_POSITION);
//
//            // Update the model with new item at right position
//            items.set(position, itemText);
//            // Notify the adaptor
//            itemsAdapter.notifyItemChanged(position);
//            // Persist the changes
//            saveItems();
//            Toast.makeText(getContext(), "Item updated sucessfully ", Toast.LENGTH_SHORT).show();
//        } else {
//            Log.w("MainActivity", "Unknown call to onActivityResult");
//        }
//    }

    private File getDataFile() {
        return new File(getContext().getFilesDir(), "data.txt");
    }

    // This function will load items by reading every line of the data file
    private void loadItems() {
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity", "Error reading items", e);
            items = new ArrayList<>();
        }
    }

    // This function saves item by writing them into data file
    private void saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            Log.e("MainActivity", "Error writing items", e);
        }
    }
}
