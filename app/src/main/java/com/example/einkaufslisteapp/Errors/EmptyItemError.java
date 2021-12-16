package com.example.einkaufslisteapp.Errors;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class EmptyItemError implements ItemFieldError {

    private List<EditText> editTextItems;
    private Context context;

    public EmptyItemError(List<EditText> editTextItems, Context context) {
        this.editTextItems = editTextItems;
        this.context = context;
    }

    @Override
    public boolean isItemFieldValid() {
        for (EditText editTextItem : editTextItems) {
            String field = editTextItem.getText().toString();
            if (field.equals("")) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void toastMessage() {
        Toast toastEmptyField = Toast.makeText(context, "Es sind leere Felder vorhanden, bitte alle ausfüllen", Toast.LENGTH_SHORT);
        toastEmptyField.show();
    }
}
