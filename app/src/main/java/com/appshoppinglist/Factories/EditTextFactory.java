package com.appshoppinglist.Factories;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

public class EditTextFactory implements ViewFactory {

    @Override
    public View create(Context context) {
        EditText editText = new EditText(context);
        editText.setHint("item");
        editText.setId(View.generateViewId());
        editText.setEms(10);
        editText.setTag("item");
        editText.requestFocus();
        return editText;
    }
}
