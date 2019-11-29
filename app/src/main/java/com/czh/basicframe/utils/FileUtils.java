package com.czh.basicframe.utils;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.text.DecimalFormat;

/**
 * create by Chen
 * create date : 2019/11/28
 * desc :
 */
public class FileUtils {
    private final String TAG = "[FileUtils]";
    private final String B = "B";
    private final String KB = "KB";
    private final String MB = "MB";
    private final String G = "G";

    public static FileUtils getInstance() {
        return FileUtilsHolder.instance;
    }

    private static class FileUtilsHolder {
        private static final FileUtils instance = new FileUtils();
    }

    /**
     * 获取文件大小
     *
     * @param file 文件
     * @return
     */
    public String getFileSize(File file) {
        String size = "0B";
        if (file == null) {
            return size;
        }
        long length = file.length();
        DecimalFormat df = new DecimalFormat("#.00");
//        LogUtils.e(TAG,">>>> 文件大小 >>> "+length);
        if (length < 1024) {
            size = df.format((double) length) + B;
        } else if (length < 1048576) {
            size = df.format((double) length / 1024) + KB;
        } else if (length < 1073741824) {
            size = df.format((double) length / 1048576) + MB;
        } else {
            size = df.format((double) length / 1073741824) + G;
        }
        return size;
    }

    /**
     * user转换为file文件
     * 返回值为file类型
     *
     * @param uri
     * @return
     */
    public File uri2File(Activity activity , Uri uri) {
        String img_path;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor actualimagecursor = activity.managedQuery(uri, proj, null,
                null, null);
        if (actualimagecursor == null) {
            img_path = uri.getPath();
        } else {
            int actual_image_column_index = actualimagecursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            actualimagecursor.moveToFirst();
            img_path = actualimagecursor
                    .getString(actual_image_column_index);
        }
        File file = new File(img_path);
        return file;
    }

}
