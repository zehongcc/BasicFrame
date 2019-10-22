package com.czh.basicframe.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.Toast;

import com.czh.basicframe.mvp.BasePresenter;
import com.czh.basicframe.mvp.BaseView;
import com.czh.basicframe.utils.EventBean;
import com.czh.basicframe.utils.PermissionUtils;
import com.czh.basicframe.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * author  : czh
 * create Date : 2019/8/7  17:25
 * 详情 :
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected String TAG;

    protected PermissionUtils permissionUtils;

    protected Activity mActivity;

    protected Context mContext;

    protected ToastUtils toast;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayout());
        initValues();
        init(savedInstanceState);
        main();
    }

    protected abstract int setLayout();

    protected abstract void init(Bundle savedInstanceState);

    protected abstract void main();

    private void initValues() {
        TAG = this.getLocalClassName();
        permissionUtils = PermissionUtils.getInstance();
        mActivity = this;
        mContext = this;
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        toast = ToastUtils.getInstance();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //权限回调
        PermissionUtils.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * @param object
     * @Subscribe(threadMode = ThreadMode.Async)
     * @Subscribe(threadMode = ThreadMode.BackgroundThread)
     * @Subscribe(threadMode = ThreadMode.PostThread)
     */
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEventBus(EventBean object) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }



}
