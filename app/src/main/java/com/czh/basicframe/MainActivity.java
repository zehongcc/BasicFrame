package com.czh.basicframe;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.czh.basicframe.base.BaseActivity;
import com.czh.basicframe.db.SQHelper;
import com.czh.basicframe.interfaces.DialogClickListener;
import com.czh.basicframe.interfaces.OnCameraCallback;
import com.czh.basicframe.utils.EventBean;
import com.czh.basicframe.utils.LogUtils;
import com.czh.basicframe.utils.PermissionUtils;
import com.czh.basicframe.widget.dialog.NormDialog;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

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
                NormDialog dialog = new NormDialog.Builder(this)
                        .setTitle("测试标题")
                        .setContent("测试内容")
                        .setOkColor(Color.YELLOW)
                        .setCancelTvColor(Color.RED)
                        .setTitleColor(Color.GREEN)
                        .setContentColor(Color.BLUE)
                        .setOkClickListener(new DialogClickListener() {
                            @Override
                            public void onClick(View view, int result) {
                                toast.shortToast(result == 1 ? "确定" : "取消");
                            }
                        })
                        .create();
                dialog.show();
                break;
            case R.id.btn2:
                break;
            case R.id.btn3:
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
    }

    @Override
    public void onAblumCallBack(File file) {
        //相册获取图片回调
    }

    @Override
    public void onFail() {
        //相机或者相册获取失败/取消
        LogUtils.e(TAG, ">>>> 相机或者相册获取失败/取消 >>>> ");
    }
}
