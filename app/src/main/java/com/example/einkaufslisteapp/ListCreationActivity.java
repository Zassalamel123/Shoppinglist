package com.example.einkaufslisteapp;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import managerlists.EinkaufsListe;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class ListCreationActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.einkaufslisteapp.MESSAGE";
    public EinkaufsListe einkaufsListe = new EinkaufsListe();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_creation);

        addItem();

    }

    private void nameTitle() {


//        Intent intent = new Intent(this, ListCreationActivity.class);
        EditText editText = (EditText) findViewById(R.id.listTitle1);
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

}