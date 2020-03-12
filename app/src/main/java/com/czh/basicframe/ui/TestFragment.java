package com.czh.basicframe.ui;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.czh.basicframe.FaceResourceMap;
import com.czh.basicframe.R;
import com.czh.basicframe.base.BaseFragment;
import com.czh.basicframe.interfaces.OnCameraCallback;
import com.czh.basicframe.utils.EventBean;
import com.czh.basicframe.utils.LogUtils;
import com.czh.basicframe.widget.dialog.NormDialog;

import java.io.File;
import java.io.FileInputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    @BindView(R.id.test_show_tv)
    TextView showTv;
    @BindView(R.id.emoji_et)
    EditText emojiEt;
    @BindView(R.id.emoji_viewPager)
    ViewPager viewPager ;

    @Override
    protected int setLayout() {
        return R.layout.fragment_test;
    }

    @Override
    protected void initValue(Bundle bundle) {
//        emojiEt.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int zoom, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int zoom, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                try {
//                    LogUtils.e(TAG," "+new String(s.toString().getBytes(),"IOS -Windows"));
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }


    @Override
    protected void main() {

    }





    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                //打开相册
                openAlbum(TestFragment.this, false);
                break;
            case R.id.btn2:
                //打开相机
                openCamera(TestFragment.this, false);
                break;
            case R.id.btn3:
                NormDialog dialog = new NormDialog.Builder(mContext)
                        .setTitle("温馨提示")
                        .setTitleColor(Color.BLUE)
                        .setContent("确认是否退出登录？")
                        .setOk("ok")
                        .setCancel("cancel")
                        .setOnClickListener((view1, result) -> toast.shortToast(result == 1 ? "OK" : "Cancel"))
                        .create();
                dialog.show();
                break;
            case R.id.btn4:
                throw new RuntimeException("测试测试");
            case R.id.btn5:
                String s = loadFromSDFile();
                showTv.setText(s);
                break;
            case R.id.btn6:
                String str = "1234[d_aini]jhhjkh khj[d_beishang][阿达我去[d_bishi]啊啊啊p[d_lei]";
                String pattern = "\\[d_([a-z|A-Z]+)\\]";
                String pattern2 = "\\ue[0-9][0-9]([0-9]|[a-f])" ;
                Pattern r = Pattern.compile(pattern);
                final Matcher m = r.matcher(str);
                SpannableString spannableStr = new SpannableString(str);
                int sIndex = 0;
                int blue = getContext().getResources().getColor(R.color.color_blue);
                int black = getContext().getResources().getColor(R.color.color_black);
                while (m.find()) {
                    spannableStr.setSpan(new ForegroundColorSpan(black), sIndex, m.start(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    spannableStr.setSpan(new ForegroundColorSpan(blue),  m.start(), m.end(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    spannableStr.setSpan(new ImageSpan(mContext,FaceResourceMap.getFace(m.group())), m.start(), m.end(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    sIndex = m.end();
                    LogUtils.e(TAG, ">>>>> 匹配到 >>>> " + m.group(0) +
                            ", " + m.start() + " , " +
                            m.end() + " ,, " + str.length());
                    LogUtils.e(TAG, ">>>> +sIndex = " + sIndex);
                }
                if (sIndex < str.length()) {
                    spannableStr.setSpan(new ForegroundColorSpan(black), sIndex, str.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                }
                emojiEt.setText(spannableStr);
                break;
            default:
                break;
        }
    }


    private String loadFromSDFile() {
        String result = null;
        try {
            File f = new File(mContext.getExternalCacheDir().getAbsolutePath() + "/CrashHandlder/cash.txt");
            int length = (int) f.length();
            byte[] buff = new byte[length];
            FileInputStream fin = new FileInputStream(f);
            fin.read(buff);
            fin.close();
            result = new String(buff, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
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
    public void onCrop(Bitmap bitmap) {
    }

    @Override
    public void onFail() {

    }

}
