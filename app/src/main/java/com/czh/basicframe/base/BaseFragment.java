package com.czh.basicframe.base;

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
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.czh.basicframe.interfaces.OnCameraCallback;
import com.czh.basicframe.utils.Code;
import com.czh.basicframe.utils.EventBean;
import com.czh.basicframe.utils.LogUtils;
import com.czh.basicframe.utils.PermissionUtils;
import com.czh.basicframe.utils.ToastUtils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

/**
 * author  : czh
 * create Date : 2019/10/22  9:37
 * 详情 :
 */
public abstract class BaseFragment extends Fragment {

    protected String TAG;

    protected abstract int setLayout();

    protected abstract void initValue(Bundle bundle);

    protected abstract void main();

    protected Context mContext;

    protected Activity mActivity;

    protected View mView;

    protected Unbinder unbinder;

    protected ToastUtils toast;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
        TAG = getClass().getSimpleName();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(setLayout(), container, false);
        mView = view;
        unbinder = ButterKnife.bind(this, view);
        init();
        initValue(savedInstanceState);
        main();
        return view;
    }

    /**
     * 初始化一些工具类
     */
    private void init() {
        mContext = getContext();
        toast = ToastUtils.getInstance();
    }

    /**
     * 权限请求回调
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
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

    protected OnCameraCallback onCameraCallback;

    protected void openCamera(OnCameraCallback callback) {
        this.onCameraCallback = callback;
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
//            LogUtils.e(TAG, "相机创建新文件是否成功 = " + b);
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
        this.startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        .putExtra(MediaStore.EXTRA_OUTPUT, picUri),
                Code.OPEN_CAMERA_F);
    }

    /**
     * 打开相册
     */
    protected void openAlbum(OnCameraCallback callback) {
        this.onCameraCallback = callback;
        this.startActivityForResult(new Intent(Intent.ACTION_PICK).setType("image/*"), Code.OPEN_ALBUM_F);
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
                    Cursor cursor = getContext().getContentResolver().query(uri, proj, null, null, null);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Code.OPEN_CAMERA_F://相机回调
                    if (onCameraCallback != null) onCameraCallback.onCameraCallBack(photoFile);
                    break;
                case Code.OPEN_ALBUM_F://相册回调
                    if (onCameraCallback != null)
                        onCameraCallback.onAblumCallBack(getPictureFile(data.getData()));
                    break;
            }
        } else {
            switch (requestCode) {
                case Code.OPEN_CAMERA_F://相机
                case Code.OPEN_ALBUM_F://相册
                    if (onCameraCallback != null) {
                        onCameraCallback.onFail();
                    }
                    break;
            }
        }
    }

    /**
     * 收到的eventBus消息
     *
     * @param object
     */
    protected void onFragmentEventBus(EventBean object) {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
