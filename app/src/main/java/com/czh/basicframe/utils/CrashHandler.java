package com.czh.basicframe.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * create by Chen
 * create date : 2019/10/31
 * desc : 系统异常捕获，并保存到本地 。 参考 https://blog.csdn.net/gavin_zippo/article/details/79557959
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private final String TAG = "CrashHandler";

    private Context context;

    private Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler;

    public static CrashHandler getInstance() {
        return CrashHolder.instance;
    }

    private static class CrashHolder {
        private static final CrashHandler instance = new CrashHandler();
    }

    public void init(Context c) {
        this.context = c;
        //获取当前默认的exceptionHandler 并保存在全局
        defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        //将当前CrashHandler替换掉原来的。
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {//发生异常会调用此方法
        //保存异常信息
        saveException(t, e);
        //异常处理
        if (defaultUncaughtExceptionHandler != null) {
            //系统异常的handler存在就交给系统处理。
            defaultUncaughtExceptionHandler.uncaughtException(t, e);
        } else {
            //否则自己杀死进程
            Process.killProcess(Process.myPid());
        }

    }

    /**
     * 保存异常信息
     *
     * @param t
     * @param e
     */
    private void saveException(Thread t, Throwable e) {
//        //判断当前SD卡的挂载状态
//        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {//MEDIA_MOUNTED ：存在SD卡并且可读可写
//            Log.i(TAG, "current device no sdCard , can't save exception .");
//            return;
//        }
        //保存到缓存路径下面
        String path = context.getExternalCacheDir().getAbsolutePath() + "/CrashHandlder/";
        LogUtils.i(TAG, "異常信息保存地址：" + path);

        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        String time = TimeUtils.getInstance().getCurrentTime("yyyy-MM-dd HH:mm:ss");

        File logFile = new File(path, "cash.txt");

        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(logFile)));
            pw.println(time);
            saveDeviceMsg(pw);//保存本设备信息
            pw.println();
            e.printStackTrace(pw);//吧具体内容打印出来
            pw.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    //保存当前设备信息
    private void saveDeviceMsg(PrintWriter pw) {
        PackageManager pm = context.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (packageInfo != null) {
                //当前APP的版本
                pw.print("APP版本:");
                pw.print(packageInfo.versionName);
                pw.print("_版本号:");
                pw.println(packageInfo.versionCode);
                //设备系统版本n
                pw.print("系统版本：");
                pw.print(Build.VERSION.RELEASE);
                pw.print("_");
                pw.println(Build.VERSION.SDK_INT);
                //手机制造商
                pw.print("手机制造商: ");
                pw.println(Build.MANUFACTURER);
                //手机型号
                pw.print("Model: ");
                pw.println(Build.MODEL);
                //cpu架构
                pw.print("CPU ABI: ");
                pw.println(Build.CPU_ABI);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
