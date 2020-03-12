package com.czh.basicframe.ui.login;

import android.os.Bundle;
import android.view.View;

import com.czh.basicframe.R;
import com.czh.basicframe.base.BaseActivity;
import com.czh.basicframe.https.base.BasePresenter;

/**
 * author  : czh
 * create Date : 2019/10/26  9:27
 * 详情 :
 */
public class Activity_Login extends BaseActivity {

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
    }

    @Override
    protected void main() {

    }


    public void toClick(View view) {
        mActivity.overridePendingTransition(R.anim.zoom_right_in, R.anim.zoom_right_out);
        finish();
    }
}
