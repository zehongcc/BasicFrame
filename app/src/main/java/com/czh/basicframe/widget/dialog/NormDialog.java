package com.czh.basicframe.widget.dialog;

import android.app.Dialog;
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
public class NormDialog extends Dialog implements View.OnClickListener {

    private DialogClickListener clickListener;

    private NormDialog(@NonNull Context context, NormDialogParameter parameter) {
        super(context, R.style.dialog);
        this.clickListener = parameter.listener;
        initDialog(parameter);
    }

    private void initDialog(NormDialogParameter parameter) {
        View view = getLayoutInflater().inflate(R.layout.dialog_norm, null);
        TextView titleTv = view.findViewById(R.id.dialog_title_tv);
        TextView contentTv = view.findViewById(R.id.dialog_content_tv);
        TextView okTv = view.findViewById(R.id.dialog_ok_tv);
        TextView cancelTv = view.findViewById(R.id.dialog_cancel_tv);
        View lineView = view.findViewById(R.id.dialog_line_view);
        if (parameter != null) {
            if (!TextUtils.isEmpty(parameter.title)) {
                titleTv.setText(parameter.title);
            }
            if (!TextUtils.isEmpty(parameter.content)) {
                contentTv.setText(parameter.content);
            }
            if (!TextUtils.isEmpty(parameter.ok)) {
                okTv.setText(parameter.ok);
            }
            if (!TextUtils.isEmpty(parameter.cancel)) {
                cancelTv.setText(parameter.cancel);
            }
            if (parameter.isHideCancel) {
                lineView.setVisibility(View.GONE);
                cancelTv.setVisibility(View.GONE);
            }
            titleTv.setTextColor(parameter.titleTvColor == 0 ? getContext().getResources().getColor(R.color.color_black) : parameter.titleTvColor);
            contentTv.setTextColor(parameter.contentTvColor == 0 ? getContext().getResources().getColor(R.color.color_black) : parameter.contentTvColor);
            okTv.setTextColor(parameter.okTvColor == 0 ? getContext().getResources().getColor(R.color.color_black) : parameter.okTvColor);
            cancelTv.setTextColor(parameter.cancelTvColor == 0 ? getContext().getResources().getColor(R.color.color_black) : parameter.cancelTvColor);
        }
        //点击事件
        okTv.setOnClickListener(this);
        cancelTv.setOnClickListener(this);
        //设置此对话框是否可以通过返回键dismiss()
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setContentView(view);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_ok_tv:
                if (clickListener != null) {
                    clickListener.onClick(v, 1);
                }
                break;
            case R.id.dialog_cancel_tv:
                if (clickListener != null) {
                    clickListener.onClick(v, 2);
                }
                break;
        }
        dismiss();
    }

    public static class Builder {
        private NormDialogParameter parameter = new NormDialogParameter();
        private Context context;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setTitle(String title) {
            this.parameter.title = title;
            return this;
        }

        public Builder setContent(String content) {
            this.parameter.content = content;
            return this;
        }

        public Builder setOk(String ok) {
            this.parameter.ok = ok;
            return this;
        }

        public Builder setCancel(String cancel) {
            this.parameter.cancel = cancel;
            return this;
        }

        public Builder isHideCancel(boolean isHide) {
            this.parameter.isHideCancel = isHide;
            return this;
        }

        public Builder setClickListener(DialogClickListener listener) {
            parameter.listener = listener;
            return this;
        }

        public Builder setOkColor(int okColor) {
            this.parameter.okTvColor = okColor;
            return this;
        }

        public Builder setCancelTvColor(int cancelColor) {
            this.parameter.cancelTvColor = cancelColor;
            return this;
        }

        public Builder setTitleColor(int titleColor) {
            this.parameter.titleTvColor = titleColor;
            return this;
        }

        public Builder setContentColor(int contentColor) {
            this.parameter.contentTvColor = contentColor;
            return this;
        }

        public NormDialog create() {
            NormDialog normDialog = new NormDialog(context, parameter);
            return normDialog;
        }
    }


}
