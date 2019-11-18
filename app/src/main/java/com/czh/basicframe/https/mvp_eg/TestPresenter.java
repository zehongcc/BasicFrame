package com.czh.basicframe.https.mvp_eg;

import com.czh.basicframe.https.base.BaseBean;
import com.czh.basicframe.https.base.BaseObserver;
import com.czh.basicframe.https.base.BasePresenter;

import junit.framework.Test;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * create by Chen
 * create date : 2019/11/18
 * desc :
 */
public class TestPresenter extends BasePresenter<TestControl.View> implements TestControl.Model {
    @Override
    public void test(String testParam) {
        getView().showDialog();
        service.test().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<TestBean>() {
                    @Override
                    protected void onSuccess(TestBean bean, String msg) {

                    }

                    @Override
                    protected void onFail(String err, int errorCode) {

                    }
                });
    }
}
