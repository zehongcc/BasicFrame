package com.czh.basicframe.base;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.czh.basicframe.R;

/**
 * author  : czh
 * create Date : 2019/8/7  17:23
 * 详情 :
 */
public abstract class BaseDialog extends Dialog {

    public interface OnDialogClickListener {
        void onClick(View view, int i);
    }

    public BaseDialog(@NonNull Context context) {
        this(context, R.style.dialog);
    }

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        setContentView(init());
    }

    protected abstract View init();

}
