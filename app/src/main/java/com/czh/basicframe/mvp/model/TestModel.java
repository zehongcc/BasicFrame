package com.czh.basicframe.mvp.model;

import com.czh.basicframe.https.UrlUtils;
import com.czh.basicframe.mvp.BaseModel;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * author  : czh
 * create Date : 2019/10/22  16:03
 * 详情 :
 */
public class TestModel extends BaseModel implements ImpModel.Imp_Test {
    @Override
    public void test() {
        service.test(UrlUtils.IP)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object o) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
