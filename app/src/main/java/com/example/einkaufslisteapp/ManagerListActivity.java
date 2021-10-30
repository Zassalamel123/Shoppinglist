package com.example.einkaufslisteapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import managerlists.ManagerList;
import ressourcelists.DatabaseReader;
import ressourcelists.DatabaseWriter;

import java.util.ArrayList;
import java.util.List;

public class ManagerListActivity extends AppCompatActivity {

    private ImageButton createListBtn;
    private final ManagerList managerList = new ManagerList(new DatabaseReader(this), new DatabaseWriter(this));
    public static final String EXTRA_MESSAGE = "com.example.einkaufslisteapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_list);
        loadList();
        createList();
    }

    private void createList() {
        createListBtn = new ImageButton(this);
        createListBtn = findViewById(R.id.createListButton);
        createListBtn.setOnClickListener(v ->{
            Intent intent = new Intent(ManagerListActivity.this, ListCreationActivity.class);
            TextView textView = findViewById(R.id.shoppingList1);
            String message = textView.getText().toString();
            intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);
        });
    }

    private void loadList() {
        String title = "";
        String key = "";
        List<String> keys = managerList.getKeys();

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
