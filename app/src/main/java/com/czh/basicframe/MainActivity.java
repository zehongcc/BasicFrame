package com.czh.basicframe;

import android.Manifest;
import android.os.Bundle;
import android.view.View;

import com.czh.basicframe.base.BaseActivity;
import com.czh.basicframe.utils.LogUtils;

public class MainActivity extends BaseActivity implements BaseActivity.OnPermissionCallBack {

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    //请求权限
    public void toRequest(View view) {
        checkPermissions(new String[]{Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO}, 110, this);
    }

    @Override
    public void requestPermissionCallBack(boolean isSuccess, int requestCode) {
        LogUtils.d("请求权限" + isSuccess + " , 请求码：" + requestCode);
    }
}
