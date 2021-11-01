package com.example.einkaufslisteapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import managerlists.ManagerList;
import ressourcelists.DatabaseReader;
import ressourcelists.DatabaseWriter;

import java.util.List;

public class ManagerListActivity extends AppCompatActivity {

    private ImageButton createListBtn;
    private ImageButton editListBtn;
    private final ManagerList managerList = new ManagerList(new DatabaseReader(this), new DatabaseWriter(this));
    public static final String TITLE_NAME = "com.example.einkaufslisteapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_list);
        loadList();
        createList();
        editList();
    }

    private void createList() {
        createListBtn = new ImageButton(this);
        createListBtn = findViewById(R.id.createListButton);
        createListBtn.setOnClickListener(v ->{
            Intent intent = new Intent(ManagerListActivity.this, ListCreationActivity.class);
            TextView textView = findViewById(R.id.shoppingList1);
            String message = textView.getText().toString();
            intent.putExtra(TITLE_NAME, message);
            startActivity(intent);
        });
    }

    private void editList() {
        editListBtn = new ImageButton(this);
        editListBtn = findViewById(R.id.editListButton);
        editListBtn.setOnClickListener(v ->{
            Intent intent = new Intent(ManagerListActivity.this, ListEditActivity.class);
            TextView textView = findViewById(R.id.shoppingList1);
            String message = textView.getText().toString();
            intent.putExtra(TITLE_NAME, message);
            startActivity(intent);
        });
    }

    private void loadList() {
        String title = "";
        String key = "";
        List<String> keys = managerList.getTitleKeys();

        if (areKeysNotNull(keys)) {
            key = keys.get(0);
        } else
            key = "Titel";

        TextView textView = findViewById(R.id.shoppingList1);
        textView.setText(key);
    }

    private boolean areKeysNotNull(List<String> keys) {
        return keys != null;
    }

}
