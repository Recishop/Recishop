package com.example.recishop;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recishop.fragments.ProfileFragment;
import com.example.recishop.fragments.RecipeCreationFragment;
import com.example.recishop.fragments.ShoppingListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    final FragmentManager fragmentManager = getSupportFragmentManager();
    BottomNavigationView bottomNavigationView;
    private List<String> shoppingList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Navigation bar initialization
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
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
    }

    public void goToCreateRecipe() {
        Fragment fragment = new RecipeCreationFragment();
        fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
    }

    public List<String> getShoppingList() {
        return shoppingList;
    }

    public void addToShoppingList(List<Ingredient> items) {
        List<String> temp = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            temp.add(items.get(i).getItem());
        }
        shoppingList.addAll(temp);
    }

    public void clearShoppingList() {
        shoppingList.clear();
    }

    public void logoutAndBackToLoginScreen() {
        Intent i = new Intent(this, LoginActivity.class);
        Toast.makeText(this, "Successfully signed out!", Toast.LENGTH_SHORT).show();
        ParseUser.logOut();
        startActivity(i);
        finish();
    }
}