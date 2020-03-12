package com.czh.basicframe.calendar;

import android.text.TextUtils;

/**
 * author  : czh
 * create Date : 2019/8/20  14:04
 * 详情 :
 */
public class CCalendarBean {
    private String day;
    private boolean isSelect;

    public CCalendarBean(String day, boolean isSelect) {
        this.day = day;
        this.isSelect = isSelect;
    }

    //当前日期是否可以点击
    public boolean isDayClickable;

    public boolean isDayClickable() {
        return isDayClickable;
    }

    public void setDayClickable(boolean dayClickable) {
        isDayClickable = dayClickable;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    //是否可以点击 -- 针对空白数据的
    public boolean isEnable() {
        return !TextUtils.isEmpty(getDay()) ? true : false;
    }
}
