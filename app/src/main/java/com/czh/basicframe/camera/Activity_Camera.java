package com.czh.basicframe.camera;

import android.graphics.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;

import com.czh.basicframe.R;
import com.czh.basicframe.base.BaseActivity;
import com.czh.basicframe.https.base.BasePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * create by Chen
 * create date : 2019/12/2
 * desc :
 */
public class Activity_Camera extends BaseActivity implements SurfaceHolder.Callback {

    @BindView(R.id.surface_View)
    SurfaceView surfaceView;
    @BindView(R.id.back_iv)
    ImageView backIv;
    @BindView(R.id.btn_iv)
    ImageView btnIv;
    private Camera mCamera;
    private SurfaceHolder mHolder;


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_camera;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mHolder = surfaceView.getHolder();
        mHolder.addCallback(this);
    }

    @Override
    protected void main() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back_iv, R.id.btn_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                break;
            case R.id.btn_iv:
                break;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
