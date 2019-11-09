package com.czh.basicframe.https.base;

/**
 * create by Chen
 * create date : 2019/11/6
 * desc :
 */
public interface BaseView {
    void showDialog();
    void hideDialog();
    void onFail(String err, int errCode);
}
