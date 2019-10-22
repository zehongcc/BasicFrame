package com.czh.basicframe.mvp;

/**
 * author  : czh
 * create Date : 2019/10/22  15:40
 * 详情 :
 */
public interface BaseView {
    void onFail(String err, String msg);

    void showDialog();

    void hideDialog();


    interface TestView extends BaseView {
        void onTest();
    }

}
