package com.czh.basicframe.calendar;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.czh.basicframe.R;
import com.czh.basicframe.utils.DensityUtil;
import com.czh.basicframe.utils.LogUtils;
import com.czh.basicframe.utils.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * author  : czh
 * create Date : 2019/8/20  10:39
 * 详情 : 自定义简单日历
 */
public class CCalendarView extends LinearLayout implements View.OnClickListener {
    private final String TAG = "CCalendarView";
    private LinearLayout mRootLayout;
    private LinearLayout mTitleLayout;
    private ImageView mLeftIv;
    private ImageView mRightIv;
    private TextView mYearTv;
    private TextView mMonthTv;
    private LinearLayout mWeekLayout;
    private RelativeLayout mDayLayout;
    private RecyclerView mRecyclerView;

    private Context context;
    private int mItemWidth;

    private int mWidth, mHeight;

    private boolean isDisSelectYesterday;//不能选择昨天

    //当前时间
    private int currentYear, currentMonth, currentDay;

    public CCalendarView(Context context) {
        this(context, null);
    }

    public CCalendarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);

    }

    public CCalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.ccalendarview, this, true);
        mRootLayout = view.findViewById(R.id.cv_root_layout);
        mTitleLayout = view.findViewById(R.id.cv_title_layout);
        mLeftIv = view.findViewById(R.id.cv_left_iv);
        mRightIv = view.findViewById(R.id.cv_right_iv);
        mYearTv = view.findViewById(R.id.cv_year_tv);
        mMonthTv = view.findViewById(R.id.cv_month_tv);
        mWeekLayout = view.findViewById(R.id.cv_week_layout);
        mDayLayout = view.findViewById(R.id.cv_day_layout);
        mRecyclerView = view.findViewById(R.id.cv_day_recyclerView);
        //初始化时间
        String currentTime = TimeUtils.getInstance().getCurrentTime("yyyy-MM-dd");
        currentYear = Integer.valueOf(currentTime.split("-")[0]);
        currentMonth = Integer.valueOf(currentTime.split("-")[1]);
        currentDay = Integer.valueOf(currentTime.split("-")[2]);
        mYearTv.setText(String.valueOf(currentYear));
        mMonthTv.setText(currentMonth > 9 ? "" + currentMonth : "0" + currentMonth);
//        LogUtils.e(TAG, "初始化时间 .........  " + currentYear + " , " + currentMonth + " , " + currentDay);
        //点击事件
        mLeftIv.setOnClickListener(this);
        mRightIv.setOnClickListener(this);
        //年份点击事件
        mYearTv.setOnClickListener(this);
        mMonthTv.setOnClickListener(this);
    }

    private boolean isFirstLoad;//这切换月份就不会多处理了。

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
//        LogUtils.e(TAG,">>>>>>> onLayout() >>>>>> " + isFirstLoad);
        if (changed && !isFirstLoad) {
            mWidth = getWidth();
            mHeight = getHeight();
            //设置周布局
            initWeekBar();
            //设置日期
            initDay(currentYear, currentMonth, currentDay);
            isFirstLoad = true;
        }

    }

    private boolean isShowDay;

    //设置天数
    private void initDay(int year, int month, int day) {
        isShowDay = true;
        isShowYear = false;
        isShowMonth = false;
        List<CCalendarBean> itemDays = new ArrayList<>();
        //
        int days = getDays(year, month);
        int week = getFirstDayForWeek(year, month);//1号是在星期几
        int count = week % 7;
        for (int i = 0; i < count; i++) {
            CCalendarBean cCalendarBean = new CCalendarBean("", false);
            cCalendarBean.setDayClickable(true);
            itemDays.add(cCalendarBean);
        }
        for (int i = 0; i < days; i++) {
            boolean isSelect = false;
            //这里设置显示默认天
            if (i == (day - 1)) {
                isSelect = true;
            }
            CCalendarBean cCalendarBean = new CCalendarBean(String.valueOf((i + 1)), isSelect);
            if (isDisSelectYesterday) {//不允许选择今天之前的时间
                String currentTime = TimeUtils.getInstance().getCurrentTime("yyyy/MM/dd");
                String setTime = year + "/" + month + "/" + (i + 1);
                int timeCompareSize = TimeUtils.getInstance().getTimeCompareSize(currentTime, setTime, "yyyy/MM/dd");
//                LogUtils.e(TAG, ">>>> 当前时间 : " + currentTime
//                        + " , 判断时间： " + setTime + " ,,, " + timeCompareSize);
                //判断一下当前时间是否在今天之前还是在今天之后
                if (timeCompareSize == 1) {
                    cCalendarBean.setDayClickable(false);
                } else {
                    cCalendarBean.setDayClickable(true);
                }
            } else {
                cCalendarBean.setDayClickable(true);
            }
            //保存
            itemDays.add(cCalendarBean);
        }
//      LogUtils.e(TAG, "....... initDay .........." + year + " , month = " + month + " , " + days + " , " + week);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(7, StaggeredGridLayoutManager.VERTICAL));
        CCalendarAdapter mAdapter = new CCalendarAdapter(context, itemDays, mItemWidth);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnClickItemListener((view, position, o) -> {
            String d = (String) o;
            String day1 = Integer.valueOf(d) > 9 ? d : "0" + d;
            String time = mYearTv.getText().toString() + "-" + mMonthTv.getText().toString() + "-" + day1;
            if (listener != null) {
                listener.onSelect(time);
            }
        });
//        //返回默认时间
        if (listener != null) {
            String time = mYearTv.getText().toString() + "-" + mMonthTv.getText().toString() + "-" + mAdapter.getDay();
            if (isDisSelectYesterday) {
                String currentTime = TimeUtils.getInstance().getCurrentTime("yyyy-MM-dd");
                int distanceTime = TimeUtils.getInstance().getTimeCompareSize(currentTime, time, "yyyy-MM-dd");
                if (distanceTime != 1) {
                    listener.onSelect(time);
                }
            } else {
                listener.onSelect(time);
            }

        }
    }

    /**
     * 获取某年某月的天数
     */
    private int getDays(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        return a.get(Calendar.DATE);
    }

    /**
     * 1号是星期几
     */
    private int getFirstDayForWeek(int year, int mouth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, mouth - 1, 0);
        int week = calendar.get(Calendar.DAY_OF_WEEK);//这就是星期几
        return week;
    }

    //设置weekBar
    private void initWeekBar() {
        String[] stringArray = context.getResources().getStringArray(R.array.week_str);
//        LogUtils.e(TAG, "...当前宽度..." + DensityUtil.dip2px(context, getWidth()));
        mItemWidth = (getWidth() - DensityUtil.dip2px(context, 5) * 14 - DensityUtil.dip2px(context, 10) * 2) / stringArray.length;
        for (int i = 0; i < stringArray.length; i++) {
            TextView weekTv = new TextView(context);
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(DensityUtil.dip2px(context, 5), 0, DensityUtil.dip2px(context, 5), 0);
            params.width = mItemWidth;
            params.height = mItemWidth;
            weekTv.setLayoutParams(params);
            weekTv.setGravity(Gravity.CENTER);
            weekTv.setTextColor(context.getResources().getColor(R.color.color_black));
            weekTv.setText(stringArray[i]);
            mWeekLayout.addView(weekTv);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cv_left_iv:
//                LogUtils.e(TAG, ">>>>> pre >> isShowYear =" + isShowYear + " , isShowDay = " + isShowDay);
                if (isShowYear) {
                    --mYearCounTag;
                    initYearRecyclerView(Integer.valueOf(mYearTv.getText().toString()));
                } else if (isShowDay) {
                    mYearCounTag = 0;
                    offsetDayForMonth(0);
                }
                break;
            case R.id.cv_right_iv:
//                LogUtils.e(TAG, ">>>>> next >> isShowYear =" + isShowYear + " , isShowDay = " + isShowDay);
                if (isShowYear) {
                    ++mYearCounTag;
                    initYearRecyclerView(Integer.valueOf(mYearTv.getText().toString()));
                } else if (isShowDay) {
                    mYearCounTag = 0;
                    offsetDayForMonth(1);
                }
                break;
            case R.id.cv_year_tv://切换年份
                currentYear = Integer.valueOf(mYearTv.getText().toString());
                currentMonth = Integer.valueOf(mMonthTv.getText().toString());
                if (!isShowYear) {
                    mWeekLayout.setVisibility(GONE);
                    initYearRecyclerView(Integer.valueOf(mYearTv.getText().toString()));
                } else {
                    mWeekLayout.setVisibility(VISIBLE);
                    //恢复成日选择
                    initDay(currentYear, currentMonth, currentDay);
                }
                break;
            case R.id.cv_month_tv://切换月份
                currentYear = Integer.valueOf(mYearTv.getText().toString());
                currentMonth = Integer.valueOf(mMonthTv.getText().toString());
                if (!isShowMonth) {
                    mWeekLayout.setVisibility(GONE);
                    initMonthRecyclerView(Integer.valueOf(mMonthTv.getText().toString()));
                } else {
                    mWeekLayout.setVisibility(VISIBLE);
                    //恢复成日选择
                    initDay(currentYear, currentMonth, currentDay);
                }
                break;
        }
    }

    /**
     * 判断当前显示是否是月份布局
     */
    private boolean isShowMonth;

    /**
     * 初始化月份列表
     *
     * @param cMonth ..当前选中的是几月份
     */
    private void initMonthRecyclerView(int cMonth) {
        isShowYear = false;
        isShowMonth = true;
        isShowDay = false;
        //这里设置控件的宽高
        int w = mWidth - DensityUtil.dip2px(getContext(), 30);
        int childViewWidth = w / 3;
        int childViewHeight = childViewWidth / 2;
        //
        List<CCalendarBean> itemMonth = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            String m = i > 9 ? String.valueOf(i) : "0" + i;
            CCalendarBean monthBean = new CCalendarBean(m, i == cMonth);//
            String currentTime = TimeUtils.getInstance().getCurrentTime("yyyy-MM");
            String nowTime = currentYear + "-" + m;
            int distanceTime = TimeUtils.getInstance().getTimeCompareSize(currentTime, nowTime, "yyyy-MM");
            if (isDisSelectYesterday) {
                if (distanceTime == 1) {
                    monthBean.setDayClickable(false);
                } else {
                    monthBean.setDayClickable(true);
                }
            } else {
                monthBean.setDayClickable(true);
            }
            itemMonth.add(monthBean);
        }
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        CCalendarAdapter mAdapter = new CCalendarAdapter(context, itemMonth, childViewWidth, childViewHeight);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnClickItemListener((view, position, o) -> {
            mWeekLayout.setVisibility(VISIBLE);
            mMonthTv.setText(((String) o));
            currentMonth = Integer.valueOf(((String) o));
            //恢复成日选择
            initDay(currentYear, currentMonth, currentDay);
        });
    }

    /**
     * 判断当前显示是否是年份布局
     */
    private boolean isShowYear;
    /**
     * 控制上一页 "--" 还是下一页 "++"
     */
    private int mYearCounTag;

    /**
     * 列表初始化成年份列表
     *
     * @param cYear
     */
    private void initYearRecyclerView(int cYear) {
        isShowYear = true;
        isShowDay = false;
        isShowMonth = false;
        //这里设置控件的宽高
        int w = mWidth - DensityUtil.dip2px(getContext(), 30);
        int childViewWidth = w / 4;
        int childViewHeight = childViewWidth * 2 / 3;
//        LogUtils.e(TAG, " >>>  " + childViewWidth + " , " + childViewHeight);
        List<CCalendarBean> itemYears = new ArrayList<>();
        String currentYear = TimeUtils.getInstance().getCurrentYear();
        for (int i = 16 * mYearCounTag; i < 16 * mYearCounTag + 16; i++) {
            int cy = this.currentYear + i;
            LogUtils.e(TAG, ">>>>>>> 初始化年份列表 >>>>>  当前年份 " + currentYear + " , cy = " + cy);
            CCalendarBean yearBean = new CCalendarBean(String.valueOf(cy), cy == cYear);//
            if (isDisSelectYesterday) {
                if (Integer.valueOf(currentYear) > cy) {
                    yearBean.setDayClickable(false);
                } else {
                    yearBean.setDayClickable(true);
                }
            } else {
                yearBean.setDayClickable(true);
            }
            itemYears.add(yearBean);
        }
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        CCalendarAdapter mAdapter = new CCalendarAdapter(context, itemYears, childViewWidth, childViewHeight);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnClickItemListener((view, position, o) -> {
            mYearTv.setText(((String) o));
            this.currentYear = Integer.valueOf(((String) o));
            mWeekLayout.setVisibility(VISIBLE);
            //恢复成日选择
            initDay(this.currentYear, currentMonth, currentDay);
        });
    }

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");

    //下个月
    private void offsetDayForMonth(int type) {
        //设置时间
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Integer.valueOf(mYearTv.getText().toString()));
        cal.set(Calendar.MONTH, Integer.valueOf(mMonthTv.getText().toString()) - 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        String time = null;
        if (type == 1) {//下一个月
            cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1); // 设置为下一个月
            Date date = cal.getTime();
            time = this.format.format(date);
            String[] timeSp = time.split("-");
            mYearTv.setText(timeSp[0]);
            mMonthTv.setText(timeSp[1]);
        } else {//上一个月
            cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1); // 设置为上一个月
            Date date = cal.getTime();
            time = this.format.format(date);
            String[] timeSp = time.split("-");
            mYearTv.setText(timeSp[0]);
            mMonthTv.setText(timeSp[1]);
        }
//        LogUtils.e(TAG, ".................. type = " + type + " , " + time);
        initDay(Integer.valueOf(mYearTv.getText().toString()), Integer.valueOf(mMonthTv.getText().toString()), 1);
    }

    public OnSelectTimeListener listener;

    public void setOnSelectTimeListener(OnSelectTimeListener listener) {
        this.listener = listener;
    }

    public interface OnSelectTimeListener {
        void onSelect(String time);
    }

    /**
     * 设置时间
     *
     * @param year
     * @param month
     * @param day
     */
    public void setTime(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        if (year == 0) {
            year = calendar.get(Calendar.YEAR);
        }
        if (month == 0) {
            month = calendar.get(Calendar.MONTH) + 1;
        }
        if (day == 0) {
            day = 1;
        }
        currentYear = year;
        currentMonth = month;
        currentDay = day;
//        Log.e(TAG, "设置数据: ........ year = " + year + " , month = " + month + " , day = " + day);
        mYearTv.setText(String.valueOf(currentYear));
        mMonthTv.setText(currentMonth > 9 ? "" + currentMonth : "0" + currentMonth);
    }


    /**
     * 设置是否不允许选择昨天
     *
     * @param b
     */
    public void setIsDisSelectYesterday(boolean b) {
        this.isDisSelectYesterday = b;
    }
}
