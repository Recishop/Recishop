package com.example.recishop;

import androidx.fragment.app.DialogFragment;

public interface ConfirmationDialogListener {
    public void onPositiveClick(DialogFragment dialogFragment);
    public void onNegativeClick(DialogFragment dialogFragment);
}
