package com.czh.basicframe.utils;

/**
 * author  : czh
 * create Date : 2019/10/24  13:46
 * 详情 :
 */
public class DialogUtils {
    public static DialogUtils getInstance() {
        return DialogHolder.instance;
    }

    private static class DialogHolder {
        private static final DialogUtils instance = new DialogUtils();
    }


}
