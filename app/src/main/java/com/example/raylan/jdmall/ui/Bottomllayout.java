package com.example.raylan.jdmall.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by Raylan on 2017/7/10.
 */

public class Bottomllayout extends LinearLayout {
    public Bottomllayout(Context context) {
        super(context);
    }

    public Bottomllayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).setSelected(selected);
        }
    }
}
