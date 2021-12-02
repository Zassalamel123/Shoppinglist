package com.example.einkaufslisteapp;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashSet;
import java.util.List;

public class DuplicateItemError implements ItemFieldError {

    private List<EditText> editTextItems;
    private Context context;

    public DuplicateItemError(List<EditText> editTextItems, Context context) {
        this.editTextItems = editTextItems;
        this.context = context;
    }

    @Override
    public boolean isItemFieldValid() {
        HashSet<String> hashSet = new HashSet<>();
        for (EditText editTextItem : editTextItems) {
            String field = editTextItem.getText().toString();
            if (hashSet.add(field) == false) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void toastMessage() {
        Toast toastDuplicateField = Toast.makeText(context, "Die Feldernamen müssen einmalig sein, bitte ändern", Toast.LENGTH_SHORT);
        toastDuplicateField.show();
    }
}
