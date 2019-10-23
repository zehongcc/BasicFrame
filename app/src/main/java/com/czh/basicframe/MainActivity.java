package com.czh.basicframe;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.czh.basicframe.base.BaseActivity;
import com.czh.basicframe.interfaces.OnCameraCallback;
import com.czh.basicframe.utils.EventBean;
import com.czh.basicframe.utils.LogUtils;
import com.czh.basicframe.utils.PermissionUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements OnCameraCallback {

    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;
    @BindView(R.id.btn3)
    Button btn3;
    @BindView(R.id.main_iv)
    ImageView picIv;

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    private TestFragment testFragment;

    @Override
    protected void init(Bundle savedInstanceState) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        testFragment = new TestFragment();
        transaction.add(R.id.frameLayout, testFragment);
        transaction.commit();
    }

    @Override
    protected void main() {
        //
    }

    //请求权限
    public void toRequest() {
        PermissionUtils.getInstance().checkPermissions(this, new String[]{Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO}, 110, new PermissionUtils.OnPermissionCallBack() {
            @Override
            public void requestPermissionCallBack(boolean isSuccess, int requestCode) {
                LogUtils.d("请求权限" + isSuccess + " , 请求码：" + requestCode);
            }
        });
    }

    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                break;
            case R.id.btn2:
                PermissionUtils.getInstance().checkPermissions(this, new String[]{Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, 11, new PermissionUtils.OnPermissionCallBack() {
                    @Override
                    public void requestPermissionCallBack(boolean isSuccess, int requestCode) {
                        if (isSuccess) {
                            //打开相册
                            openAlbum(MainActivity.this);
                        }
                    }
                });

                break;
            case R.id.btn3:
                //打开相机
                openCamera(MainActivity.this);
                break;
        }
    }

    @Override
    public void onEventBus(EventBean object) {
        super.onEventBus(object);
        if (object == null) return;
        String content = (String) object.getObject();
        int tag = object.getTag();

    }

    @Override
    public void onCameraCallBack(File file) {
        //相机拍照回调
        Glide.with(this).load(file).into(picIv);
    }

    @Override
    public void onAblumCallBack(File file) {
        //相册获取图片回调
        Glide.with(this).load(file).into(picIv);
    }

    @Override
    public void onFail() {
        //相机或者相册获取失败/取消
        LogUtils.e(TAG, ">>>> 相机或者相册获取失败/取消 >>>> ");
    }
}
