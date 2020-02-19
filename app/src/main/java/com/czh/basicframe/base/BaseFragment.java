package com.czh.basicframe.base;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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
import java.io.FileNotFoundException;
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

    protected Unbinder mUnbinder;

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
        mUnbinder = ButterKnife.bind(this, view);
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
     * 界面跳转
     *
     * @param cls 跳转的界面
     */
    protected void toAct(Class cls) {
        Intent intent = new Intent(mContext, cls);
        mContext.startActivity(intent);
    }

    /**
     * 带参数跳转界面
     *
     * @param bundle
     * @param cls
     */
    protected void toAct(Bundle bundle, Class cls) {
        Intent intent = new Intent(mContext, cls);
        intent.putExtra("bundle", bundle);
        mContext.startActivity(intent);
    }

    /**
     * 权限请求回调
     *
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
    private Uri picUri = null;//相机拍摄的Uri文件
    protected OnCameraCallback onCameraCallback;

    private boolean mIsCrop;//是否裁剪

    protected void openCamera(OnCameraCallback callback, boolean isCrop) {
        mIsCrop = isCrop;
        onCameraCallback = callback;
        PermissionUtils.getInstance().checkPermissions(mActivity, new String[]{Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE},
                991, new PermissionUtils.OnPermissionCallBack() {
                    @Override
                    public void requestPermissionCallBack(boolean isSuccess, int requestCode) {
                        if (!isSuccess) {
                            toast.shortToast("请打开相关权限");
                            return;
                        }
                        File pathFile = new File(mContext.getExternalCacheDir(), "pic");
                        if (!pathFile.exists()) {
                            pathFile.mkdirs();
                        }
                        photoFile = new File(pathFile, System.currentTimeMillis() + ".jpg");
                        if (photoFile.exists()) {
                            photoFile.delete();
                        }
                        try {
                            boolean b = photoFile.createNewFile();
                            LogUtils.e(TAG, "相机创建新文件是否成功 = " + b);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        picUri = null;
                        if (Build.VERSION.SDK_INT >= 24) {
                            picUri = FileProvider.getUriForFile(mContext,
                                    mContext.getPackageName() + ".FileProvider", photoFile);
                        } else {
                            picUri = Uri.fromFile(photoFile);
                        }
//                        LogUtils.e(TAG, "【相机创建的Uri】 = " + picUri);
                        BaseFragment.this.startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                                        .putExtra(MediaStore.EXTRA_OUTPUT, picUri),
                                Code.OPEN_CAMERA_F);

                    }
                });
    }

    /**
     * 打开相册
     */
    protected void openAlbum(OnCameraCallback callback, boolean isCrop) {
        mIsCrop = isCrop;
        onCameraCallback = callback;
        PermissionUtils.getInstance().checkPermissions(getActivity(), new String[]{Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, 992, new PermissionUtils.OnPermissionCallBack() {
            @Override
            public void requestPermissionCallBack(boolean isSuccess, int requestCode) {
                if (isSuccess) {
                    BaseFragment.this.startActivityForResult(new Intent(Intent.ACTION_PICK).setType("image/*"),
                            Code.OPEN_ALBUM_F);
                }
            }
        });
    }

    public File getPictureFile(Uri uri) {
//        LogUtils.e("getPictureFile == " + uri.toString());
        try {
            if (uri != null) {
                String s = uri.toString().split(":")[0];
                if (s.equals("file")) {//     file:///storage/emulated/0/MIUI/wallpaper/sb10063035c-001_%26_f7cd5d79-6e01-419a-b668-130c19b481db.jpg
                    return new File(new URI(uri.toString()));
                } else {//    content://media/external/images/media/33
                    String[] proj = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContext().getContentResolver().query(uri, proj, null,
                            null, null);
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    String filePath = cursor.getString(column_index);
                    cursor.close();
                    return new File(filePath);
                }
            }
        } catch (URISyntaxException e) {
//            LogUtils.e("Tools", "获取图图片出现异常");
            e.printStackTrace();
        }
//        LogUtils.e("Tools", "图库图片Uri空值");
        return null;
    }

    /**
     * 裁剪图片
     */
    private Uri cropUri;

    private void crop(Uri uri) {
        //com.android.camera.action.CROP，这个action是调用系统自带的图片裁切功能
        cropUri = Uri.fromFile(new File(mContext.getExternalCacheDir().getPath(), "crop_"+System.currentTimeMillis() + ".jpeg"));
        LogUtils.e(TAG, ">>>>>>>>>>>>>> 裁剪保存的图片地址 uri = " + cropUri);
        //
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setDataAndType(uri, "image/*");//裁剪的图片uri和图片类型
        intent.putExtra("crop", "true");//设置允许裁剪，如果不设置，就会跳过裁剪的过程，还可以设置putExtra("crop", "circle")
        intent.putExtra("aspectX", 1);//裁剪框的 X 方向的比例,需要为整数
        intent.putExtra("aspectY", 1);//裁剪框的 Y 方向的比例,需要为整数
        intent.putExtra("outputX", 340);//返回数据的时候的X像素大小。--- 实验证明350应该是清晰度最高的时候
        intent.putExtra("outputY", 340);//返回数据的时候的Y像素大小。--- 实验证明350应该是清晰度最高的时候
        //Android 对Intent中所包含数据的大小是有限制的，一般不能超过 1M，否则会使用缩略图 ,所以我们要指定输出裁剪的图片路径
        //开启临时访问的读和写权限
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);//是否将数据保留在Bitmap中返回
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());//输出格式，一般设为Bitmap格式及图片类型
//        intent.putExtra("noFaceDetection", true);
        this.startActivityForResult(intent, Code.CROP_PIC);//裁剪完成的标识
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Code.OPEN_CAMERA_F://相机回调
                    LogUtils.e(TAG, ">>>>>> 相机回调 mIsCrop = " + mIsCrop);
                    if (mIsCrop) {
                        crop(picUri);
                    }
//                    else
                    if (onCameraCallback != null) {
                        onCameraCallback.onCameraCallBack(photoFile);
                    }
                    break;
                case Code.OPEN_ALBUM_F://相册回调
                    LogUtils.e(TAG, ">>>>>> 相册回调 mIsCrop = " + mIsCrop);
                    Uri uri = data.getData();
                    if (mIsCrop) {
                        crop(uri);
                    }
//                    else
                    if (onCameraCallback != null) {
                        onCameraCallback.onAblumCallBack(getPictureFile(uri));
                    }
                    break;
                case Code.CROP_PIC:
//                    LogUtils.e(TAG, "裁剪成功返回的数据: ");
////                     TODO: 2019/11/29  way1
////                     intent.putExtra("return-data", true);//设置为true时候使用
//                    if (onCameraCallback != null) {
//                        Bundle bundle = data.getExtras();
//                        if (bundle != null) {
//                            //在这里获得了剪裁后的Bitmap对象，可以用于上传
//                            Bitmap bitmap = bundle.getParcelable("data");
//                            //设置到ImageView上
//                            onCameraCallback.onCrop(bitmap);
//                        }
//                    }
                    // TODO: 2019/11/29  way2 -- 将uri转成bitmap,前提是intent.putExtra("return-data", false)
                    if (onCameraCallback != null && cropUri != null) {
                        try {
                            Bitmap bitmap = BitmapFactory.decodeStream(mContext.getContentResolver().openInputStream(cropUri));
                            onCameraCallback.onCrop(bitmap);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
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
                case Code.CROP_PIC://裁剪失败
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
        mUnbinder.unbind();
    }

}
