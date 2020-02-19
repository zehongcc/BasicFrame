package com.czh.basicframe.https.base;

/**
 * create by Chen
 * create date : 2019/11/6
 * desc :
 */
public class BaseBean<T> {

    /**
     * code : 1
     * msg :
     * data : {"accType":"2","accTypeName":"平台管理员","nickName":"平台管理1","contact":"真实姓名3","token":"c4d53d929b8e4222b65a03476132a701","tokenOutTime":"2019-10-16 11:21:32"}
     */

    private int code;
    private String msg;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
