package com.czh.basicframe.utils;

/**
 * author  : czh
 * create Date : 2019/10/22  10:52
 * 详情 : event 消息
 */
public class EventBean {
    private int tag;
    private Object object;

    public EventBean(int tag, Object object) {
        this.tag = tag;
        this.object = object;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
