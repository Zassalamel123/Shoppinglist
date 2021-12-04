package com.example.einkaufslisteapp;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;

public class ImageButtonFactory implements ViewFactory{

    @Override
    public View create(Context context) {
        ImageButton imageButton = new ImageButton(context);
        imageButton.setId(View.generateViewId());
        imageButton.setMinimumWidth(60);
        imageButton.setMinimumHeight(44);
        imageButton.setImageResource(R.drawable.ic_baseline_edit_24);
        return imageButton;
    }
}
