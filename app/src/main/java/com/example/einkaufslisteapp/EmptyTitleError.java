package com.example.einkaufslisteapp;

import android.content.Context;
import android.widget.Toast;

public class EmptyTitleError implements TitleFieldError {

    private Context context;

    public EmptyTitleError(Context context) {
        this.context = context;
    }

    @Override
    public boolean isTitleValid(String title) {
        if (title.equals("")) {
            return false;
        }
        return true;
    }

    @Override
    public void toastMessage() {
        Toast toastEmptyField = Toast.makeText(context, "Der Titel ist leer, bitte alle ausfüllen", Toast.LENGTH_SHORT);
        toastEmptyField.show();
    }
}
