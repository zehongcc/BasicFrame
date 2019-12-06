package com.czh.basicframe.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.czh.basicframe.R;
import com.czh.basicframe.base.BaseFragment;
import com.czh.basicframe.interfaces.OnCameraCallback;
import com.czh.basicframe.utils.FileUtils;
import com.czh.basicframe.utils.GlideUtils;
import com.czh.basicframe.utils.LogUtils;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.FileCallback;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    CheckBox btn4;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.pic_iv1)
    ImageView picIv1;
    @BindView(R.id.pic_iv2)
    ImageView picIv2;
    @BindView(R.id.btn5)
    Button btn5;
    @BindView(R.id.test_content_tv)
    TextView contentTv;

    private boolean isCrop;

    @Override
    protected int setLayout() {
        return R.layout.fragment_1;
    }

    @Override
    protected void initValue(Bundle bundle) {
        btn4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isCrop = isChecked;
            }
        });
    }

    @Override
    protected void main() {

    }


    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn5})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn1://拍照
                openCamera(cameraCallback, isCrop);
                break;
            case R.id.btn2://相册
                openAlbum(cameraCallback, isCrop);
                break;
            case R.id.btn3://压缩
                tiny();
                break;
            case R.id.btn5://
//                toAct(Activity_Camera.class);
                String str = "2019年11月4日，工业和信息化部展开《APP》侵犯用户权益专项《整治》行动。即日起，腾讯《应用宝》将根据新隐私权限审核规则，" +
                        "对应用宝内所有应用进行审核。具体审核细节如下对应用宝内所有应用进行审核。具体审核细节如下对应用宝内所有应用进行审核。" +
                        "具体审核细节如下对应用宝内所有应用进行审核。具体审核细节如下对应用宝内所有应用进行审核。具体审核细节如下对应用宝内所有应用进行审核。" +
                        "具体审核细节如下对应用宝内所有应用进行审核。具体审核细节如下对应用宝内所有应用进行审核。具体审核细节如下对应用宝内所有应用进行审核。" +
                        "具体审核细节如下对应用宝内所有应用进行审核。具体审核细节如下对应用宝内所有应用进行审核。具体审核细节如下对应用宝内所有应用进行审核。" +
                        "具体审核细节如下对应用宝内所有应用进行审核。具体审核细节如下对应用宝内所有应用进行审核。具体审核细节如下对应用宝内所有应用进行审核。" +
                        "具体审核细节如下对应用宝内所有应用进行审核。具体审核细节如下对应用宝内所有应用进行审核。具体审核细节如下对应用宝内所有应用进行审核。" +
                        "具体审核细节如下对应用宝内所有应用进行审核。具体审核细节如下对应用宝内所有应用进行审核。具体审核细节如下：";
                String pattern = "《(.[^》]+)》";
                Pattern r = Pattern.compile(pattern);
                final Matcher m = r.matcher(str);
                SpannableString spannableStr = new SpannableString(str);
                int sIndex = 0;
                int blue = getContext().getResources().getColor(R.color.color_blue);
                int black = getContext().getResources().getColor(R.color.color_black);
                while (m.find()) {
                    spannableStr.setSpan(new ForegroundColorSpan(black), sIndex, m.start(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    spannableStr.setSpan(new MyClickSpan(m.group()), m.start(), m.end(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    spannableStr.setSpan(new ForegroundColorSpan(blue), m.start(), m.end(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    sIndex = m.end();
                    LogUtils.e(TAG, ">>>>> 匹配到 >>>> " + m.group(0) +
                            ", " + m.start() + " , " +
                            m.end() + " ,, " + str.length());
                    LogUtils.e(TAG, ">>>> +sIndex = " + sIndex);
                }
                if (sIndex < str.length()) {
                    spannableStr.setSpan(new ForegroundColorSpan(black), sIndex, str.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                }
                contentTv.setMovementMethod(LinkMovementMethod.getInstance());
                contentTv.setText(spannableStr);
                break;
        }
    }

    private class MyClickSpan extends ClickableSpan {
        private String text;

        public MyClickSpan(String text) {
            this.text = text;
        }

        @Override
        public void onClick(@NonNull View widget) {
            LogUtils.e(TAG, ">>>>> 点击了 >>>>> " + text);
            toast.shortToast(text);
        }

        @Override
        public void updateDrawState(@NonNull TextPaint ds) {
            // 文字不变色
            ds.setUnderlineText(false);
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
                    tv2.setText("压缩后(" + FileUtils.getInstance().getFileSize(file) + ")");
                }
            }
        });
    }


    /**
     * 拍照相机回调
     */
    private File sourcePicFile;
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
        public void onCrop(Bitmap bitmap) {
            tv2.setText("裁剪后(" + FileUtils.getInstance().getBitmapSize(bitmap) + ")");
            GlideUtils.load(bitmap, picIv2);
        }

        @Override
        public void onFail() {
            sourcePicFile = null;
            tv1.setText("原图(" + FileUtils.getInstance().getFileSize(sourcePicFile) + ")");
            GlideUtils.clear(picIv1);
        }
    };

}
