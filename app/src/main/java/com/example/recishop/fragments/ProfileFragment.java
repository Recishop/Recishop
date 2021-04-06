package com.example.recishop.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recishop.LoginActivity;
import com.example.recishop.MainActivity;
import com.example.recishop.R;
import com.example.recishop.Recipe;
import com.example.recishop.RecipesAdapter;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.w3c.dom.Text;

import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    private static final String TAG = ProfileFragment.class.getSimpleName();
    private static final String WELCOME_MESSAGE = "Welcome back, ";

    TextView tvUsername;
    RecyclerView rvUserRecipes;
    ImageView ivProfilePicture;
    List<Recipe> recipeList;
    RecipesAdapter recipesAdapter;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.i(TAG, "Current User: " + ParseUser.getCurrentUser().getUsername());

        tvUsername = view.findViewById(R.id.tvWelcomeMessage);
        ivProfilePicture = view.findViewById(R.id.ivProfilePicture);
        rvUserRecipes = view.findViewById(R.id.rvUserRecipes);

        ivProfilePicture.setImageResource(R.drawable.ic_baseline_person_24);
        tvUsername.setText(WELCOME_MESSAGE + ParseUser.getCurrentUser().getUsername() + "!");

        // TODO: Create adapter for recipes
        recipeList = new ArrayList<>();
        recipesAdapter = new RecipesAdapter(recipeList, getContext());
        rvUserRecipes.setAdapter(recipesAdapter);
        rvUserRecipes.setLayoutManager(new LinearLayoutManager(getContext()));

        // TODO: Pull information from current Parse User to populate fields and recipes
        queryRecipes();
    }

    protected void queryRecipes() {
        ParseQuery<Recipe> recipeParseQuery = ParseQuery.getQuery(Recipe.class);

        // Specify any kind of filtering
        recipeParseQuery.include(Recipe.KEY_CHEF);

        recipeParseQuery.findInBackground(new FindCallback<Recipe>() {
            @Override
            public void done(List<Recipe> objects, ParseException e) {
                if (e == null) {
                    recipeList.clear();
                    recipeList.addAll(objects);
                    recipesAdapter.notifyDataSetChanged();
                    Toast.makeText(getContext(), "Successfully pulled recipes!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "UNSUCCESSFULLY DID NOT PULL recipes!", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Recipe query error: " + e);
                    return;
                }
            }
        });

    }

    // FOR CUSTOM ACTION BAR ACTIONS FOR FRAGMENT //

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_profile_fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_menuBtnSignout: {
                ((MainActivity) getActivity()).logoutAndBackToLoginScreen();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}