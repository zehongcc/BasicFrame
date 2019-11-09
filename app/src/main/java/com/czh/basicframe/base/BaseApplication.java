package com.czh.basicframe.base;

import android.app.Application;
import android.content.Context;

import com.czh.basicframe.utils.CrashHandler;
import com.czh.basicframe.utils.LogUtils;
import com.czh.basicframe.utils.SharedPerfencensUtils;

import cn.jpush.im.android.api.JMessageClient;

/**
 * author  : czh
 * create Date : 2019/8/8  11:02
 * 详情 :
 */
public class BaseApplication extends Application {

    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        LogUtils.setIsDebug(true);
        SharedPerfencensUtils.getInstance().init(this);
        CrashHandler.getInstance().init(this);
        //极光IM
        JMessageClient.setDebugMode(true);
        JMessageClient.init(this);
    }

}
