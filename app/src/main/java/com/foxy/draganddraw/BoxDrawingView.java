package com.foxy.draganddraw;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class BoxDrawingView extends View {

    // используется при создании view в коде
    public BoxDrawingView(Context context) {
        super(context);
    }

    // используется при заполнении view из XML
    public BoxDrawingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
}
