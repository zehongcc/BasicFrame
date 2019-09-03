package com.czh.basicframe.utils;

import android.util.Log;

/**
 * author  : czh
 * create Date : 2019/7/25  11:06
 * 详情 : 日志输出工具类
 * 在application调用setIsDebug()方法，以便全局控制日志输出。
 */
public class LogUtils {

    private static final String defaultTag = "LogUtils";

    private static boolean isDebug;

    /*发布到线上的版本时设置false*/
    public static void setIsDebug(boolean isDebug) {
        LogUtils.isDebug = isDebug;
    }

    public static void e(String tag, String msg) {
        if (isDebug)
            Log.e(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (isDebug)
            Log.i(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug)
            Log.d(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (isDebug)
            Log.v(tag, msg);
    }

    public static void e(String msg) {
        if (isDebug)
            Log.e(defaultTag, msg);
    }

    public static void i(String msg) {
        if (isDebug)
            Log.i(defaultTag, msg);
    }

    public static void d(String msg) {
        if (isDebug)
            Log.d(defaultTag, msg);
    }

    public static void v(String msg) {
        if (isDebug)
            Log.v(defaultTag, msg);
    }
}
