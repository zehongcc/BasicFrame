package com.czh.basicframe.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.czh.basicframe.R;
import com.czh.basicframe.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * create by Chen
 * create date : 2019/11/29
 * desc :音频相关
 */
public class Fragment_2 extends BaseFragment {
    @BindView(R.id.fragment2_btn1)
    Button fragment2Btn1;
    @BindView(R.id.fragment2_btn2)
    Button fragment2Btn2;
    @BindView(R.id.fragment2_btn3)
    Button fragment2Btn3;
    @BindView(R.id.fragment2_btn4)
    Button fragment2Btn4;

    @Override
    protected int setLayout() {
        return R.layout.fragment_2;
    }

    @Override
    protected void initValue(Bundle bundle) {
    }

    @Override
    protected void main() {

    }

    @OnClick({R.id.fragment2_btn1, R.id.fragment2_btn2,
            R.id.fragment2_btn3, R.id.fragment2_btn4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment2_btn1://AudioRecord 录制

                break;
            case R.id.fragment2_btn2:

                break;
            case R.id.fragment2_btn3:

                break;
            case R.id.fragment2_btn4:


                break;
        }
    }
}
