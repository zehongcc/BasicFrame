package com.czh.basicframe.utils;

import android.os.CountDownTimer;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.czh.basicframe.R;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * author  : czh
 * create Date : 2019/10/23  9:53
 * 详情 :
 */
public class Tools {
    private final String TAG = "【Tools】";

    public static Tools getInstance() {
        return ToolsHolder.instance;
    }

    private static class ToolsHolder {
        private static Tools instance = new Tools();

    }

    /**
     * 1、设置密码是否可见
     */
    public void setPasswordVisibility(EditText editText, ImageView showIv) {
        if (EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD == editText.getInputType()) {
            //如果不可见就设置为可见
            editText.setInputType(EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            showIv.setImageResource(R.drawable.icon_eye_hide);
        } else {
            //如果可见就设置为不可见
            editText.setInputType(EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            showIv.setImageResource(R.drawable.icon_eye_show);
        }
        //执行上面的代码后光标会处于输入框的最前方,所以把光标位置挪到文字的最后面
        editText.setSelection(editText.getText().toString().length());
    }

    public long lastClick = 0;

    /**
     * 2、快速点击判断
     * @param x
     * @return
     */
    public boolean fastClick(int x) {
        if (System.currentTimeMillis() - lastClick <= (x * 1000)) {
            return true;
        }
        lastClick = System.currentTimeMillis();
        return false;
    }

    public boolean fastClick() {
        if (System.currentTimeMillis() - lastClick <= 1000) {
            return true;
        }
        lastClick = System.currentTimeMillis();
        return false;
    }


    private CountDownTimer countDownTimer;

    /**
     * 3 倒计时
     * @param view 显示内容的view
     * @param millisInFuture 倒计时总时间
     * @param countDownInterval 倒计时时间间隔
     * @return 把倒计时对象返回去在引用界面的onDestory()里面销毁。
     */
    public CountDownTimer countDown(View view, long millisInFuture, long countDownInterval) {
        countDownTimer = new CountDownTimer(millisInFuture, countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                ((TextView) view).setText(millisInFuture / 1000 + "s");
            }

            @Override
            public void onFinish() {
                ((TextView) view).setText("重新获取");
                view.setClickable(true);
            }
        }.start();
        view.setClickable(false);
        return countDownTimer;
    }


    public String getIpAddress(){
        try {
            for (Enumeration<NetworkInterface> enNetI = NetworkInterface.getNetworkInterfaces(); enNetI
                    .hasMoreElements();) {
                NetworkInterface netI = enNetI.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = netI.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (inetAddress instanceof Inet4Address &&!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "";
    }
}
