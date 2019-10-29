package com.czh.basicframe.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

/**
 * author  : czh
 * create Date : 2019/10/26  9:32
 * 详情 :
 */
public class ClearEditText extends AppCompatEditText {

    private boolean hasFocus;//是否获取焦点

    public ClearEditText(Context context) {
        this(context, null);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        hasFocus = hasFocus();

        Drawable compoundDrawable = getCompoundDrawables()[2];//
        if (compoundDrawable == null) {
            compoundDrawable = getCompoundDrawablesRelative()[2];
        }
        if (compoundDrawable != null) {
            compoundDrawable.setBounds(0, 0,
                    compoundDrawable.getIntrinsicWidth(),
                    compoundDrawable.getIntrinsicHeight());
        }
    }
}
