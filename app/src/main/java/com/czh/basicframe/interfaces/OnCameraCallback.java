package com.czh.basicframe.interfaces;

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
     * onActivityResult 返回错误
     */
    void onFail();

}
