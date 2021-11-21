package com.example.einkaufslisteapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import managerlists.ManagerList;
import ressourcelists.DatabaseReader;
import ressourcelists.DatabaseWriter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ListCreationActivity extends AppCompatActivity {

    private final ManagerList managerList = new ManagerList(new DatabaseReader(this), new DatabaseWriter(this));
    private List<EditText> editTextItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_creation);
        setToolBar();
        goToPreviousActivity();
        getTitleListFromPreviousActivity();
        addItem();
        deleteLastItem();
        saveShoppingList();
    }

    private void setToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void goToPreviousActivity() {
        ImageView backArrow = findViewById(R.id.toolBarArrowBack);
        backArrow.setOnClickListener(v -> {
            Intent intent = new Intent(ListCreationActivity.this, ManagerListActivity.class);
            ListCreationActivity.super.finish();
            startActivity(intent);
        });
    }

    private void getTitleListFromPreviousActivity() {
        Intent intent = getIntent();
        String title = intent.getStringExtra(ManagerListActivity.TITLE_NAME);
        EditText editText = (EditText) findViewById(R.id.listTitle);
        editText.setText(title);
    }

//    private void loadItemsFromPreviousList() {
//        EditText editText = (EditText) findViewById(R.id.listTitle);
//        String title = editText.getText().toString();
//        List<String> itemKeys = managerList.getItemKeys(title);
//        if (itemKeys != null) {
//            int index = 0;
//            for (int id : itemCreationIds) {
//                EditText editTextItem = (EditText) findViewById(id);
//                editTextItem.setText(itemKeys.get(index++));
//            }
//        }
//    }

    private void addItem() {
        ImageView addItem = findViewById(R.id.toolBarAdd);
        addItem.setOnClickListener(v -> {
            EditText item = generateItemField();
            applyItemToView(item);
            saveEditTextItemToList(item);
        });
    }

    private EditText generateItemField() {
        EditText editText = new EditText(this);
        editText.setHint("item");
        editText.setId(View.generateViewId());
        editText.setEms(10);
        return editText;
    }

    private void applyItemToView(EditText item) {
        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);
        ConstraintSet constraintSet = new ConstraintSet();

        constraintLayout.addView(item);
        constraintSet.clone(constraintLayout);

        if (editTextItems.isEmpty()) {
            constraintSet.connect(item.getId(), ConstraintSet.TOP, R.id.listTitle, ConstraintSet.BOTTOM, 16);
            constraintSet.connect(item.getId(), ConstraintSet.LEFT, R.id.constraintLayout, ConstraintSet.LEFT);
            constraintSet.connect(item.getId(), ConstraintSet.RIGHT, R.id.constraintLayout, ConstraintSet.RIGHT);
        } else {
            EditText lastItem = editTextItems.get(editTextItems.size() - 1);
            constraintSet.connect(item.getId(), ConstraintSet.TOP, lastItem.getId(), ConstraintSet.BOTTOM, 16);
            constraintSet.connect(item.getId(), ConstraintSet.LEFT, R.id.constraintLayout, ConstraintSet.LEFT);
            constraintSet.connect(item.getId(), ConstraintSet.RIGHT, R.id.constraintLayout, ConstraintSet.RIGHT);
        }

        constraintSet.applyTo(constraintLayout);
    }

    private void saveEditTextItemToList(EditText item) {
        editTextItems.add(item);
    }

    private void deleteLastItem() {
        ImageView deleteItem = findViewById(R.id.toolBarDelete);
        deleteItem.setOnClickListener(v -> {
            if (isFieldEmpty()) {
                toastFieldCanNotBeRemoved();
            } else {
                int lastEntry = editTextItems.size() - 1;
                EditText lastItem = editTextItems.get(lastEntry);
                deleteField(lastItem);
                removeLastEditTextItemFromList(lastItem);
            }
        });
    }

    private void deleteField(EditText lastItem) {
        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);
        constraintLayout.removeView(lastItem);
    }

    private boolean isFieldEmpty() {
        if (editTextItems.isEmpty()) {
            return true;
        }
        return false;
    }

    private void toastFieldCanNotBeRemoved() {
        Toast toastNoEmptyField = Toast.makeText(this, "Es sind keine Felder vorhanden, die gelöscht werden können", Toast.LENGTH_SHORT);
        toastNoEmptyField.show();
    }

    private void removeLastEditTextItemFromList(EditText item) {
        editTextItems.remove(item);
    }


    private List<String> saveItemsToList() {
        List<String> items = new ArrayList<>();
        for (EditText editTextItem : editTextItems) {
            String item = editTextItem.getText().toString();
            items.add(item);
        }
        return items;
    }

    private void saveShoppingList() {
        ImageView saveListImage = findViewById(R.id.toolBarSave);
        EditText editText = (EditText) findViewById(R.id.listTitle);
        Toast toastEmptyField = Toast.makeText(this, "Es sind leere Felder vorhanden, bitte alle ausfüllen", Toast.LENGTH_SHORT);
        Toast toastDuplicateField = Toast.makeText(this, "Die Feldernamen müssen einmalig sein, bitte ändern", Toast.LENGTH_SHORT);

        saveListImage.setOnClickListener(v -> {
            if (areFieldsEmpty() | isTitleEmpty()) {
                toastEmptyField.show();
            } else if (areFieldNamesDuplicated()) {
                toastDuplicateField.show();
            } else {
                String title = editText.getText().toString();
                managerList.saveItemList(title, saveItemsToList());
                Intent intent = new Intent(ListCreationActivity.this, ManagerListActivity.class);
                ListCreationActivity.super.finish();
                startActivity(intent);
            }
        });
    }

    private boolean areFieldsEmpty() {
        for (EditText editTextItem : editTextItems) {
            String field = editTextItem.getText().toString();
            if (field.equals("")) {
                return true;
            }
        }
        return false;
    }

    private boolean isTitleEmpty() {
        EditText editText = (EditText) findViewById(R.id.listTitle);
        String field = editText.getText().toString();
        if (field.equals("")) {
            return true;
        }
        return false;
    }

    private boolean areFieldNamesDuplicated() {
        HashSet<String> hashSet = new HashSet<>();
        for (EditText editTextItem : editTextItems) {
            String field = editTextItem.getText().toString();
            if (hashSet.add(field) == false) {
                return true;
            }
        }
        return false;
    }
}