package com.czh.basicframe;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * create by Chen
 * create date : 2019/12/6
 * desc :
 */
public class TestBean implements Parcelable {
    private String Id ;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Id);
    }

    public TestBean() {
    }

    protected TestBean(Parcel in) {
        this.Id = in.readString();
    }

    public static final Parcelable.Creator<TestBean> CREATOR = new Parcelable.Creator<TestBean>() {
        @Override
        public TestBean createFromParcel(Parcel source) {
            return new TestBean(source);
        }

        @Override
        public TestBean[] newArray(int size) {
            return new TestBean[size];
        }
    };
}
