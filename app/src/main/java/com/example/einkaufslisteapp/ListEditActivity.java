package com.example.einkaufslisteapp;

import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import managerlists.ManagerList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ressourcelists.DatabaseReader;
import ressourcelists.DatabaseWriter;

public class ListEditActivity extends AppCompatActivity {

    private final int[] itemEditIds = new int[]{R.id.editItem1, R.id.editItem2, R.id.editItem3, R.id.editItem4, R.id.editItem5, R.id.editItem6, R.id.editItem7, R.id.editItem8};
    private final ManagerList managerList = new ManagerList(new DatabaseReader(this), new DatabaseWriter(this));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_edit);
        getTitleListFromPreviousActivity();
        try {
            loadItems();
        } catch (JSONException exception) {
            exception.printStackTrace();
        }
    }

    private void getTitleListFromPreviousActivity() {
        Intent intent = getIntent();
        String title = intent.getStringExtra(ManagerListActivity.TITLE_NAME);
        TextView textView = (TextView) findViewById(R.id.editTitleList);
        textView.setText(title);
    }

    private void loadItems() throws JSONException {

        JSONArray content = new JSONArray();
        content = managerList.getJsonContent();
//        JSONObject jsonObject = new JSONObject(content);
        String test = content.get(0).toString();
        int counter = 0;

        for(int id : itemEditIds){
            TextView textView = (TextView) findViewById(id);
            textView.setText((String) content.opt(counter));
            counter++;
        }
    }
}