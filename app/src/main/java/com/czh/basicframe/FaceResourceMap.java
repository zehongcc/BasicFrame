package com.czh.basicframe;

import com.czh.basicframe.utils.LogUtils;

import java.lang.reflect.Field;

/**
 * create by Chen
 * create date : 2019/12/3
 * desc :
 */
public class FaceResourceMap {

    public static int getFace(String name) {
        R.mipmap drawable = new R.mipmap();
        String newName = name.substring(1, name.length() - 1);
        LogUtils.e("FACE ",">>>>> name = "+newName);
        Field field = null;
        int resId = 0x7f02000b;
        try {
            field = R.mipmap.class.getField(newName);
            resId = (int) field.get(drawable);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        LogUtils.e("FACE ", " ..  resId = "+resId+" , "+R.mipmap.d_aini);
        return resId;
    }

}
