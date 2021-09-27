package com.example.einkaufslisteapp;

import android.content.Intent;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goToManagerListActivity();

    }

    private void goToManagerListActivity() {
        button = new Button(this);
        button = findViewById(R.id.buttonListeVerwalten);
        button.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, ManagerListActivity.class)));
    }

    private void loadLists() {

    }
}