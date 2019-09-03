package com.czh.basicframe.base;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * author  : czh
 * create Date : 2019/8/7  17:25
 * 详情 :
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected String TAG ;

    private OnPermissionCallBack callBack;
    private int permissionCode;

    protected abstract int setLayout();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayout());
        TAG = this.getLocalClassName();
    }

    /**
     * 动态请求权限
     * @param permissions
     * @param requestCode
     * @param c
     */
    protected void checkPermissions(String[] permissions, int requestCode, OnPermissionCallBack c) {
        callBack = c;
        this.permissionCode = requestCode;
        List<String> pList = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                pList.add(permissions[i]);
            }
        }
        if (pList.size() > 0) {
            String[] requestArray = new String[pList.size()];
            for (int i = 0; i < pList.size(); i++) {
                requestArray[i] = pList.get(i);
            }
            ActivityCompat.requestPermissions(this, requestArray, requestCode);
        } else {
            callBack.requestPermissionCallBack(true,permissionCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == permissionCode) {
            boolean isSuccess =  true;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    isSuccess = false ;
                    break;
                }
            }
            if (isSuccess){
                callBack.requestPermissionCallBack(true,permissionCode);
            }else {
                callBack.requestPermissionCallBack(false,permissionCode);
            }
        }
    }

    public interface OnPermissionCallBack {
        void requestPermissionCallBack(boolean isSuccess , int requestCode);
    }
}
