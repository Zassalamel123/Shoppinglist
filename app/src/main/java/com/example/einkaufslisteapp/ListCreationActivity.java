package com.example.einkaufslisteapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import managerlists.EinkaufsListe;
import managerlists.ManagerList;
import ressourcelists.DatabaseReader;
import ressourcelists.DatabaseWriter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ListCreationActivity extends AppCompatActivity {

    public EinkaufsListe einkaufsListe = new EinkaufsListe();
    private final ManagerList managerList = new ManagerList(new DatabaseReader(this), new DatabaseWriter(this));
    private int id = 0;
    private int[] itemCreationIds = new int[]{R.id.item1, R.id.item2, R.id.item3, R.id.item4, R.id.item5, R.id.item6, R.id.item7, R.id.item8};
    List<EditText> editTextItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_creation);
        setToolBar();
        goToPreviousActivity();
        getTitleListFromPreviousActivity();
        loadItemsFromPreviousList();
        addItem();
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

    private void loadItemsFromPreviousList() {
        EditText editText = (EditText) findViewById(R.id.listTitle);
        String title = editText.getText().toString();
        List<String> itemKeys = managerList.getItemKeys(title);
        if (itemKeys != null) {
            int index = 0;
            for (int id : itemCreationIds) {
                EditText editTextItem = (EditText) findViewById(id);
                editTextItem.setText(itemKeys.get(index++));
            }
        }
    }

    private void addItem() {

        ImageView addItem = findViewById(R.id.toolBarAdd);
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEditText();
            }
        });

    }

    private void addEditText() {
        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);
        ConstraintSet constraintSet = new ConstraintSet();

        EditText editText = new EditText(this);
        editText.setText("test123123");
        editText.setId(id++);
        editText.setEms(10);

        constraintLayout.addView(editText);
        constraintSet.clone(constraintLayout);

        constraintSet.connect(editText.getId(), ConstraintSet.TOP, R.id.item8, ConstraintSet.BOTTOM, 16);
        constraintSet.connect(editText.getId(), ConstraintSet.LEFT, R.id.constraintLayout, ConstraintSet.LEFT);
        constraintSet.connect(editText.getId(), ConstraintSet.RIGHT, R.id.constraintLayout, ConstraintSet.RIGHT);
//        constraintSet.constrainWidth(editText.getId(), ConstraintSet.WRAP_CONTENT);
//        constraintSet.constrainHeight(editText.getId(), ConstraintSet.WRAP_CONTENT);

//        constraintSet.connect(editText.getId(), ConstraintSet.BOTTOM, R.id.constraintLayout, ConstraintSet.BOTTOM);


//        constraintSet.connect(R.id.button2, ConstraintSet.LEFT, R.id.constraintLayout, ConstraintSet.LEFT);
//        constraintSet.connect(R.id.button2, ConstraintSet.RIGHT, R.id.constraintLayout, ConstraintSet.RIGHT);
//        constraintSet.connect(R.id.button2, ConstraintSet.BOTTOM, R.id.constraintLayout, ConstraintSet.BOTTOM);
//
//        constraintSet.connect(R.id.saveListButton, ConstraintSet.LEFT, R.id.constraintLayout, ConstraintSet.LEFT);
//        constraintSet.connect(R.id.saveListButton, ConstraintSet.RIGHT, R.id.constraintLayout, ConstraintSet.RIGHT);
//        constraintSet.connect(R.id.saveListButton, ConstraintSet.BOTTOM, R.id.button2, ConstraintSet.TOP);
//
//        constraintSet.connect(R.id.item8, ConstraintSet.TOP, R.id.constraintLayout, ConstraintSet.TOP, 16);
//        constraintSet.connect(R.id.item8, ConstraintSet.LEFT, R.id.constraintLayout, ConstraintSet.LEFT);
//        constraintSet.connect(R.id.item8, ConstraintSet.RIGHT, R.id.constraintLayout, ConstraintSet.RIGHT);


        constraintSet.applyTo(constraintLayout);


        editTextItems.add(editText);
    }

    private void deleteItem() {

    }

    private void modifyItem() {

    }

    private List<String> saveItemsToList() {
        List<String> items = new ArrayList<>();

        for (int id : itemCreationIds) {
            EditText editText = (EditText) findViewById(id);
            String item = editText.getText().toString();
            items.add(item);
        }

        return items;
    }

    private void saveShoppingList() {
        ImageView saveListImage = findViewById(R.id.toolBarSave);
        EditText editText = (EditText) findViewById(R.id.listTitle);
        Toast toastEmptyField = Toast.makeText(this, "Es sind leere Felder vorhanden, bitte alle ausfüllen", Toast.LENGTH_SHORT);
        Toast toastDuplicateField = Toast.makeText(this, "Die Feldernamen müssen einmalig sein, bitte ändern", Toast.LENGTH_SHORT);

        saveListImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });
    }

    private boolean areFieldsEmpty() {
        for (int id : itemCreationIds) {
            EditText editText = (EditText) findViewById(id);
            String field = editText.getText().toString();
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
        for (int id : itemCreationIds) {
            EditText editText = (EditText) findViewById(id);
            String field = editText.getText().toString();
            if (hashSet.add(field) == false) {
                return true;
            }
        }
        return false;
    }
}