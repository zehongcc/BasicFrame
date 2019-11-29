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
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;

import com.czh.basicframe.https.base.BasePresenter;
import com.czh.basicframe.https.base.BaseView;
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
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements BaseView {

    protected String TAG;

    protected Activity mActivity;

    protected Context mContext;

    protected ToastUtils toast;

    protected OnCameraCallback onCameraCallback;

    protected T mPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayout());
        initValues();
        init(savedInstanceState);
        main();
    }

    protected abstract T createPresenter();

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
        //
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.acttech(this);
        }
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
    private boolean mIsCrop;//是否裁剪
    private Uri picUri;//相机拍摄的Uri

    protected void openCamera(final OnCameraCallback callback, boolean isCrop) {
        onCameraCallback = callback;
        mIsCrop = isCrop;
        PermissionUtils.getInstance().checkPermissions(this, new String[]{Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, 22, new PermissionUtils.OnPermissionCallBack() {
            @Override
            public void requestPermissionCallBack(boolean isSuccess, int requestCode) {
                if (isSuccess) {
                    File pathFile = new File(mContext.getExternalCacheDir(), "照相");
                    if (!pathFile.exists()) {
                        pathFile.mkdirs();
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
                    picUri = null;
                    if (Build.VERSION.SDK_INT >= 24) {
                        picUri = FileProvider.getUriForFile(mContext,
                                mContext.getPackageName() + ".FileProvider", photoFile);
                    } else {
                        picUri = Uri.fromFile(photoFile);
                    }
                    LogUtils.e(TAG, "【相机创建的Uri】 = " + picUri);
                    startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                            .putExtra(MediaStore.EXTRA_OUTPUT, picUri), OPEN_CAMERA);
                } else {
                    toast.shortToast("无法打开相机，请开启相关权限");
                }
            }
        });
    }

    /**
     * 打开相册
     */
    protected void openAlbum(final OnCameraCallback callback, boolean isCrop) {
        onCameraCallback = callback;
        mIsCrop = isCrop;
        PermissionUtils.getInstance().checkPermissions(this, new String[]{Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, 11, new PermissionUtils.OnPermissionCallBack() {
            @Override
            public void requestPermissionCallBack(boolean isSuccess, int requestCode) {
                if (isSuccess) {
                    startActivityForResult(new Intent(Intent.ACTION_PICK).setType("image/*"), OPEN_ALBUM);
                } else {
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case OPEN_CAMERA://相机回调
                    LogUtils.e(TAG, ">>>>>>[activity] 相机回调 mIsCrop = " + mIsCrop);
                    if (mIsCrop) {
                        crop(picUri);
                    }
//                    else
                    if (onCameraCallback != null) {
                        onCameraCallback.onCameraCallBack(photoFile);
                    }
                    break;
                case OPEN_ALBUM://相册回调
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
                    LogUtils.e(TAG, "裁剪成功返回的数据: ");
////                     TODO: 2019/11/29  way1 -- AV画质 有待处理
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
        if (mPresenter != null) {
            mPresenter.detach();
        }
    }

    private long firstPressedTime = 0L;

    @Override
    public void onBackPressed() {
        if (isAble) {
            long pressedTime = System.currentTimeMillis();
            if (pressedTime - firstPressedTime > 2000) {
                toast.shortToast("再按一次退出程序");
                firstPressedTime = pressedTime;
            } else {
                System.exit(0);
            }
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 设置是否双击退出
     */
    private boolean isAble;

    protected void setIsBackAble(boolean isAble) {
        this.isAble = isAble;
    }

    @Override
    public void showDialog() {
//        if (loadingDialog == null) {
//            loadingDialog = new LoadingDialog(this);
//        }
//        loadingDialog.show();
    }

    @Override
    public void hideDialog() {
//        if (loadingDialog != null) {
//            loadingDialog.dismiss();
//        }
    }

    @Override
    public void onFail(String err, int errCode) {
        toast.shortToast(err, errCode);
    }


}
