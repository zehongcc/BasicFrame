package com.czh.basicframe.mvp;

import java.lang.ref.SoftReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * author  : czh
 * create Date : 2019/10/22  15:42
 * 详情 :
 */
public abstract class BasePresenter<T extends BaseView> extends BaseModel implements ImpPresenter {

    protected T mView;

    private SoftReference<BaseView> mReferenceView;

    /**
     * @param view
     * @link https://www.cnblogs.com/perfei456/p/8962167.html
     */
    @SuppressWarnings("unchecked")//抑制没有进行类型检查操作的警告
    @Override
    public void acttech(BaseView view) {
        mReferenceView = new SoftReference<>(view);
        mView = (T) Proxy.newProxyInstance(view.getClass().getClassLoader(),
                view.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if (mReferenceView == null || mReferenceView.get() == null) {
                            return null;
                        }
                        return method.invoke(mReferenceView.get(), args);
                    }
                });
    }

    @SuppressWarnings("unchecked")
    public T getView() {
        return mView;
    }

    @Override
    public void detech(BaseView view) {
        mReferenceView.clear();
        mReferenceView = null;
    }
}
