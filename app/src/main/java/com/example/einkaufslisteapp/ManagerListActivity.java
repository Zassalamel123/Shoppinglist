package com.example.einkaufslisteapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class ManagerListActivity extends AppCompatActivity {

    private ImageButton createListBtn;
    public static final String EXTRA_MESSAGE = "com.example.einkaufslisteapp.MESSAGE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_list);

        createList();


    }

    private void createList() {
        createListBtn = new ImageButton(this);
        createListBtn = findViewById(R.id.createListButton);
        createListBtn.setOnClickListener(v ->
                startActivity(new Intent(ManagerListActivity.this, ListCreationActivity.class)));

    }

}
