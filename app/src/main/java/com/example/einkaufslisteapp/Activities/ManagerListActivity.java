package com.example.einkaufslisteapp.Activities;

import com.example.einkaufslisteapp.Factories.ImageButtonFactory;
import com.example.einkaufslisteapp.Factories.TextViewFactory;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import com.example.einkaufslisteapp.R;
import managerlists.ManagerList;
import ressourcelists.DatabaseReader;
import ressourcelists.DatabaseWriter;

import java.util.ArrayList;
import java.util.List;

public class ManagerListActivity extends AppCompatActivity {

    private final ManagerList managerList = new ManagerList(new DatabaseReader(this), new DatabaseWriter(this));
    public static final String TITLE_NAME = "com.example.einkaufslisteapp.MESSAGE";
    private TextViewFactory textViewFactory = new TextViewFactory();
    private ImageButtonFactory imageButtonFactory = new ImageButtonFactory();
    private List<ImageButton> editListImageButtons = new ArrayList<>();
    private List<TextView> textViews = new ArrayList<>();
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_list);
        loadLists();
        addNewList();
        deleteNewestList();
    }

    private void addNewList() {
        Button addNewListBtn = findViewById(R.id.createNewListButton);
        addNewListBtn.setOnClickListener(v -> {
            Intent intent = new Intent(ManagerListActivity.this, ListCreationActivity.class);
            startActivity(intent);
        });
    }

    private void deleteNewestList() {
        Button deleteListBtn = findViewById(R.id.deleteListButton);
        deleteListBtn.setOnClickListener(v -> {
            if (doesListExist()) {
                int lastEntry = textViews.size() - 1;
                TextView lastItem = textViews.get(lastEntry);
                deleteTextView(lastItem);
                removeLastTextViewFromList(lastItem);

                int lastEntryButton = editListImageButtons.size() - 1;
                ImageButton lastImageButton = editListImageButtons.get(lastEntryButton);
                deleteEditButton(lastImageButton);
                removeLastEditButtonFromList(lastImageButton);

                managerList.deleteList(lastItem.getText().toString());
            } else {
                toastListCanNotBeRemoved();
            }
        });
    }

    private boolean doesListExist() {
        if (textViews.isEmpty()) {
            return false;
        }
        return true;
    }

    private void toastListCanNotBeRemoved() {
        Toast toastNoList = Toast.makeText(this, "Es sind keine Listen vorhanden, die gelöscht werden können", Toast.LENGTH_SHORT);
        toastNoList.show();
    }

    private void deleteTextView(TextView lastItem) {
        ConstraintLayout constraintLayout = findViewById(R.id.managerListLayout);
        constraintLayout.removeView(lastItem);
    }

    private void removeLastTextViewFromList(TextView textView) {
        textViews.remove(textView);
    }

    private void deleteEditButton(ImageButton imageButton) {
        ConstraintLayout constraintLayout = findViewById(R.id.managerListLayout);
        constraintLayout.removeView(imageButton);
    }

    private void removeLastEditButtonFromList(ImageButton imageButton) {
        editListImageButtons.remove(imageButton);
    }

    private void loadLists() {
        List<String> keys = managerList.getTitleKeys();
        if (keys == null) {
            return;
        }

        for (String key : keys) {
            createNewListMenus(key);
        }
    }

    private void createNewListMenus(String key) {
        index++;
        addImageButtonToEditSpecificList();
        addTextViewTitle(key);
    }

    private void addImageButtonToEditSpecificList() {
        ImageButton imageButton = generateEditListImageView();
        applyEditImageButtonToView(imageButton);
        saveEditListImageButtonsToCollection(imageButton);
    }

    private ImageButton generateEditListImageView() {
        return (ImageButton) imageButtonFactory.create(this);
    }

    private void applyEditImageButtonToView(ImageButton imageButton) {
        ConstraintLayout constraintLayout = findViewById(R.id.managerListLayout);
        ConstraintSet constraintSet = new ConstraintSet();

        constraintLayout.addView(imageButton);
        constraintSet.clone(constraintLayout);

        if (editListImageButtons.isEmpty()) {
            constraintSet.connect(imageButton.getId(), ConstraintSet.TOP, R.id.managerListLayout, ConstraintSet.TOP, 16);
            constraintSet.connect(imageButton.getId(), ConstraintSet.RIGHT, R.id.managerListLayout, ConstraintSet.RIGHT, 8);
        } else {
            ImageButton lastItem = editListImageButtons.get(editListImageButtons.size() - 1);
            constraintSet.connect(imageButton.getId(), ConstraintSet.TOP, lastItem.getId(), ConstraintSet.BOTTOM, 16);
            constraintSet.connect(imageButton.getId(), ConstraintSet.RIGHT, R.id.managerListLayout, ConstraintSet.RIGHT, 8);
        }

        constraintSet.applyTo(constraintLayout);
    }

    private void saveEditListImageButtonsToCollection(ImageButton imageButton) {
        editListImageButtons.add(imageButton);
    }

    private void addTextViewTitle(String key) {
        TextView textView = generateTextView(key);
        applyTextViewToView(textView);
        saveTextViewToCollection(textView);
        applyListenerEditListButton(textView);
    }

    private TextView generateTextView(String key) {
        TextView textView = (TextView) textViewFactory.create(this);
        textView.setText(key);
        return textView;
    }

    private void applyTextViewToView(TextView textView) {
        ConstraintLayout constraintLayout = findViewById(R.id.managerListLayout);
        ConstraintSet constraintSet = new ConstraintSet();

        constraintLayout.addView(textView);
        constraintSet.clone(constraintLayout);

        if (textViews.isEmpty()) {
            constraintSet.connect(textView.getId(), ConstraintSet.TOP, R.id.managerListLayout, ConstraintSet.TOP, 32);
            constraintSet.connect(textView.getId(), ConstraintSet.RIGHT, editListImageButtons.get(index - 1).getId(), ConstraintSet.LEFT, 8);
            constraintSet.connect(textView.getId(), ConstraintSet.LEFT, R.id.managerListLayout, ConstraintSet.LEFT, 16);

        } else {
            TextView lastItem = textViews.get(textViews.size() - 1);
            constraintSet.connect(textView.getId(), ConstraintSet.TOP, lastItem.getId(), ConstraintSet.BOTTOM, 58);
            constraintSet.connect(textView.getId(), ConstraintSet.RIGHT, editListImageButtons.get(index - 1).getId(), ConstraintSet.LEFT, 8);
            constraintSet.connect(textView.getId(), ConstraintSet.LEFT, R.id.managerListLayout, ConstraintSet.LEFT, 16);
        }

        constraintSet.applyTo(constraintLayout);
    }


    private void saveTextViewToCollection(TextView textView) {
        textViews.add(textView);
    }

    private void applyListenerEditListButton(TextView textView) {
        ImageButton createListBtn = editListImageButtons.get(index - 1);
        createListBtn.setOnClickListener(v -> {
            startListEditActivity(textView);
        });
    }

    private void startListEditActivity(TextView textView) {
        Intent intent = new Intent(ManagerListActivity.this, ListEditActivity.class);
        String title = textView.getText().toString();
        intent.putExtra(TITLE_NAME, title);
        startActivity(intent);
    }
}
