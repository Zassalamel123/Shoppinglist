package com.example.einkaufslisteapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import managerlists.EinkaufsListe;
import managerlists.ManagerList;
import ressourcelists.DatabaseReader;
import ressourcelists.DatabaseWriter;

import java.util.ArrayList;
import java.util.List;

public class ListCreationActivity extends AppCompatActivity {

    public EinkaufsListe einkaufsListe = new EinkaufsListe();
    private final ManagerList managerList = new ManagerList(new DatabaseReader(this), new DatabaseWriter(this));
    private final int[] itemCreationIds = new int[]{R.id.item1, R.id.item2, R.id.item3, R.id.item4, R.id.item5, R.id.item6, R.id.item7, R.id.item8};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_creation);
        getTitleListFromPreviousActivity();
        saveShoppingList();
    }

    private void getTitleListFromPreviousActivity() {
        Intent intent = getIntent();
        String title = intent.getStringExtra(ManagerListActivity.TITLE_NAME);
        EditText editText = (EditText) findViewById(R.id.listTitle);
        editText.setText(title);
    }

    private void addItem() {

//        ConstraintLayout mLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);
//        Button mButton = (Button) findViewById(R.id.button);
//        mButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                EditText editText = new EditText(ListCreationActivity.this);
//                editText.setLayoutParams(new
//                        ConstraintLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                        ConstraintLayout.LayoutParams.WRAP_CONTENT));
//                mLayout.addView(editText);
//            }
//        });

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
        Button saveListButton = findViewById(R.id.saveListButton);
        EditText editText = (EditText) findViewById(R.id.listTitle);
        Toast toast = Toast.makeText(this, "Es sind leere Felder vorhanden, bitte alle ausfüllen", Toast.LENGTH_SHORT);

        saveListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (areFieldsEmpty()) {
                    toast.show();
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
            if (isFieldEmpty(editText)) {
                return true;
            }
        }
        return false;
    }

    private boolean isFieldEmpty(EditText editText) {
        String field = editText.getText().toString();
        if (field.equals("")) {
            return true;
        }
        return false;
    }
}