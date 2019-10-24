package com.czh.basicframe.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;

import com.czh.basicframe.R;
import com.czh.basicframe.base.BaseApplication;

import java.io.File;
import java.io.IOException;

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

}
