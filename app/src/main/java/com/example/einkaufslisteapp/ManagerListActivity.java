package com.example.einkaufslisteapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import managerlists.ManagerList;
import org.json.JSONArray;
import org.json.JSONObject;
import ressourcelists.ShoppingListDatabase;

import java.util.Iterator;

public class ManagerListActivity extends AppCompatActivity {

    private ImageButton createListBtn;
    public static final String EXTRA_MESSAGE = "com.example.einkaufslisteapp.MESSAGE";
    private final ManagerList managerList = new ManagerList(new ShoppingListDatabase());


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
        JSONArray jsonArray = managerList.getJsonContent();
        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < jsonArray.length(); i++) {
            jsonObject = (JSONObject) jsonArray.opt(i);
        }

        TextView textView = new TextView(this);
        textView = findViewById(R.id.shoppingList1);
        textView.setText(jsonArray.optString(0));
    }

}
