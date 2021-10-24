package com.example.einkaufslisteapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import managerlists.ManagerList;
import ressourcelists.DatabaseReader;
import ressourcelists.DatabaseWriter;

import java.util.Iterator;

public class ManagerListActivity extends AppCompatActivity {

    private ImageButton createListBtn;
    public static final String EXTRA_MESSAGE = "com.example.einkaufslisteapp.MESSAGE";
    private final ManagerList managerList = new ManagerList(new DatabaseReader(this), new DatabaseWriter(this));


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
        createListBtn.setOnClickListener(v ->
                startActivity(new Intent(ManagerListActivity.this, ListCreationActivity.class)));
    }

    private void loadList() {
//        String title = "";
//        List<String> iterator = managerList.getKeys();
//        if (iterator != null) {
//            while (iterator.hasNext()){
//                title = iterator.next();
//                TextView textView = new TextView(this);
//                textView = findViewById(R.id.shoppingList1);
//                textView.setText(title);
//            }
//        }
    }

}
