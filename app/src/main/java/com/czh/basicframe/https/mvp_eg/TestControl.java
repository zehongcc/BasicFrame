package com.czh.basicframe.https.mvp_eg;

import com.czh.basicframe.https.base.BaseView;

/**
 * create by Chen
 * create date : 2019/11/18
 * desc :
 */
public interface TestControl {

    interface View extends BaseView{
        void onTest();
    }

    interface Model {
        void test(String testParam);
    }
}
