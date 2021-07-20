package com.example.recishop.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.example.recishop.R;
import com.example.recishop.models.Recipe;

import java.util.zip.Inflater;

/**
 * RecipeCreationDialog is a dialog that appears when a user creates a recipe
 * This dialog will display a custom view that walks the user through the process
 * of creating a recipe.
 *
 * At the end of the dialog, the positive click button will call the interface method
 * that is defined in the CreationActivity class
 */
public class RecipeCreationDialog extends DialogFragment {
    private static final String TAG = RecipeCreationDialog.class.getSimpleName();

    public interface RecipeDialogInterfaceListeners {
        public void finishRecipe();
    }

    private RecipeDialogInterfaceListeners callback;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            callback = (RecipeDialogInterfaceListeners) getTargetFragment();
        } catch (ClassCastException e) {
            Log.e(TAG, "Calling fragment must implement the RecipeDialogInterfaceListener: " + e);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        Dialog createRecipeDialog = new AlertDialog.Builder(getActivity())
                .setView(inflater.inflate(R.layout.activity_creation, null))
                .setPositiveButton("Finish", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callback.finishRecipe();
                    }
                })
                .setNegativeButton("Quit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                })
                .create();
        return createRecipeDialog;
    }
}
