package com.czh.basicframe.mvp.presenters;

import com.czh.basicframe.mvp.BasePresenter;
import com.czh.basicframe.mvp.BaseView;
import com.czh.basicframe.mvp.model.TestModel;

/**
 * author  : czh
 * create Date : 2019/10/22  16:02
 * 详情 :
 */
public class TestPresenter extends BasePresenter<BaseView.TestView>  {

    private TestModel model ;


    public void test(){
        mView.showDialog();
        model.test();
    }
}
