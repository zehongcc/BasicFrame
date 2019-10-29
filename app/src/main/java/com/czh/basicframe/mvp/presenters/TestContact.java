package com.czh.basicframe.mvp.presenters;

import com.czh.basicframe.mvp.BaseModel;
import com.czh.basicframe.mvp.BaseView;

/**
 * author  : czh
 * create Date : 2019/10/26  9:17
 * 详情 :
 */
public interface TestContact  {

    interface View extends BaseView {
        void onTest();
    }

    interface Model {
        void test();
    }
}
