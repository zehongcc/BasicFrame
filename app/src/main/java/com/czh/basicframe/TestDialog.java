package com.czh.basicframe;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.czh.basicframe.utils.LogUtils;
import com.czh.basicframe.widget.dialog.BaseDialog;
import com.czh.basicframe.widget.dialog.BaseBuilder;
import com.czh.basicframe.widget.dialog.TestParam;

/**
 * create by Chen
 * create date : 2019/11/18
 * desc :
 */
public class TestDialog extends BaseDialog<TestParam> {

    private TestDialog(@NonNull Context context, TestParam param) {
        super(context, param);
    }

    private TestDialog(@NonNull Context context, int themeResId, TestParam param) {
        super(context, themeResId, param);
    }

    @Override
    protected int inflateLayout() {
        return R.layout.dialog_test;
    }

    @Override
    protected void init(TestParam param) {
        TextView titleTv = mView.findViewById(R.id.dialog_title_tv);
        TextView contentTv = mView.findViewById(R.id.dialog_content_tv);
        TextView okTv = mView.findViewById(R.id.dialog_ok_tv);
        TextView cancelTv = mView.findViewById(R.id.dialog_cancel_tv);
        View lineView = mView.findViewById(R.id.dialog_line_view);
        LogUtils.e(TAG, ">>>>>>>>>>>>>> null >>>>>>>>>>>> " + (param == null));
        if (param != null) {
            if (param.titleColor != 0) {
                titleTv.setTextColor(mContext.getResources().getColor(param.titleColor));
            }
            titleTv.setText(param.titleStr);
            LogUtils.e(TAG, ">>>>>>>>>>>>>> ??? >>>>>>>>>>>> " +param.titleColor+" , "+param.titleStr);
        }
        //点击事件
        okTv.setOnClickListener(this);
        cancelTv.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_ok_tv:
                if (mClickListener != null) {
                    mClickListener.onClick(v, 1);
                }
                break;
            case R.id.dialog_cancel_tv:
                if (mClickListener != null) {
                    mClickListener.onClick(v, 2);
                }
                break;
        }
        dismiss();
    }

    public static class Builder extends BaseBuilder<Builder, TestParam> {

        @Override
        protected TestParam createParam() {
            return new TestParam();
        }

        public Builder(Context context) {
            super(context);
        }

        public Builder setTitleColor(int color) {
            mParam.titleColor = color;
            return this;
        }

        public TestDialog create() {
            return new TestDialog(mContext, mParam);
        }

    }


}
