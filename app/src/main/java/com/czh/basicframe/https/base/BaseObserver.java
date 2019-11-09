package com.czh.basicframe.https.base;


import android.text.TextUtils;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * create by Chen
 * create date : 2019/11/6
 * desc :
 */
public abstract class BaseObserver<T> implements Observer<BaseBean<T>> {
    public static final String CONNECT_EXCEPTION = "网络连接异常，请检查您的网络状态";
    public static final String SOCKET_TIMEOUT_EXCEPTION = "网络连接超时，请检查您的网络状态，稍后重试";
    public static final String UNKNOWN_HOST_EXCEPTION = "网络异常，请检查您的网络状态";
    public static final String HTTP_NOT_FOUND = "Error:服务器错误";
    public static final String HTTP_SERVICE_BUSY = "服务器繁忙";

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onNext(BaseBean<T> t) {
        String msg = t.getMsg();
        int code = t.getCode();
        if (code == 1) {
            onSuccess(t.getData(), msg);
        } else {
            onFail(msg, code);
        }
    }

    @Override
    public void onError(Throwable t) {
        //处理常见的几种连接错误
        if (t instanceof SocketTimeoutException) {
            onFail("" + SOCKET_TIMEOUT_EXCEPTION, 111);
        } else if (t instanceof ConnectException) {
            onFail("" + CONNECT_EXCEPTION, 111);
        } else if (t instanceof UnknownHostException) {
            onFail("" + UNKNOWN_HOST_EXCEPTION, 111);
        } else {
            try {
                String message = t.getMessage();
                if (!TextUtils.isEmpty(message) && (message.equals("HTTP 404 Not Found")
                        || message.equals("HTTP 500 Internal Server Error"))) {
                    onFail("" + HTTP_NOT_FOUND, 111);
                } else if (!TextUtils.isEmpty(message) && message.equals("HTTP 503 Server Too Busy")) {
                    onFail("" + HTTP_SERVICE_BUSY, 111);
                } else {
                    throw new RuntimeException("" + message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected abstract void onSuccess(T t, String msg);

    protected abstract void onFail(String err, int errorCode);

}
