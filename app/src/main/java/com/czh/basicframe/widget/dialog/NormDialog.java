package com.czh.basicframe.widget.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.czh.basicframe.R;
import com.czh.basicframe.interfaces.DialogClickListener;

/**
 * author  : czh
 * create Date : 2019/10/24  10:05
 * 详情 : 普通dialog
 */
public class NormDialog extends BaseDialog<NormDialogParameter> {

    public NormDialog(@NonNull Context context, NormDialogParameter param) {
        super(context, param);
    }

    @Override
    protected int inflateLayout() {
        return R.layout.dialog_norm;
    }

    @Override
    protected void init(NormDialogParameter param) {
        TextView titleTv = mView.findViewById(R.id.dialog_title_tv);
        TextView contentTv = mView.findViewById(R.id.dialog_content_tv);
        TextView okTv = mView.findViewById(R.id.dialog_ok_tv);
        TextView cancelTv = mView.findViewById(R.id.dialog_cancel_tv);
        View lineView = mView.findViewById(R.id.dialog_line_view);
        if (param != null) {
            if (!TextUtils.isEmpty(param.titleStr)) {
                titleTv.setText(param.titleStr);
            }
            if (!TextUtils.isEmpty(param.contentStr)) {
                contentTv.setText(param.contentStr);
            }
            if (!TextUtils.isEmpty(param.okStr)) {
                okTv.setText(param.okStr);
            }
            if (!TextUtils.isEmpty(param.cancelStr)) {
                cancelTv.setText(param.cancelStr);
            }
            if (param.isHideCancel) {
                lineView.setVisibility(View.GONE);
                cancelTv.setVisibility(View.GONE);
            }
            titleTv.setTextColor(param.titleTvColor == 0 ? getContext().getResources().getColor(R.color.color_black) : param.titleTvColor);
            contentTv.setTextColor(param.contentTvColor == 0 ? getContext().getResources().getColor(R.color.color_black) : param.contentTvColor);
            okTv.setTextColor(param.okTvColor == 0 ? getContext().getResources().getColor(R.color.color_black) : param.okTvColor);
            cancelTv.setTextColor(param.cancelTvColor == 0 ? getContext().getResources().getColor(R.color.color_black) : param.cancelTvColor);
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

    public static class Builder extends BaseBuilder<Builder,NormDialogParameter> {

        public Builder(Context context) {
            super(context);
        }

        @Override
        protected NormDialogParameter createParam() {
            return new NormDialogParameter();
        }

        public Builder isHideCancel(boolean isHide) {
            this.mParam.isHideCancel = isHide;
            return this;
        }

        public Builder setOkColor(int okColor) {
            this.mParam.okTvColor = okColor;
            return this;
        }
        public Builder setCancelTvColor(int cancelColor) {
            this.mParam.cancelTvColor = cancelColor;
            return this;
        }

        public Builder setTitleColor(int titleColor) {
            this.mParam.titleTvColor = titleColor;
            return this;
        }

        public Builder setContentColor(int contentColor) {
            this.mParam.contentTvColor = contentColor;
            return this;
        }

        public NormDialog create() {
            NormDialog normDialog = new NormDialog(mContext,mParam);
            return normDialog;
        }
    }


}
