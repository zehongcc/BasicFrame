package com.czh.basicframe.interfaces;

import android.graphics.Bitmap;

import java.io.File;

/**
 * author  : czh
 * create Date : 2019/10/23  10:56
 * 详情 : 相机相册回调
 */
public interface OnCameraCallback {

    /**
     * 相机回调
     */
    void onCameraCallBack(File file);

    /**
     * 相册回调
     */
    void onAblumCallBack(File file);

    /**
     * 裁剪回调
     * @param bitmap
     */
    void onCrop(Bitmap bitmap);

    /**
     * onActivityResult 返回错误
     */
    void onFail();


}
