package com.czh.basicframe;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.czh.basicframe.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION}, 11, new OnPermissionCallBack() {
            @Override
            public void onSuccess() {
                Log.e(TAG, "onSuccess: >>>>  允许" );
            }

            @Override
            public void onRefuse() {
                Log.e(TAG, "onSuccess: >>>>  拒绝" );
            }
        });
    }


}
