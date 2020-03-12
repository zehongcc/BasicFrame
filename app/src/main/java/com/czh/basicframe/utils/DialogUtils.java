package com.czh.basicframe.utils;

import android.content.Context;
import android.support.design.widget.BottomSheetDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.czh.basicframe.R;
import com.czh.basicframe.calendar.CCalendarView;

/**
 * author  : czh
 * create Date : 2019/10/24  13:46
 * 详情 :
 */
public class DialogUtils {
    public static DialogUtils getInstance() {
        return DialogHolder.instance;
    }

    private static class DialogHolder {
        private static final DialogUtils instance = new DialogUtils();
    }


    /**
     * 日历时间选择器
     *
     * @param b        是否不允许选择昨天   true 不能选择今天之前的日期
     * @param listener
     */
    public void showTimeDialog(Context mContext, String time, boolean b, CCalendarView.OnSelectTimeListener listener) {
        final BottomSheetDialog dialog = new BottomSheetDialog(mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_calendar, null);
        CCalendarView calendarView = view.findViewById(R.id.dialog_calendarView);
        calendarView.setIsDisSelectYesterday(b);
        if (TextUtils.isEmpty(time)) {//todo 根据时间样式改动
            time = TimeUtils.getInstance().getCurrentTime("yyyy-MM-dd");
        }
        String[] split = time.split("-");//yyyy-MM-dd
        calendarView.setTime(Integer.valueOf(split[0]), Integer.valueOf(split[1]), Integer.valueOf(split[2]));
        calendarView.setOnSelectTimeListener(listener);
        dialog.setContentView(view);
        //是否可以取消弹窗
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.setCancelable(false);
        //给布局设置透明背景色
        dialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet)
                .setBackgroundColor(mContext.getResources().getColor(android.R.color.transparent));
        dialog.show();
    }
}
