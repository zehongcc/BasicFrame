package com.czh.basicframe.base;

import android.app.Application;
import android.content.Context;

import com.czh.basicframe.utils.LogUtils;

/**
 * author  : czh
 * create Date : 2019/8/8  11:02
 * 详情 :
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.setIsDebug(true);
    }


}
