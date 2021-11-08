package com.example.einkaufslisteapp;

import android.content.Intent;
import android.graphics.Paint;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import managerlists.ManagerList;
import org.json.JSONObject;
import ressourcelists.DatabaseReader;
import ressourcelists.DatabaseWriter;

import java.util.List;

public class ListEditActivity extends AppCompatActivity {

    private final int[] itemEditIds = new int[]{R.id.editItem1, R.id.editItem2, R.id.editItem3, R.id.editItem4, R.id.editItem5, R.id.editItem6, R.id.editItem7, R.id.editItem8};
    private final ManagerList managerList = new ManagerList(new DatabaseReader(this), new DatabaseWriter(this));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_edit);
        getTitleListFromPreviousActivity();
        loadItems();
        checkMarkItem();
    }

    private void getTitleListFromPreviousActivity() {
        Intent intent = getIntent();
        String title = intent.getStringExtra(ManagerListActivity.TITLE_NAME);
        TextView textView = (TextView) findViewById(R.id.editTitleList);
        textView.setText(title);
    }

    private void loadItems() {
        TextView textView = (TextView) findViewById(R.id.editTitleList);
        String title = textView.getText().toString();
        List<String> itemKeys = managerList.getItemKeys(title);
        int index = 0;
        for (int id : itemEditIds) {
            TextView textViewItem = (TextView) findViewById(id);
            textViewItem.setText(itemKeys.get(index++));
            checkMarkItemBasedOnValue(title, textViewItem);
        }
    }

    private void checkMarkItemBasedOnValue(String title, TextView textViewItem) {
        Object value = managerList.getItemValue(title, textViewItem.getText().toString());
        if (value != null) {
            boolean isItemChecked = (boolean) value;
            if (isItemChecked) {
                textViewItem.setPaintFlags(textViewItem.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                textViewItem.setPaintFlags(textViewItem.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            }

        }
    }

    private void checkMarkItem() {
        TextView textView = (TextView) findViewById(R.id.editTitleList);
        String title = textView.getText().toString();
        JSONObject jsonObject = managerList.getItemsByTitleKey(title);
        for (int id : itemEditIds) {
            TextView textViewItem = (TextView) findViewById(id);
            textViewItem.setOnClickListener(v->{
                checkMarkItemOnClick(title, textViewItem);
            });
        }
    }

    private void checkMarkItemOnClick(String title, TextView textViewItem) {
        Object itemValue = managerList.getItemValue(title, textViewItem.getText().toString());
        boolean isItemChecked = (boolean) itemValue;
        if (isItemChecked) {
            textViewItem.setPaintFlags(textViewItem.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            managerList.updateItemValue(title,textViewItem.getText().toString(),false);
        } else {
            textViewItem.setPaintFlags(textViewItem.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            managerList.updateItemValue(title,textViewItem.getText().toString(),true);
        }
    }
}