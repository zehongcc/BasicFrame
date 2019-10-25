package com.czh.basicframe.ui;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.czh.basicframe.R;
import com.czh.basicframe.base.BaseFragment;
import com.czh.basicframe.interfaces.DialogClickListener;
import com.czh.basicframe.interfaces.OnCameraCallback;
import com.czh.basicframe.utils.EventBean;
import com.czh.basicframe.utils.LogUtils;
import com.czh.basicframe.utils.PermissionUtils;
import com.czh.basicframe.widget.dialog.NormDialog;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author  : czh
 * create Date : 2019/10/22  10:09
 * 详情 :
 */
public class TestFragment extends BaseFragment implements OnCameraCallback {

    @BindView(R.id.fragment_iv)
    ImageView fragmentIv;
    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;
    @BindView(R.id.btn3)
    Button btn3;

    @Override
    protected int setLayout() {
        return R.layout.fragment_test;
    }

    @Override
    protected void initValue(Bundle bundle) {
    }

    @Override
    protected void main() {
    }

    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                PermissionUtils.getInstance().checkPermissions(getActivity(), new String[]{Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, 220, new PermissionUtils.OnPermissionCallBack() {
                    @Override
                    public void requestPermissionCallBack(boolean isSuccess, int requestCode) {
                        LogUtils.d("请求权限" + isSuccess + " , 请求码：" + requestCode);
                        if (isSuccess) {
                            //打开相册
                            openAlbum(TestFragment.this);
                        }
                    }
                });
                break;
            case R.id.btn2:
                PermissionUtils.getInstance().checkPermissions(getActivity(), new String[]{Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, 220, new PermissionUtils.OnPermissionCallBack() {
                    @Override
                    public void requestPermissionCallBack(boolean isSuccess, int requestCode) {
                        LogUtils.d("请求权限" + isSuccess + " , 请求码：" + requestCode);
                        if (isSuccess) {
                            //打开相机
                            openCamera(TestFragment.this);
                        }
                    }
                });
                break;
            case R.id.btn3:
                NormDialog dialog = new NormDialog.Builder(mContext)
                        .setTitle("aaa")
                        .setContent("bbb")
                        .setOk("ok")
                        .setCancel("cancel")
                        .setClickListener(new DialogClickListener() {
                            @Override
                            public void onClick(View view, int result) {
                                toast.shortToast(result == 1 ? "OK" : "Cancel");
                            }
                        })
                        .create();
                dialog.show();
                break;
        }
    }


    @Override
    protected void onFragmentEventBus(final EventBean object) {
        super.onFragmentEventBus(object);
    }

    @Override
    public void onCameraCallBack(File file) {
        Glide.with(this).load(file).into(fragmentIv);
    }

    @Override
    public void onAblumCallBack(File file) {
        Glide.with(this).load(file).into(fragmentIv);
    }

    @Override
    public void onFail() {

    }


}
