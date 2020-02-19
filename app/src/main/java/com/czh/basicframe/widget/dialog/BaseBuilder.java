package com.czh.basicframe.widget.dialog;

import android.content.Context;

/**
 * create by Chen
 * create date : 2019/11/18
 * desc :
 */
public abstract class BaseBuilder<B extends BaseBuilder, P extends BaseParam> {
    protected Context mContext;
    protected P mParam;

    protected abstract P createParam();

    public BaseBuilder(Context context) {
        this.mParam = createParam();
        this.mContext = context;
    }

    public B setTitle(String str) {
        mParam.titleStr = str;
        return (B) this;
    }

    public B setContent(String str) {
        mParam.contentStr = str;
        return (B) this;
    }

    public B setOk(String str) {
        mParam.okStr = str;
        return (B) this;
    }

    public B setCancel(String str) {
        mParam.cancelStr = str;
        return (B) this;
    }

    public B setOnClickListener(BaseDialog.OnDialogClickListener clickListener) {
        mParam.clickListener = clickListener;
        return (B) this;
    }

    public B setCancelable(boolean isCancelable) {
        mParam.isCancelable = isCancelable;
        return (B) this;
    }

    public B setCanceledOnTouchOutside(boolean isCanceledOnTouchOutside) {
        mParam.isCanceledOnTouchOutside = isCanceledOnTouchOutside;
        return (B) this;
    }

}
