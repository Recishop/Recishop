package com.example.recishop.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.recishop.R;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    private static final String TAG = ProfileFragment.class.getSimpleName();
    private static final String WELCOME_MESSAGE = "Welcome back, ";

    TextView tvUsername;
    RecyclerView rvUserRecipes;
    ImageView ivProfilePicture;

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

        tvUsername = view.findViewById(R.id.tvWelcomeMessage);
        ivProfilePicture = view.findViewById(R.id.ivProfilePicture);
        rvUserRecipes = view.findViewById(R.id.rvUserRecipes);

        ivProfilePicture.setImageResource(R.drawable.ic_baseline_person_24);
        tvUsername.setText(WELCOME_MESSAGE + "Mike");

        // TODO: Create adapter for recipes
        // TODO: Pull information from current Parse User to populate fields and recipes
    }

    // FOR CUSTOM ACTION BAR ACTIONS FOR FRAGMENT //

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

}