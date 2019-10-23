package com.czh.basicframe.base;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;

import com.czh.basicframe.interfaces.OnCameraCallback;
import com.czh.basicframe.utils.EventBean;
import com.czh.basicframe.utils.LogUtils;
import com.czh.basicframe.utils.PermissionUtils;
import com.czh.basicframe.utils.ToastUtils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

import static com.czh.basicframe.utils.Code.OPEN_ALBUM;
import static com.czh.basicframe.utils.Code.OPEN_CAMERA;

/**
 * author  : czh
 * create Date : 2019/8/7  17:25
 * 详情 :
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected String TAG;

    protected Activity mActivity;

    protected Context mContext;

    protected ToastUtils toast;

    protected OnCameraCallback onCameraCallback;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayout());
        initValues();
        init(savedInstanceState);
        main();
    }

    protected abstract int setLayout();

    protected abstract void init(Bundle savedInstanceState);

    protected abstract void main();

    private void initValues() {
        TAG = this.getLocalClassName();
        mActivity = this;
        mContext = this;
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        toast = ToastUtils.getInstance();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //权限回调
        PermissionUtils.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    /**
     * 打开相机
     */
    private File photoFile;//相机拍摄的文件

    protected void openCamera(final OnCameraCallback callback) {
        PermissionUtils.getInstance().checkPermissions(this, new String[]{Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, 22, new PermissionUtils.OnPermissionCallBack() {
            @Override
            public void requestPermissionCallBack(boolean isSuccess, int requestCode) {
                if (isSuccess) {
                    onCameraCallback = callback;
                    File pathFile = new File(Environment.getExternalStorageDirectory(), "照相");
                    if (!pathFile.exists()) {
                        pathFile.mkdir();
                    }
                    photoFile = new File(pathFile, "photo.jpg");
                    if (photoFile.exists()) {
                        photoFile.delete();
                    }
                    try {
                        boolean b = photoFile.createNewFile();
//                        LogUtils.e(TAG, "相机创建新文件是否成功 = " + b);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Uri picUri = null;
                    if (Build.VERSION.SDK_INT >= 24) {
                        picUri = FileProvider.getUriForFile(BaseApplication.getContext(),
                                BaseApplication.getContext().getPackageName() + ".FileProvider", photoFile);
                    } else {
                        picUri = Uri.fromFile(photoFile);
                    }
                    LogUtils.e(TAG, "【相机创建的Uri】 = " + picUri);
                    startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                            .putExtra(MediaStore.EXTRA_OUTPUT, picUri), OPEN_CAMERA);
                }else {
                    toast.shortToast("无法打开相机，请开启相关权限");
                }
            }
        });
    }

    /**
     * 打开相册
     */
    protected void openAlbum(final OnCameraCallback callback) {
        PermissionUtils.getInstance().checkPermissions(this, new String[]{Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, 11, new PermissionUtils.OnPermissionCallBack() {
            @Override
            public void requestPermissionCallBack(boolean isSuccess, int requestCode) {
                if (isSuccess) {
                    onCameraCallback = callback;
                    startActivityForResult(new Intent(Intent.ACTION_PICK).setType("image/*"), OPEN_ALBUM);
                }else {
                    toast.shortToast("无法打开相册，请开启相关权限");
                }
            }
        });

    }

    public File getPictureFile(Uri uri) {
        LogUtils.e("getPictureFile == " + uri.toString());
        try {
            if (uri != null) {
                String s = uri.toString().split(":")[0];
                if (s.equals("file")) {//     file:///storage/emulated/0/MIUI/wallpaper/sb10063035c-001_%26_f7cd5d79-6e01-419a-b668-130c19b481db.jpg
                    return new File(new URI(uri.toString()));
                } else {//    content://media/external/images/media/33
                    String[] proj = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(uri, proj, null, null, null);
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    String filePath = cursor.getString(column_index);
                    cursor.close();
                    return new File(filePath);
                }
            }
        } catch (URISyntaxException e) {
            LogUtils.e("Tools", "获取图图片出现异常");
            e.printStackTrace();
        }
        LogUtils.e("Tools", "图库图片Uri空值");
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtils.e(TAG, "   !!!!!!!  " + requestCode);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case OPEN_CAMERA://相机回调
                    if (onCameraCallback != null) onCameraCallback.onCameraCallBack(photoFile);
                    break;
                case OPEN_ALBUM://相册回调
                    if (onCameraCallback != null)
                        onCameraCallback.onAblumCallBack(getPictureFile(data.getData()));
                    break;
            }
        } else {
            switch (requestCode) {
                case OPEN_CAMERA://相机
                case OPEN_ALBUM://相册
                    if (onCameraCallback != null) {
                        onCameraCallback.onFail();
                    }
                    break;
            }
        }
    }

    /**
     * @param object
     * @Subscribe(threadMode = ThreadMode.Async)
     * @Subscribe(threadMode = ThreadMode.BackgroundThread)
     * @Subscribe(threadMode = ThreadMode.PostThread)
     */
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEventBus(EventBean object) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


}
