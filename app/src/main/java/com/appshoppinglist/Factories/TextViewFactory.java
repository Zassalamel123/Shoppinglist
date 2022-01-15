package com.appshoppinglist.Factories;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

public class TextViewFactory implements ViewFactory {

    @Override
    public View create(Context context) {
        TextView textView = new TextView(context);
        textView.setId(View.generateViewId());
        textView.setTextSize(20f);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        return textView;
    }
}
