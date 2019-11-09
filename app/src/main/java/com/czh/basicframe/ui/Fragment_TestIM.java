package com.czh.basicframe.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.czh.basicframe.R;
import com.czh.basicframe.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;

/**
 * create by Chen
 * create date : 2019/11/4
 * desc :
 */
public class Fragment_TestIM extends BaseFragment {
    @BindView(R.id.im_user_et)
    EditText imUserEt;
    @BindView(R.id.im_password_et)
    EditText imPasswordEt;
    @BindView(R.id.im_login_btn)
    Button imLoginBtn;

    @Override
    protected int setLayout() {
        return R.layout.fragment_testim;
    }

    @Override
    protected void initValue(Bundle bundle) {

    }

    @Override
    protected void main() {

    }


    @OnClick(R.id.im_login_btn)
    public void onViewClicked() {
        final String user = imUserEt.getText().toString();
        String password = imPasswordEt.getText().toString();
        JMessageClient.login(user, password, new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if (i == 0) {
                    switch (user) {
                        case "chen1":
                            break;
                        case "chen2":

                            break;
                    }
                }
            }
        });
    }
}
