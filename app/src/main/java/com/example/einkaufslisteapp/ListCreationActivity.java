package com.example.einkaufslisteapp;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import managerlists.EinkaufsListe;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import managerlists.ManagerList;
import ressourcelists.ShoppingListDatabase;

import java.util.ArrayList;
import java.util.List;

public class ListCreationActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.einkaufslisteapp.MESSAGE";
    public EinkaufsListe einkaufsListe = new EinkaufsListe();
    private ShoppingListDatabase shoppingListDatabase = new ShoppingListDatabase();
    private ManagerList managerList = new ManagerList(shoppingListDatabase);
    private Button saveListButton;
    private int[] itemsIds = new int[]{R.id.item1, R.id.item2, R.id.item3, R.id.item4, R.id.item5, R.id.item6, R.id.item7, R.id.item8};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_creation);

    }

    private void nameTitle() {


//        Intent intent = new Intent(this, ListCreationActivity.class);
        EditText editText = (EditText) findViewById(R.id.listTitle);
        String message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
//        startActivity(intent);

        einkaufsListe.nameTitle(message);

    }

    private void addItem() {

        ConstraintLayout mLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);
        Button mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = new EditText(ListCreationActivity.this);
                editText.setLayoutParams(new
                        ConstraintLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        ConstraintLayout.LayoutParams.WRAP_CONTENT));
                mLayout.addView(editText);
            }
        });

    }

    private void deleteItem() {

    }

    private void modifyItem() {

    }

    private List<String> saveItemsToList() {
        List<String> items = new ArrayList<>();

        for(int id : itemsIds){
            EditText editText = (EditText) findViewById(id);
            String item = editText.getText().toString();
            items.add(item);
        }

        return items;
    }

    private void safeList() {
        EditText editText = (EditText) findViewById(R.id.listTitle);
        String title = editText.getText().toString();
        saveListButton = new Button(this);
        saveListButton = findViewById(R.id.saveListButton);
        saveListButton.setOnClickListener(v ->
                managerList.saveToJson(title, saveItemsToList()));
    }

}