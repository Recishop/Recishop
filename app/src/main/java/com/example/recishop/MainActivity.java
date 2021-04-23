package com.example.recishop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.recishop.fragments.MapFragment;
import com.example.recishop.fragments.ProfileFragment;
import com.example.recishop.fragments.RecipeCreationFragment;
import com.example.recishop.fragments.ShoppingListFragment;
import com.example.recishop.models.Ingredient;
import com.example.recishop.models.UserViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseUser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    final FragmentManager fragmentManager = getSupportFragmentManager();
    BottomNavigationView bottomNavigationView;
    public static final String SHOPPING_LIST_FILE = "SHOPPING_LIST.txt";

    // Create viewModel
    public UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getShoppingList().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                // Any time the list is changed, save it to backend or disk
                writeShoppingListToFile();
            }
        });

        // Navigation bar initialization
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.action_map:
                        fragment = new MapFragment();
                        break;
                    case R.id.action_profile:
                        fragment = new ProfileFragment();
                        break;
                    case R.id.action_shopping_list:
                        fragment = new ShoppingListFragment();
                        break;
                    case R.id.action_recipe_list:
                        fragment = new RecipeCreationFragment();
                        break;
                    default:
                        fragment = new ProfileFragment();
                }
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.action_profile);

        loadShoppingList();
    }

    public void goToCreateRecipe() {
        Fragment fragment = new RecipeCreationFragment();
        fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
    }

    public void writeShoppingListToFile() {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.openFileOutput(SHOPPING_LIST_FILE, Context.MODE_PRIVATE));
            List<String> temp = (userViewModel.getShoppingList().getValue() == null) ? new ArrayList<>() : userViewModel.getShoppingList().getValue();
            for (String item : temp) {
                Log.d(TAG, String.format("Writing item: [%s]", item));
                outputStreamWriter.write(item);
                outputStreamWriter.write("\n");
            }
            outputStreamWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadShoppingList() {
        Log.i(TAG, "Attempting to load shopping list from file");
        List<String> loadedItems = new ArrayList<>();

        try {
            InputStream inputStream = this.openFileInput(SHOPPING_LIST_FILE);

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receivedString = "";

                while ( (receivedString = bufferedReader.readLine()) != null) {
                    Log.d(TAG, String.format("Loaded item: %s", receivedString));
                    loadedItems.add(receivedString);
                }
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e(TAG, "Shopping list file not found: ", e);
        } catch (IOException e) {
            Log.e(TAG, "IOException: ", e);
        } finally {
            Log.d(TAG, "Returning list with contents: " + loadedItems);
            userViewModel.setShoppingList(loadedItems);
        }
    }

    public void logoutAndBackToLoginScreen() {
        Intent i = new Intent(this, LoginActivity.class);
        Toast.makeText(this, "Successfully signed out!", Toast.LENGTH_SHORT).show();
        ParseUser.logOut();
        startActivity(i);
        finish();
    }
}