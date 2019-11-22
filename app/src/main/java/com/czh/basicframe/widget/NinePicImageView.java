package com.czh.basicframe.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.Target;
import com.czh.basicframe.R;
import com.czh.basicframe.utils.DensityUtil;
import com.czh.basicframe.utils.LogUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * create by Chen
 * create date : 2019/11/22
 * desc : 九宫格图片
 */
public class NinePicImageView extends View {
    private final String TAG = "NinePicImageView";
    private int defalutHeight;
    private int mWidth ,mHeight;

    private Context mContext = getContext();

    private int mPadding;//间隔


    public NinePicImageView(Context context) {
        this(context, null);
    }

    public NinePicImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NinePicImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        List<String> test = new ArrayList<>();
        test.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1671398372,3381203579&fm=26&gp=0.jpg");
        test.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3998889803,535011003&fm=26&gp=0.jpg");
        test.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1302601015,3873955939&fm=26&gp=0.jpg");
        test.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3620678974,412273927&fm=26&gp=0.jpg");
        test.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1485081754,2164381578&fm=26&gp=0.jpg");
        test.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3687293927,3730859401&fm=26&gp=0.jpg");
        test.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2638329283,1152876418&fm=11&gp=0.jpg");
        test.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3551856573,95373944&fm=26&gp=0.jpg");
        test.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2106686232,3200710859&fm=26&gp=0.jpg");
        setUrls(test);
    }

    private void initPaint() {
        defalutHeight = DensityUtil.dip2px(mContext, 50);
        mPadding = DensityUtil.dip2px(mContext, 2);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureHeight(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        LogUtils.e(TAG, " onsizeChanged >> " + DensityUtil.px2dip(mContext, w) + " , " + DensityUtil.px2dip(mContext, h));
        mWidth= w;
        mHeight = h ;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    private int measureHeight(int heightMeasureSpec) {
        int result = 0; //结果
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);
        switch (specMode) {
            case MeasureSpec.AT_MOST:  // 子容器可以是声明大小内的任意大小
                result = specSize;
                break;
            case MeasureSpec.EXACTLY: //父容器已经为子容器设置了尺寸,子容器应当服从这些边界,不论子容器想要多大的空间.  比如EditTextView中的DrawLeft
                result = specSize;
                break;
            case MeasureSpec.UNSPECIFIED:  //父容器对于子容器没有任何限制,子容器想要多大就多大. 所以完全取决于子view的大小
                result = defalutHeight;
                break;
            default:
                break;
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //背景
        canvas.drawColor(mContext.getResources().getColor(R.color.color_bg));
        Bitmap bitmap = BitmapFactory.decodeFile(files.get(0).toString());
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, mWidth, mHeight, true);//缩放
        canvas.drawBitmap(scaledBitmap,0,0,new Paint());
    }


    List<File> files = new ArrayList<>();

    public void setUrls(List<String> urls) {
        long startTime = System.currentTimeMillis();
        new Thread(() -> {
            for (int i = 0; i < urls.size(); i++) {
                FutureTarget<File> target = Glide.with(mContext)
                        .asFile()
                        .load(urls.get(i))
                        .submit();
                try {
                    final File imageFile = target.get();
                    files.add(imageFile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            long endTime = System.currentTimeMillis();
            LogUtils.e(TAG, ">>>>>>>>>>> " + (endTime - startTime)+"ms"+" ,,, "+files.size());
        }).start();
    }


}
