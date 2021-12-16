package com.example.einkaufslisteapp.Activities;

import android.content.Intent;
import android.graphics.Paint;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import com.example.einkaufslisteapp.R;
import managerlists.ManagerList;
import ressourcelists.DatabaseReader;
import ressourcelists.DatabaseWriter;

import java.util.ArrayList;
import java.util.List;

public class ListEditActivity extends AppCompatActivity {

    private final ManagerList managerList = new ManagerList(new DatabaseReader(this), new DatabaseWriter(this));
    private List<TextView> textViewItems = new ArrayList<>();

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

        for (String key : itemKeys) {
            TextView item = generateItemField(key);
            checkMarkItemBasedOnValue(title, item);
            applyItemToView(item);
            textViewItems.add(item);
        }
    }

    private TextView generateItemField(String key) {
        TextView textView = new TextView(this);
        textView.setId(View.generateViewId());
        textView.setText(key);
        textView.setClickable(true);
        textView.setTextSize(24f);
        return textView;
    }

    private void applyItemToView(TextView item) {
        ConstraintLayout constraintLayout = findViewById(R.id.listEditConstraintLayout);
        ConstraintSet constraintSet = new ConstraintSet();

        constraintLayout.addView(item);
        constraintSet.clone(constraintLayout);

        if (textViewItems.isEmpty()) {
            constraintSet.connect(item.getId(), ConstraintSet.TOP, R.id.editTitleList, ConstraintSet.BOTTOM, 16);
            constraintSet.connect(item.getId(), ConstraintSet.LEFT, R.id.listEditConstraintLayout, ConstraintSet.LEFT);
            constraintSet.connect(item.getId(), ConstraintSet.RIGHT, R.id.listEditConstraintLayout, ConstraintSet.RIGHT);
        } else{
            TextView lastItem = textViewItems.get(textViewItems.size()-1);
            constraintSet.connect(item.getId(), ConstraintSet.TOP, lastItem.getId(), ConstraintSet.BOTTOM, 16);
            constraintSet.connect(item.getId(), ConstraintSet.LEFT, R.id.listEditConstraintLayout, ConstraintSet.LEFT);
            constraintSet.connect(item.getId(), ConstraintSet.RIGHT, R.id.listEditConstraintLayout, ConstraintSet.RIGHT);
        }

        constraintSet.applyTo(constraintLayout);
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
        for (TextView item : textViewItems) {
            item.setOnClickListener(v->{
                checkMarkItemOnClick(title, item);
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