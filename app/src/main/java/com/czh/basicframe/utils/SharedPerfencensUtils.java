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

    /////////////// get /////////////////////
    public String getString(String key) {
        throwEx();
        return sharedPreferences.getString(key, "");
    }

    public Long getLong(String key) {
        throwEx();
        return sharedPreferences.getLong(key, 0);
    }

    public float getFloat(String key) {
        throwEx();
        return sharedPreferences.getFloat(key, 0f);
    }

    public boolean getBoolean(String key) {
        throwEx();
        return sharedPreferences.getBoolean(key, false);
    }

    public int getInt(String key, int defaultValues) {
        throwEx();
        return sharedPreferences.getInt(key, defaultValues);
    }

    ////////////////// put ////////////////////

    public void putString(String key, String value) {
        throwEx();
        editor.putString(key, value);
        commit();
    }

    public void putInt(String key, int value) {
        throwEx();
        editor.putInt(key, value);
        commit();
    }

    public void putBoolean(String key, boolean value) {
        throwEx();
        editor.putBoolean(key, value);
        commit();
    }

    public void putLong(String key, long value) {
        throwEx();
        editor.putLong(key, value);
        commit();
    }

    public void putFloat(String key, float value) {
        throwEx();
        editor.putFloat(key, value);
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
            throw new NullPointerException("sharedPreferences is null , inflateLayout this class in BaseApplication !!!");
        }
    }

    /**
     * 提交 ： 同步提交到磁盘，有返回boolean, 效率低。
     */
    private void commit() {
        if (editor != null) {
            editor.commit();
        }
    }

    /**
     * 提交： 没有返回值，提交的时候放在内存，然后异步提交到磁盘，不会返回提交结果，不在乎结果的话可使用。 --- 效率高，异步，不返回结果。
     */
    private void apply() {
        if (editor != null) {
            editor.apply();
        }
    }
}
