package com.example.recishop;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class CreationActivity extends AppCompatActivity{

    public static final String TAG = "CreationActivity";
    public static final String[] PATHS = {"produce", "dairy", "meat", "other"};
    private Recipe currentRecipe;
    private RecyclerView rvIngredients;
    private EditText etQuantity;
    private EditText etIngrName;
    private EditText etMeasurement;
    private Spinner spinCategory;
    private Button btnAddIngredient;
    private Button btnDone;
    private IngredientsAdapter adapter;
    private List<Ingredient> allIngredients;

    private String chosenCategory;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation);

        rvIngredients = findViewById(R.id.rvIngredients);
        btnAddIngredient = findViewById(R.id.btnAddIngredient);
        etQuantity = findViewById(R.id.etQuantity);
        etIngrName = findViewById(R.id.etIngrName);
        etMeasurement = findViewById(R.id.etMeasurement);
        btnAddIngredient = findViewById(R.id.btnAddIngredient);
        btnDone = findViewById(R.id.btnDone);



        //this freaking line is the problem
        currentRecipe = getIntent().getParcelableExtra("recipe");

        Log.i(TAG,"current recipe: "+currentRecipe.getName());


        spinCategory = (Spinner)findViewById(R.id.spinCategory);
        ArrayAdapter<String>spinadapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,PATHS);

        spinadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinCategory.setAdapter(spinadapter);
        spinCategory.setSelection(3);
        //spinCategory.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        allIngredients = new ArrayList<>();
        adapter = new IngredientsAdapter(this, allIngredients);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvIngredients.setLayoutManager(layoutManager);
        rvIngredients.setAdapter(adapter);

        // TODO fill in this listener and make a queryIngredients function to populate the recycler view and SAVE BUTTON

        btnAddIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"add button clicked");
                String quantcheck = etQuantity.getText().toString();
                Double qty = null;
                String item = etIngrName.getText().toString();
                String meas = etMeasurement.getText().toString();
                Log.i(TAG, "quantity: " + quantcheck);
                Log.i(TAG, "item: " + item);
                Log.i(TAG, "Meas: " + meas);
                Log.i(TAG, "Category: " + chosenCategory);
                if(!quantcheck.isEmpty()){
                    qty = Double.parseDouble(quantcheck);
                }
                if(qty==null||item.isEmpty()||meas.isEmpty()||chosenCategory.isEmpty()){
                    Toast.makeText(CreationActivity.this, "A field is empty", Toast.LENGTH_SHORT).show();
                    Log.e(TAG,"missing info when trying to save ingredient");
                }
                else {
                    Log.i(TAG, "going to save ingredient");
                    saveIngredient(qty,item,meas);
                    Log.i(TAG, "attempting to query ingredients");
                    queryIngredients();
                }

            }
        });


        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CreationActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        spinCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                chosenCategory = (String)parent.getItemAtPosition(position);
                //((TextView) view).setTextColor(Color.BLACK);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
                chosenCategory = "Other";
            }
        });

        queryIngredients();

    }

    private void queryIngredients() {
        Log.i(TAG, "ingredient query called");
        ParseQuery<Ingredient> ingredientParseQuery = ParseQuery.getQuery(Ingredient.class);

        // Specify any kind of filtering
        ingredientParseQuery.include(Ingredient.KEY_RECIPE);
        ingredientParseQuery.whereEqualTo(Ingredient.KEY_RECIPE, currentRecipe);

        ingredientParseQuery.findInBackground(new FindCallback<Ingredient>() {
            @Override
            public void done(List<Ingredient> objects, ParseException e) {
                if (e == null) {
                    allIngredients.clear();
                    allIngredients.addAll(objects);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(CreationActivity.this, "Successfully pulled recipes!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CreationActivity.this, "UNSUCCESSFULLY DID NOT PULL recipes!", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Recipe query error: " + e);
                    return;
                }
            }
        });
    }

    private void saveIngredient(Double qty, String item, String meas) {
        Log.i(TAG, "ingredient save called");
        Ingredient ingredient = new Ingredient();
        ingredient.setItem(item);
        ingredient.setCategory(chosenCategory);
        ingredient.setQuantity(qty);
        ingredient.setMeasurement(meas);
        ingredient.setRecipe(currentRecipe);
        ingredient.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e!=null){
                    Log.e(TAG, "error while saving ingredient");
                } else {
                    Log.i(TAG, "Ingredient save was successful");
                }
                etIngrName.setText("");
                etMeasurement.setText("");
                etQuantity.setText("");
            }
        });
    }


}
