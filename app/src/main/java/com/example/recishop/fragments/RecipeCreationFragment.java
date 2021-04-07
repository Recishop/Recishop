package com.example.recishop.fragments;

import android.content.Intent;
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

import com.example.recishop.CreationActivity;
import com.example.recishop.R;
import com.example.recishop.Recipe;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class RecipeCreationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TAG = "RecipeCreationFragment";
    private EditText etRecipeName;
    private Button btnStartCreate;


    public RecipeCreationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_creation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etRecipeName = view.findViewById(R.id.etRecipeName);
        btnStartCreate = view.findViewById(R.id.btnStartCreate);

        btnStartCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recipeName = etRecipeName.getText().toString();
                if(recipeName.isEmpty()){
                    Toast.makeText(getContext(), "Name cannot be empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    ParseUser currentUser = ParseUser.getCurrentUser();
                    saveRecipe(recipeName, currentUser);}
            }
        });
    }



    private void saveRecipe(String recipeName, ParseUser currentUser) {
        Recipe recipe = new Recipe();
        recipe.setName(recipeName);
        recipe.setChef(currentUser);
        recipe.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e!=null){
                    Log.e(TAG, "Error while saving", e);
                    Toast.makeText(getContext(), "Error while saving", Toast.LENGTH_SHORT).show();
                }
                Log.i(TAG, "Recipe start was successful");
                etRecipeName.setText("");
                Intent i = new Intent(getContext(), CreationActivity.class);
                i.putExtra("recipe", recipe);
                startActivity(i);
                //goCreationActivity(recipe);
                //goCreationActivity();
            }
        });
    }


    /*private void goCreationActivity() {
        Intent i = new Intent(getContext(), CreationActivity.class);
        //i.putExtra("recipe", Parcels.wrap(recipe));
        startActivity(i);
    }

    private void goCreationActivity(ParseObject recipe) {
        Intent i = new Intent(getContext(), CreationActivity.class);
        i.putExtra("recipe", Parcels.wrap(recipe));
        startActivity(i);
    }*/
}