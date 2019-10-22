package com.czh.basicframe.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.czh.basicframe.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * author  : czh
 * create Date : 2019/10/22  9:53
 * 详情 :
 */
public class PermissionUtils {

    public static PermissionUtils getInstance() {
        return PermissionHolder.instance;
    }

    private static class PermissionHolder {
        private static PermissionUtils instance = new PermissionUtils();
    }

    private OnPermissionCallBack callBack;

    private int permissionCode;

    /**
     * 动态请求权限
     *
     * @param permissions
     * @param requestCode
     * @param c
     */
    public void checkPermissions(Activity activity, String[] permissions, int requestCode, OnPermissionCallBack c) {
        callBack = c;
        this.permissionCode = requestCode;
        List<String> pList = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(activity, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                pList.add(permissions[i]);
            }
        }
        if (pList.size() > 0) {
            String[] requestArray = new String[pList.size()];
            for (int i = 0; i < pList.size(); i++) {
                requestArray[i] = pList.get(i);
            }
            ActivityCompat.requestPermissions(activity, requestArray, requestCode);
        } else {
            callBack.requestPermissionCallBack(true, permissionCode);
        }
    }

    /**
     * 权限获取结果
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == permissionCode) {
            boolean isSuccess = true;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    isSuccess = false;
                    break;
                }
            }
            if (isSuccess) {
                callBack.requestPermissionCallBack(true, permissionCode);
            } else {
                callBack.requestPermissionCallBack(false, permissionCode);
            }
        }
    }

    public interface OnPermissionCallBack {
        void requestPermissionCallBack(boolean isSuccess, int requestCode);
    }

}
