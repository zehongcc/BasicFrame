package com.czh.basicframe.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.czh.basicframe.base.BaseApplication;

/**
 * create by Chen
 * create date : 2019/11/4
 * desc :
 */
public class GlideUtils {

    public static void load(Object object, ImageView imageView) {
        RequestOptions options = new RequestOptions();
        Glide.with(BaseApplication.getContext()).load(object).apply(options).into(imageView);
    }

    public static void load(Object object, ImageView imageView, int err) {
        RequestOptions options = new RequestOptions();
        options.error(err) ;
        Glide.with(BaseApplication.getContext()).load(object).apply(options).into(imageView);
    }

    public static void clear(ImageView imageView){
        Glide.with(BaseApplication.getContext()).clear(imageView);
    }

}
