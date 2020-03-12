package com.czh.basicframe.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.czh.basicframe.R;
import com.czh.basicframe.utils.DensityUtil;
import com.czh.basicframe.utils.LogUtils;

/**
 * create by Chen
 * create date : 2019/12/16
 * desc :
 */
public class CLoadingView extends View {
    private final String TAG = "【CLoadingView】";

    private int defalutHeight;

    private int mWindth, mHeight;

    private int mRadius;

    private Paint mPaint;

    private int mMoveLength;

    private int mPadding;

    private float mSweepAngle;

    private boolean isStart = true, isMove, isSpin;


    public CLoadingView(Context context) {
        this(context, null);
    }

    public CLoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        defalutHeight = DensityUtil.dip2px(context, 80);
        mPaint = new Paint();
        mPaint.setColor(getContext().getResources().getColor(R.color.color_blue));
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(DensityUtil.dip2px(context, 10));
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureHeight(widthMeasureSpec), measureHeight(heightMeasureSpec));
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
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mWindth = getWidth();
        mHeight = getHeight();
        mPadding = DensityUtil.dip2px(getContext(), 10);
        mRadius = (mWindth - mPadding) / 2;
        mMoveLength = (mHeight - mPadding) / 2;
        LogUtils.e(TAG, "onLayout() >>>>> mWidth = " + mWindth + " , mHeight = " + mHeight + ",  mRadius " + mRadius + " , mMoveLength = " + mMoveLength);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawColor(getContext().getResources().getColor(R.color.color_a_line));
        //画外圈的圆 -- 第一步缩放到圆心成点
        if (isStart) {
            canvas.drawCircle(mWindth / 2, mHeight / 2, mRadius, mPaint);
        }
        //原点向上滑动 -- 第二步 mMoveLength : 移动的距离
        if (isMove) {
            canvas.drawPoint(mWindth / 2, mMoveLength + mPadding/2, mPaint);
        }
        //第三步-爱的魔力转圈圈
        if (isSpin) {
            canvas.drawArc(new RectF(0 + mPadding / 2, 0 + mPadding / 2, mWindth - mPadding / 2, mHeight - mPadding / 2),
                    275, mSweepAngle, false, mPaint);
        }
    }

    private ValueAnimator zoomAnimator;

    public void zoom() {
        if (zoomAnimator != null && zoomAnimator.isStarted()) {
            LogUtils.e(">>>>>>>>>> zoom() >>>>>>>> ");
            zoomAnimator.pause();
            zoomAnimator = null;
        }
        isStart = true;
        isMove = false;
        isSpin = false;
        mRadius = (mWindth - mPadding) / 2;
        zoomAnimator = ValueAnimator.ofInt(mRadius, 1);
        zoomAnimator.setDuration(300);
        zoomAnimator.setRepeatCount(0);//设置动画应重复多少次。 如果重复count为0，则动画永远不会重复。
        zoomAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (int) animation.getAnimatedValue();
//                LogUtils.e(TAG, ">>>>>>>>>>>> " + animatedValue + " , dp = " + DensityUtil.px2dip(getContext(), (int) animatedValue));
                mRadius = animatedValue;
                invalidate();
                if (animatedValue == 1) {//结束
                    isStart = false;
                    isMove = true;
                    move();
                }
            }
        });
        zoomAnimator.start();
    }

    private ValueAnimator moveAnimator;

    public void move() {
        if (moveAnimator != null && moveAnimator.isStarted()) {
            LogUtils.e(">>>>>>>>>> move() >>>>>>>> ");
            moveAnimator.pause();
            moveAnimator = null;
        }
        mMoveLength = (mHeight - mPadding * 2) / 2 ;
        moveAnimator = ValueAnimator.ofInt(mMoveLength, 0);
        moveAnimator.setDuration(200);
        moveAnimator.setRepeatCount(0);
        moveAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                mMoveLength = value;
                LogUtils.e(TAG, ">>> 移动 >>> " + mMoveLength);
                invalidate();
                if (value == 0) {
                    isMove = false;
                    isSpin = true;
                    spin();
                }
            }
        });
        moveAnimator.start();
    }

    private ValueAnimator spinAnimator;

    public void spin() {
        if (spinAnimator != null && spinAnimator.isStarted()) {
            LogUtils.e(">>>>>>>>>> spin() >>>>>>>> ");
            spinAnimator.pause();
            spinAnimator = null;
        }
        mMoveLength = (mHeight - mPadding) / 2;
        spinAnimator = ValueAnimator.ofFloat(0, 360);
        spinAnimator.setDuration(1500);
        spinAnimator.setRepeatCount(ValueAnimator.INFINITE);
        spinAnimator.setRepeatMode(ValueAnimator.RESTART);
        spinAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mSweepAngle = value;
                LogUtils.e(TAG, ">>> 旋转 >>> " + mSweepAngle);
                invalidate();
            }
        });
        spinAnimator.start();
    }


}
