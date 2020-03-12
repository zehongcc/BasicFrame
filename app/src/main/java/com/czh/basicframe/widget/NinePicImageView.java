package com.czh.basicframe.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.czh.basicframe.R;
import com.czh.basicframe.utils.DensityUtil;
import com.czh.basicframe.utils.LogUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * create by Chen
 * create date : 2019/11/22
 * desc : 九宫格图片 -- 微信头像。
 */
public class NinePicImageView extends View {
    private final String TAG = "NinePicImageView";
    private int defalutHeight;
    private int mWidth, mHeight;

    private Context mContext = getContext();

    private int mPicWidth, mPicHeight, mPadding;

    private Paint mPaint;


    public NinePicImageView(Context context) {
        this(context, null);
    }

    public NinePicImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NinePicImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        defalutHeight = DensityUtil.dip2px(mContext, 50);
        mPadding = DensityUtil.dip2px(mContext, 1);
        mPaint = new Paint();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureHeight(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        LogUtils.e(TAG, " onsizeChanged >> " + DensityUtil.px2dip(mContext, w) + " , " + DensityUtil.px2dip(mContext, h));
        mWidth = w;
        mHeight = h;
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
        long sTime = System.currentTimeMillis();
        //背景
        canvas.drawColor(mContext.getResources().getColor(R.color.color_bg));
        if (bitmapList == null || bitmapList.size() == 0) {
            return;
        }
        //绘制头像 -- 会根据 RectF的规定大小做缩放。
        int size = bitmapList.size();
        switch (size) {
            case 1:
                canvas.drawBitmap(bitmapList.get(0), null,
                        new RectF(mPadding, mPadding, mPicWidth - mPadding, mPicHeight - mPadding), mPaint);
                break;
            case 2:
                int offsetH = (mHeight - 2 * mPadding - mPicHeight) / 2;
                canvas.drawBitmap(bitmapList.get(0), null, new RectF(mPadding, mPadding + offsetH, mPadding + mPicWidth, mPadding + offsetH + mPicHeight), mPaint);
                canvas.drawBitmap(bitmapList.get(1), null, new RectF(mPadding * 2 + mPicWidth, mPadding + offsetH, mWidth - mPadding, mPadding + offsetH + mPicHeight), mPaint);
                break;
            case 3:
                int offsetX = (mWidth - 2 * mPadding - mPicWidth) / 2;
                //第一张
                canvas.drawBitmap(bitmapList.get(0), null,
                        new RectF(mPadding + offsetX, mPadding, mPadding + offsetX + mPicWidth, mPadding + mPicHeight), mPaint);
                //第二。三张
                canvas.drawBitmap(bitmapList.get(1), null, new RectF(mPadding, mPadding * 2 + mPicHeight, mPadding + mPicWidth, mHeight - mPadding), mPaint);
                canvas.drawBitmap(bitmapList.get(2), null, new RectF(mPadding * 2 + mPicWidth, mPadding * 2 + mPicHeight, mWidth - mPadding, mHeight - mPadding), mPaint);
                break;
            case 4:
                canvas.drawBitmap(bitmapList.get(0), null,
                        new RectF(mPadding, mPadding, mPadding + mPicWidth, mPadding + mPicHeight), mPaint);
                canvas.drawBitmap(bitmapList.get(1), null,
                        new RectF(mPadding * 2 + mPicWidth, mPadding, mWidth - mPadding, mPadding + mPicHeight), mPaint);
                canvas.drawBitmap(bitmapList.get(2), null,
                        new RectF(mPadding, mPadding * 2 + mPicHeight, mPadding + mPicWidth, mHeight - mPadding), mPaint);
                canvas.drawBitmap(bitmapList.get(3), null,
                        new RectF(mPadding * 2 + mPicWidth, mPadding * 2 + mPicHeight, mWidth - mPadding, mHeight - mPadding), mPaint);
                break;
            case 5:
                int offset5 = (mWidth - 3 * mPadding - mPicWidth * 2) / 2;
                canvas.drawBitmap(bitmapList.get(0), null,
                        new RectF(mPadding + offset5, mPadding + offset5, mPadding + offset5 + mPicWidth, mPadding + offset5 + mPicHeight), mPaint);
                canvas.drawBitmap(bitmapList.get(1), null,
                        new RectF(mPadding * 2 + offset5 + mPicWidth, mPadding + offset5, mWidth - mPadding - offset5, mPadding + mPicHeight + offset5), mPaint);
                canvas.drawBitmap(bitmapList.get(2), null,
                        new RectF(mPadding, mHeight - mPadding - offset5 - mPicHeight, mPadding + mPicWidth, mHeight - mPadding - offset5), mPaint);
                canvas.drawBitmap(bitmapList.get(3), null,
                        new RectF(mPadding * 2 + mPicWidth, mHeight - mPadding - offset5 - mPicHeight, mPadding * 2 + mPicWidth * 2, mHeight - mPadding - offset5), mPaint);
                canvas.drawBitmap(bitmapList.get(4), null,
                        new RectF(mPadding * 3 + mPicWidth * 2, mHeight - mPadding - offset5 - mPicHeight, mWidth - mPadding, mHeight - mPadding - offset5), mPaint);
                break;
            case 6:
                int offset6 = (mWidth - 3 * mPadding - mPicWidth * 2) / 2;
                canvas.drawBitmap(bitmapList.get(0), null,
                        new RectF(mPadding, mPadding + offset6, mPadding + mPicWidth, mPadding + mPicHeight + offset6), mPaint);
                canvas.drawBitmap(bitmapList.get(1), null,
                        new RectF(mPadding * 2 + mPicWidth, mPadding + offset6, mPadding * 2 + mPicWidth * 2, mPadding + mPicHeight + offset6), mPaint);
                canvas.drawBitmap(bitmapList.get(2), null,
                        new RectF(mPadding * 3 + mPicWidth * 2, mPadding + offset6, mWidth - mPadding, mPadding + mPicHeight + offset6), mPaint);
                canvas.drawBitmap(bitmapList.get(3), null,
                        new RectF(mPadding, mHeight - mPadding - offset6 - mPicHeight, mPadding + mPicWidth, mHeight - mPadding - offset6), mPaint);
                canvas.drawBitmap(bitmapList.get(4), null,
                        new RectF(mPadding * 2 + mPicWidth, mHeight - mPadding - offset6 - mPicHeight, mPadding * 2 + mPicWidth * 2, mHeight - mPadding - offset6), mPaint);
                canvas.drawBitmap(bitmapList.get(5), null,
                        new RectF(mPadding * 3 + mPicWidth * 2, mHeight - mPadding - offset6 - mPicHeight, mWidth - mPadding, mHeight - mPadding - offset6), mPaint);
                break;
            case 7:
                canvas.drawBitmap(bitmapList.get(0), null,
                        new RectF(mPadding * 2 + mPicWidth, mPadding, mPadding * 2 + mPicWidth * 2, mPadding + mPicHeight), mPaint);

                canvas.drawBitmap(bitmapList.get(1), null,
                        new RectF(mPadding, mPadding * 2 + mPicHeight, mPadding + mPicWidth, mPadding * 2 + mPicHeight * 2), mPaint);
                canvas.drawBitmap(bitmapList.get(2), null,
                        new RectF(mPadding * 2 + mPicWidth, mPadding * 2 + mPicHeight, mPadding * 2 + mPicWidth * 2, mPadding * 2 + mPicHeight * 2), mPaint);
                canvas.drawBitmap(bitmapList.get(3), null,
                        new RectF(mPadding * 3 + mPicWidth * 2, mPadding * 2 + mPicHeight, mWidth - mPadding, mPadding * 2 + mPicHeight * 2), mPaint);

                canvas.drawBitmap(bitmapList.get(4), null,
                        new RectF(mPadding, mPadding * 3 + mPicHeight * 2, mPadding + mPicWidth, mHeight - mPadding), mPaint);
                canvas.drawBitmap(bitmapList.get(5), null,
                        new RectF(mPadding * 2 + mPicWidth, mPadding * 3 + mPicHeight * 2, mPadding * 2 + mPicWidth * 2, mHeight - mPadding), mPaint);
                canvas.drawBitmap(bitmapList.get(6), null,
                        new RectF(mPadding * 3 + mPicWidth * 2, mPadding * 3 + mPicHeight * 2, mWidth - mPadding, mHeight - mPadding), mPaint);

                break;
            case 8:
                int offset8 = (mWidth - 3 * mPadding - mPicWidth * 2) / 2;
                canvas.drawBitmap(bitmapList.get(0), null,
                        new RectF(mPadding + offset8, mPadding, mPadding + offset8 + mPicWidth, mPadding + mPicHeight), mPaint);
                canvas.drawBitmap(bitmapList.get(1), null,
                        new RectF(mPadding * 2 + offset8 + mPicWidth, mPadding, mWidth - mPadding - offset8, mPadding + mPicHeight), mPaint);

                canvas.drawBitmap(bitmapList.get(2), null,
                        new RectF(mPadding, mPadding * 2 + mPicHeight, mPadding + mPicWidth, mPadding * 2 + mPicHeight * 2), mPaint);
                canvas.drawBitmap(bitmapList.get(3), null,
                        new RectF(mPadding * 2 + mPicWidth, mPadding * 2 + mPicHeight, mPadding * 2 + mPicWidth * 2, mPadding * 2 + mPicHeight * 2), mPaint);
                canvas.drawBitmap(bitmapList.get(4), null,
                        new RectF(mPadding * 3 + mPicWidth * 2, mPadding * 2 + mPicHeight, mWidth - mPadding, mPadding * 2 + mPicHeight * 2), mPaint);

                canvas.drawBitmap(bitmapList.get(5), null,
                        new RectF(mPadding, mPadding * 3 + mPicHeight * 2, mPadding + mPicWidth, mHeight - mPadding), mPaint);
                canvas.drawBitmap(bitmapList.get(6), null,
                        new RectF(mPadding * 2 + mPicWidth, mPadding * 3 + mPicHeight * 2, mPadding * 2 + mPicWidth * 2, mHeight - mPadding), mPaint);
                canvas.drawBitmap(bitmapList.get(7), null,
                        new RectF(mPadding * 3 + mPicWidth * 2, mPadding * 3 + mPicHeight * 2, mWidth - mPadding, mHeight - mPadding), mPaint);

                break;
            case 9:
            default:
                canvas.drawBitmap(bitmapList.get(0), null,
                        new RectF(mPadding, mPadding, mPadding + mPicWidth, mPadding + mPicHeight), mPaint);
                canvas.drawBitmap(bitmapList.get(1), null,
                        new RectF(mPadding * 2 + mPicWidth, mPadding, mPadding * 2 + mPicWidth * 2, mPadding + mPicHeight), mPaint);
                canvas.drawBitmap(bitmapList.get(2), null,
                        new RectF(mPadding * 3 + mPicWidth * 2, mPadding, mWidth - mPadding, mPadding + mPicHeight), mPaint);

                canvas.drawBitmap(bitmapList.get(3), null,
                        new RectF(mPadding, mPadding * 2 + mPicHeight, mPadding + mPicWidth, mPadding * 2 + mPicHeight * 2), mPaint);
                canvas.drawBitmap(bitmapList.get(4), null,
                        new RectF(mPadding * 2 + mPicWidth, mPadding * 2 + mPicHeight, mPadding * 2 + mPicWidth * 2, mPadding * 2 + mPicHeight * 2), mPaint);
                canvas.drawBitmap(bitmapList.get(5), null,
                        new RectF(mPadding * 3 + mPicWidth * 2, mPadding * 2 + mPicHeight, mWidth - mPadding, mPadding * 2 + mPicHeight * 2), mPaint);

                canvas.drawBitmap(bitmapList.get(6), null,
                        new RectF(mPadding, mPadding * 3 + mPicHeight * 2, mPadding + mPicWidth, mHeight - mPadding), mPaint);
                canvas.drawBitmap(bitmapList.get(7), null,
                        new RectF(mPadding * 2 + mPicWidth, mPadding * 3 + mPicHeight * 2, mPadding * 2 + mPicWidth * 2, mHeight - mPadding), mPaint);
                canvas.drawBitmap(bitmapList.get(8), null,
                        new RectF(mPadding * 3 + mPicWidth * 2, mPadding * 3 + mPicHeight * 2, mWidth - mPadding, mHeight - mPadding), mPaint);
                break;
        }
        //
        long eTime = System.currentTimeMillis();
        LogUtils.e(TAG, "onDraw() time ====== " + (eTime - sTime) + "ms");
    }


    /**
     * 设置数据源
     */
//    //这个发现在列表当中出现复用问题
//    public void setUrls(List<String> urls) {
//        if (urls == null || urls.size() == 0) {
//            //没有头像的情况..
//            return;
//        }
//        List<File> files = new ArrayList<>();
//        //有头像的情况
//        long startTime = System.currentTimeMillis();
//        new Thread(() -> {
//            for (int i = 0; i < urls.size(); i++) {
//                FutureTarget<File> target = Glide.with(mContext)
//                        .asFile()
//                        .load(urls.get(i))
//                        .submit();
//                try {
//                    final File imageFile = target.get();
//                    files.add(imageFile);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//            initPicWidthAndHeight(files);
//            long endTime = System.currentTimeMillis();
//            LogUtils.e(TAG, ">>>>>>>>>>> " + (endTime - startTime) + "ms" + " ,,, " + files.size());
//        }).zoom();
//    }

    public void setUrls(List<File> list) {
        if (list == null || list.size() == 0) return;
        initPicWidthAndHeight(list);
    }

    private List<Bitmap> bitmapList = new ArrayList<>();




    /**
     * 初始头像宽高
     *
     * @param fileList
     */
    private void initPicWidthAndHeight(List<File> fileList) {
        int size = fileList.size();
        //根据头像数量的多少设置头像的宽高
        switch (size) {
            case 1:
                mPicWidth = mWidth;
                mPicHeight = mPicWidth;
                break;
            case 2://中间一行两个
            case 3://第一行1个，第二行2个
            case 4://两行两个
                mPicWidth = (mWidth - 3 * mPadding) / 2;//左右间隔+中间的分割线
                mPicHeight = mPicWidth;
                break;
            case 5://1行2个，1行三个
            case 6:
            case 7:
            case 8:
            case 9:
            default:
                mPicWidth = (mWidth - 4 * mPadding) / 3;
                mPicHeight = mPicWidth;
                break;
        }
        bitmapList.clear();
        //将对应头像文件缩放
        for (int i = 0; i < size; i++) {
            Bitmap bitmap = BitmapFactory.decodeFile(fileList.get(i).toString());
//            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, mPicWidth, mPicHeight, true);//缩放
            bitmapList.add(bitmap);
        }
//        postInvalidate();
        invalidate();
    }
}
