package com.czh.basicframe.utils;

import android.view.Gravity;
import android.widget.Toast;

import com.czh.basicframe.base.BaseApplication;

/**
 * author  : czh
 * create Date : 2019/10/22  16:35
 * 详情 :
 */
public class ToastUtils {

    public static ToastUtils getInstance() {
        return ToastHolder.instance;
    }

    private static class ToastHolder {
        private static ToastUtils instance = new ToastUtils();
    }

    private Toast mShortToast, mLongToast;

    /**
     * 提示内容 短时间提示
     *
     * @param msg
     */
    public void shortToast(String msg) {
        if (mShortToast == null) {
            mShortToast = Toast.makeText(BaseApplication.getContext(), msg, Toast.LENGTH_SHORT);
        }
        mShortToast.setText(msg);
        mShortToast.setGravity(Gravity.CENTER, 0, 0);
        mShortToast.show();
    }

    public void shortToast(String err, int errCode) {
        if (errCode == -1) {
            //根据错误码做不同操作的地方。比如退出登录
        } else {
            shortToast(err);
        }
    }
    /**
     * 提示内容 长时间提示
     *
     * @param msg
     */
    public void longToast(String msg) {
        if (mLongToast == null) {
            mLongToast = Toast.makeText(BaseApplication.getContext(), msg, Toast.LENGTH_LONG);
        }
        mLongToast.setText(msg);
        mLongToast.setGravity(Gravity.CENTER, 0, 0);
        mLongToast.show();
    }

    public void longToast(String err, int errCode) {
        if (errCode == -1) {
            //根据错误码做不同操作的地方。比如退出登录
        } else {
            shortToast(err);
        }
    }


}
