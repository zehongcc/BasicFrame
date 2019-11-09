package com.czh.basicframe.https.base;

import com.czh.basicframe.https.MyService;
import com.czh.basicframe.https.RetrofitUtils;

import java.lang.ref.SoftReference;

/**
 * create by Chen
 * create date : 2019/11/6
 * desc :
 */
public abstract class BasePresenter<V extends BaseView> {

    public static MyService service = RetrofitUtils.getInstance().getService();

    private SoftReference<V> mReference;


    public void acttech(BaseView view) {
        this.mReference = new SoftReference(view);
    }

    protected V getView() {
        return mReference.get();
    }

    public void detach() {
        if (mReference != null) {
            mReference.clear();
            mReference = null;
        }
    }
}
