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
import java.util.List;

public class ListCreationActivity extends AppCompatActivity {

    private final ManagerList managerList = new ManagerList(new DatabaseReader(this), new DatabaseWriter(this));
    private List<EditText> editTextItems = new ArrayList<>();
    private EditTextFactory editTextFactory = new EditTextFactory();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_creation);
        setToolBar();
        goToPreviousActivity();
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

    private void addItem() {
        ImageView addItem = findViewById(R.id.toolBarAdd);
        addItem.setOnClickListener(v -> {
            EditText item = generateItemField();
            applyItemToView(item);
            saveEditTextItemToList(item);
        });
    }

    private EditText generateItemField() {
        return (EditText) editTextFactory.create(this);
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
            if (isItemFieldExisting()) {
                toastFieldCanNotBeRemoved();
            } else {
                int lastEntry = editTextItems.size() - 1;
                EditText lastItem = editTextItems.get(lastEntry);
                deleteField(lastItem);
                removeLastEditTextItemFromList(lastItem);
            }
        });
    }

    private boolean isItemFieldExisting() {
        if (editTextItems.isEmpty()) {
            return true;
        }
        return false;
    }

    private void toastFieldCanNotBeRemoved() {
        Toast toastNoEmptyField = Toast.makeText(this, "Es sind keine Felder vorhanden, die gelöscht werden können", Toast.LENGTH_SHORT);
        toastNoEmptyField.show();
    }

    private void deleteField(EditText lastItem) {
        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);
        constraintLayout.removeView(lastItem);
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

        saveListImage.setOnClickListener(v -> {
            String title = editText.getText().toString();

            ItemFieldError itemFieldError = checkItemFieldErrors();
            TitleFieldError titleFieldError = checkTitleErrors(title);
            if (itemFieldError != null) {
                itemFieldError.toastMessage();
            }
            else if (titleFieldError != null) {
                titleFieldError.toastMessage();
            }
            else{
                managerList.saveItemList(title, saveItemsToList());

                returnToManagerListActivity();
            }
        });
    }

    private ItemFieldError checkItemFieldErrors() {
        EmptyItemError emptyItemError = new EmptyItemError(editTextItems, this);
        DuplicateItemError duplicateItemError = new DuplicateItemError(editTextItems, this);

        if (!emptyItemError.isItemFieldValid()) {
            return emptyItemError;
        }
        if (!duplicateItemError.isItemFieldValid()) {
            return duplicateItemError;
        }
        return null;
    }

    private TitleFieldError checkTitleErrors(String title) {
        EmptyTitleError emptyTitleError = new EmptyTitleError(this);
        DuplicateTitleError duplicateTitleError = new DuplicateTitleError(this, managerList);

        if (!emptyTitleError.isTitleValid(title)) {
            return emptyTitleError;
        }
        if (!duplicateTitleError.isTitleValid(title)) {
            return duplicateTitleError;
        }
        return null;
    }

    private void returnToManagerListActivity() {
        Intent intent = new Intent(ListCreationActivity.this, ManagerListActivity.class);
        ListCreationActivity.super.finish();
        startActivity(intent);
    }
}