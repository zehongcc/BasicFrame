package com.czh.basicframe.home_module;

import android.os.Bundle;

import com.czh.basicframe.R;
import com.czh.basicframe.base.BaseActivity;
import com.czh.basicframe.https.base.BasePresenter;

/**
 * create DATE : 2020/3/13
 * create by : Chen
 * desc : 主页  方式1： 可以左右滑动
 */
public class Activity_Home_Page extends BaseActivity {

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_home_page1;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    protected void main() {

    }
}
