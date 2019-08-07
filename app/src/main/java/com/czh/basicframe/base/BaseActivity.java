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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TAG = this.getLocalClassName();
    }

    private OnPermissionCallBack callBack;
    private int permissionCode;
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
            callBack.onSuccess();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == permissionCode) {
            boolean isSuccess =  true;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    isSuccess = false ;
                    break;
                }
            }
            if (isSuccess){
                callBack.onSuccess();
            }else {
                callBack.onRefuse();
            }
        }
    }

    protected interface OnPermissionCallBack {
        void onSuccess();

        void onRefuse();
    }


}
