package com.czh.basicframe.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.czh.basicframe.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * author  :  Czh
 * create Date : 2018/5/17  15:30
 * 详情 :
 */
public class SharedPerfencensUtils {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private static class SpHolder {
        private static SharedPerfencensUtils instance = new SharedPerfencensUtils();
    }

    public static SharedPerfencensUtils getInstance() {
        return SpHolder.instance;
    }

    /**
     * application中初始化
     */
    public void init(Context context) {
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public String getString(String key) {
        throwEx();
        return sharedPreferences.getString(key, "");
    }

    public int getInt(String key, int defaultValues) {
        throwEx();
        return sharedPreferences.getInt(key, defaultValues);
    }

    public void putInt(String key, int value) {
        throwEx();
        editor.putInt(key, value);
        commit();
    }

    public boolean getBoolean(String key) {
        throwEx();
        return sharedPreferences.getBoolean(key, false);
    }

    public void putString(String key, String value) {
        throwEx();
        editor.putString(key, value);
        commit();
    }

    public void putBoolean(String key, boolean value) {
        throwEx();
        editor.putBoolean(key, value);
        commit();
    }

    public void remove(String key) {
        throwEx();
        editor.remove(key);
        commit();
    }


    /**
     * 抛出异常
     */
    private void throwEx() {
        if (sharedPreferences == null || editor == null) {
            throw new NullPointerException("sharedPreferences is null , init this class in application !!!");
        }
    }

    /**
     * 提交
     */
    private void commit() {
        if (editor != null) {
            editor.apply();
            editor.commit();
        }
    }
}
