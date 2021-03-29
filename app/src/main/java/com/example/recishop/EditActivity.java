package com.example.recishop;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.recishop.fragments.ShoppingListFragment;

public class EditActivity extends AppCompatActivity {

    EditText etItem;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_edit);

        etItem = findViewById(R.id.etItem);
        btnSave = findViewById(R.id.btnSave);

        getSupportActionBar().setTitle("Edit item");

        etItem.setText(getIntent().getStringExtra(ShoppingListFragment.KEY_ITEM_TEXT));

        // When the user is done editing, they click the save button
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent which will contain the results
                Intent intent = new Intent();

                // Pass the data (results of editing)
                intent.putExtra(ShoppingListFragment.KEY_ITEM_TEXT, etItem.getText().toString());
                intent.putExtra(ShoppingListFragment.KEY_ITEM_POSITION, getIntent().getExtras().getInt(ShoppingListFragment.KEY_ITEM_POSITION));

                // Set the result of the intent
                setResult(RESULT_OK, intent);
                // Finish activity, close the screen and go
                finish();
            }
        });
    }
}