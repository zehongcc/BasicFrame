package com.czh.basicframe.mvp.presenters;

import com.czh.basicframe.mvp.BasePresenter;

/**
 * author  : czh
 * create Date : 2019/10/22  16:02
 * 详情 :
 */
public class TestPresenter extends BasePresenter<TestContact.View> implements TestContact.Model {
    @Override
    public void test() {
        mView.showDialog();
        service.test("");
    }
}
