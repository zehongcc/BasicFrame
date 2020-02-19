package com.czh.basicframe.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;

import com.czh.basicframe.R;
import com.czh.basicframe.widget.dialog.BaseParam;

/**
 * author  : czh
 * create Date : 2019/8/7  17:23
 * 详情 :
 */
public abstract class BaseDialog<T extends BaseParam> extends Dialog implements View.OnClickListener {
    protected String TAG ;
    protected View mView;
    protected Context mContext;

    protected OnDialogClickListener mClickListener;

    public BaseDialog(@NonNull Context context, T param) {
        this(context, R.style.dialog, param);
    }

    public BaseDialog(@NonNull Context context, int themeResId, T param) {
        super(context, themeResId);
        this.TAG = getClass().getSimpleName();
        this.mContext = context;
        this.mClickListener = param.clickListener;
        mView = LayoutInflater.from(context).inflate(inflateLayout(), null);
        init(param);
        setCancelable(param.isCancelable);
        setCanceledOnTouchOutside(param.isCanceledOnTouchOutside);
        setContentView(mView);
    }

    protected abstract int inflateLayout();

    protected abstract void init(T param);

    public interface OnDialogClickListener {
        void onClick(View view, int i);
    }
}
