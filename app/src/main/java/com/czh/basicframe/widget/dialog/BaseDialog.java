package com.czh.basicframe.base;

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
    private View mView ;
    private Context mContext ;
    private T mParam ;

    public BaseDialog(@NonNull Context context,T param) {
        this(context, R.style.dialog,param);
    }

    public BaseDialog(@NonNull Context context, int themeResId,T param) {
        super(context, themeResId);
        this.mContext = context ;
        this.mParam = param ;
        mView = LayoutInflater.from(context).inflate(inflateLayout(),null) ;
        init(mParam);
        setContentView(mView);
    }
    protected abstract int inflateLayout();

    protected abstract void init(T param);

    protected OnDialogClickListener mClickListener;

    public interface OnDialogClickListener {
        void onClick(View view, int i);
    }

    protected void setOnClickListener(OnDialogClickListener listener) {
        this.mClickListener = listener;
    }


}
