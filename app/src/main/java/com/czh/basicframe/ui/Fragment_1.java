package com.czh.basicframe.ui;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.czh.basicframe.R;
import com.czh.basicframe.base.BaseFragment;
import com.czh.basicframe.interfaces.OnCameraCallback;
import com.czh.basicframe.utils.FileUtils;
import com.czh.basicframe.utils.GlideUtils;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.FileCallback;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * create by Chen
 * create date : 2019/11/28
 * desc : 图片相关
 */
public class Fragment_1 extends BaseFragment {
    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;
    @BindView(R.id.btn3)
    Button btn3;
    @BindView(R.id.btn4)
    Button btn4;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.pic_iv1)
    ImageView picIv1;
    @BindView(R.id.pic_iv2)
    ImageView picIv2;

    @Override
    protected int setLayout() {
        return R.layout.fragment_1;
    }

    @Override
    protected void initValue(Bundle bundle) {

    }

    @Override
    protected void main() {

    }


    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn1://拍照
                openCamera(cameraCallback);
                break;
            case R.id.btn2://相册
                openAlbum(cameraCallback);
                break;
            case R.id.btn3://压缩
                tiny();
                break;
            case R.id.btn4://裁剪
                break;
        }
    }
    /**
     * tiny压缩
     */
    private void tiny() {
        if (sourcePicFile == null) return;
        Tiny.FileCompressOptions options = new Tiny.FileCompressOptions();
        Tiny.getInstance().source(sourcePicFile).asFile().withOptions(options).compress(new FileCallback() {
            @Override
            public void callback(boolean isSuccess, String outfile, Throwable t) {
                if (isSuccess) {
                    File file = new File(outfile);
                    GlideUtils.load(file, picIv2);
                    tv2.setText("处理后(" + FileUtils.getInstance().getFileSize(file) + ")");
                }
            }
        });
    }


    /**
     * 拍照相机回调
     */
    private File sourcePicFile;
    private Uri sourceUri ;
    private OnCameraCallback cameraCallback = new OnCameraCallback() {
        @Override
        public void onCameraCallBack(File file) {
            sourcePicFile = file;
            GlideUtils.load(file, picIv1);
            tv1.setText("原图(" + FileUtils.getInstance().getFileSize(file) + ")");
        }

        @Override
        public void onAblumCallBack(File file) {
            sourcePicFile = file;
            GlideUtils.load(file, picIv1);
            tv1.setText("原图(" + FileUtils.getInstance().getFileSize(file) + ")");
        }

        @Override
        public void onFail() {
            sourcePicFile = null;
            tv1.setText("原图(" + FileUtils.getInstance().getFileSize(sourcePicFile) + ")");
            GlideUtils.clear(picIv1);
        }
    };

}
