package com.czh.basicframe.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * author  : czh
 * create Date : 2018/7/6  13:54
 */
public class TimeUtils {

    public String defaultFormat = "yyyy-MM-dd HH:mm:ss";

    public static TimeUtils getInstance() {
        return TimeUtilsHolder.instance;
    }

    public static class TimeUtilsHolder {
        private static final TimeUtils instance = new TimeUtils();
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public String getCurrentTime() {
        return new SimpleDateFormat(defaultFormat).format(new Date(System.currentTimeMillis()));
    }

    public String getCurrentTime(String format) {
        return new SimpleDateFormat(format).format(new Date(System.currentTimeMillis()));
    }


    /**
     * Date 转成 String 类型
     */
    public String dateToStringTime(Date date) {
        return new SimpleDateFormat(defaultFormat).format(date);
    }

    public String dateToStringTime(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * long 转成 String
     */
    public String longToStringTime(Long l) {
        Date date = new Date(l);
        return dateToStringTime(date);
    }

    public String longToStringTime(Long l, String format) {
        return dateToStringTime(new Date(l), format);
    }

    /**
     * String 转成 Date
     * !!! time 和 format 的格式要一致
     */
    public Date stringToDateTime(String time, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try {
            Date date = simpleDateFormat.parse(time);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Date longToDate(Long l, String format) {
        String s = longToStringTime(l);
        Date date = stringToDateTime(s, format);
        return date;
    }

    /**
     * Date To Long
     */
    public Long dateToLong(Date date) {
        return date.getTime();
    }

    /**
     * String To Long
     * time 和 format 的格式要一致
     */
    public Long stringToLong(String time, String format) {
        Date date = stringToDateTime(time, format);
        return dateToLong(date);
    }

    /**
     * 获取下一天日期
     */
    public String getNextDay(String time, String format) {
        return changeTime(time, format, Calendar.DAY_OF_MONTH, 1);
    }

    /**
     * 获取前一天日期
     */
    public String getLastDay(String time, String format) {
        return changeTime(time, format, Calendar.DAY_OF_MONTH, -1);
    }

    /**
     * 获取下一周的日期
     *
     * @param time
     * @param format
     * @return
     */
    public String getNextWeekDay(String time, String format) {
        return changeTime(time, format, Calendar.DAY_OF_MONTH, 7);
    }

    /**
     * 获取前一周日期
     *
     * @param time
     * @param format
     * @return
     */
    public String getLastWeekDay(String time, String format) {
        return changeTime(time, format, Calendar.DAY_OF_MONTH, -7);
    }


    /**
     * 获取下个月日期
     *
     * @return
     */
    public String getNextMonth(String time, String format) {
        return changeTime(time, format, Calendar.MONTH, 1);
    }

    /**
     * 获取上个月日期
     *
     * @return
     */
    public String getLastMonth(String time, String format) {
        return changeTime(time, format, Calendar.MONTH, -1);
    }

    /**
     * 获取上一年时间
     *
     * @param time
     * @param format
     * @return
     */
    public String getNextYear(String time, String format) {
        return changeTime(time, format, Calendar.YEAR, 1);
    }

    /**
     * 获取下一年时间
     *
     * @param time
     * @param format
     * @return
     */
    public String getLastYear(String time, String format) {
        return changeTime(time, format, Calendar.YEAR, -1);
    }


    /**
     * 本周周一的具体时间
     * Calendar.XXX : SUNDAY, MONDAY , TUESDAY , WEDNESDAY , THURSDAY , FRIDAY , SATURDAY
     *
     * @return
     */
    public String getDateForWeek(int flag) {
        String format = "yyyy-MM-dd";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(System.currentTimeMillis()));
        switch (flag) {
            case 1:
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                break;
            case 2:
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
                break;
            case 3:
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
                break;
            case 4:
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
                break;
            case 5:
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
                break;
            case 6:
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
                break;
            case 0://系统的默认第一天是周日
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                break;
        }
        return dateToStringTime(calendar.getTime(), format);
    }


    /**
     * 改变日期
     *
     * @param time     需要改变的日期
     * @param format   日期格式与原来的日期一致
     * @param dateType Calendar.XXX
     * @param days     改变的值
     * @return
     */
    private String changeTime(String time, String format, int dateType, int days) {
        Date date = stringToDateTime(time, format);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(dateType, days);
        date = calendar.getTime();
        return dateToStringTime(date, format);
    }


    /**
     * 获取星期
     *
     * @param week
     */
    public String getWeek(int week) {
        String s = null;
        switch (week) {
            case 0:
                s = "星期日";
                break;
            case 1:
                s = "星期一";
                break;
            case 2:
                s = "星期二";
                break;
            case 3:
                s = "星期三";
                break;
            case 4:
                s = "星期四";
                break;
            case 5:
                s = "星期五";
                break;
            case 6:
                s = "星期六";
                break;
        }
        return s;
    }

    /**
     * 根据提供的年月日获取该月份的第一天
     *
     * @param year
     * @param monthOfYear
     * @return
     */
    public Date getStartDayofMonth(int year, int monthOfYear) {
        Calendar cal = Calendar.getInstance();
        // 不加下面2行，就是取当前时间前一个月的第一天及最后一天
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, monthOfYear);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);

        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date lastDate = cal.getTime();

        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDate = cal.getTime();
        return firstDate;
    }

    /**
     * 根据提供的年月获取该月份的最后一天
     *
     * @param year
     * @param monthOfYear
     * @return
     */
    public Date getEndDayofMonth(int year, int monthOfYear) {
        Calendar cal = Calendar.getInstance();
        // 不加下面2行，就是取当前时间前一个月的第一天及最后一天
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, monthOfYear);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);

        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date lastDate = cal.getTime();

        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDate = cal.getTime();
        return lastDate;
    }


    /**
     * 计算时间差
     *
     * @param time1
     * @param time2
     * @param format
     * @param flag   0 返回天数 1 返回小时 2 返回分钟 3返回秒 4返回年
     * @return
     */
    public int getDistanceTime(String time1, String time2, String format, int flag) {
        Long long1 = TimeUtils.getInstance().stringToLong(time1, format);
        Long long2 = TimeUtils.getInstance().stringToLong(time2, format);
        long day = 0;
        long hour = 0;
        long hour2 = 0;
        long min = 0;
        long min2 = 0;
        long sec = 0;
        long diff;
        long year = 0;
        if (long1 < long2) {
            diff = long2 - long1;
        } else {
            diff = long1 - long2;
        }

        day = diff / (24 * 60 * 60 * 1000);//天数
        year = day / 365;//这里默认全部都是365天
        hour = diff / (60 * 60 * 1000);//不考虑天数，只是计算两个时间段的小时差。
        hour2 = diff / (60 * 60 * 1000) - day * 24;//小时
        min = ((diff / (60 * 1000)) - day * 24 * 60 - hour2 * 60);//
        min2 = diff / (60 * 1000);
        sec = (diff / 1000 - day * 24 * 60 * 60 - hour2 * 60 * 60 - min * 60);
        if (flag == 0) {
            return (int) day;
        } else if (flag == 1) {
            return (int) hour;
        } else if (flag == 2) {
            return (int) min2;
        } else if (flag == 3) {
            return (int) sec;
        } else if (flag == 4) {
            return (int) year;
        } else {
            return 0;
        }
    }

    public String getDistanceTime(String time1, String time2, String format) {
        Long long1 = TimeUtils.getInstance().stringToLong(time1, format);
        Long long2 = TimeUtils.getInstance().stringToLong(time2, format);
        double hour = 0;
        long diff;
        if (long1 < long2) {
            diff = long2 - long1;
        } else {
            diff = long1 - long2;
        }
        hour = diff / (60 * 60 * 1000);//不考虑天数，只是计算两个时间段的小时差。
        return String.format("%.2f", hour);
    }

    /**
     * 计算天数差  eTime 比 sTime多多少天，如果要算天数要加上eTime当天
     */
    public String countDays(String sTime, String eTime, String pattern) {
        String days = null;
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            Date date1 = format.parse(sTime);
            Date date2 = format.parse(eTime);
            int i = differentDays(date1, date2);
            days = i + "";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }


    /**
     * 23、判断时间eTime是否大于sTime
     *
     * @param sTime         开始时间
     * @param eTime         结束时间
     * @param getNextOrLast 1:取前一天(一般来说开始时间填) 2:取下一天
     * @return
     */
    public String dayIsNext(Context context, String sTime, String eTime, int getNextOrLast, String sFormat) {
        Log.e("ChenZh", " ------------------ sTime = " + sTime + " , eTime = " + eTime + " ------------------ ");
        String finalTime = null;
        int timeCompareSize = getTimeCompareSize(sTime, eTime, sFormat);
        Log.e("ChenZh", " ------------------ " + timeCompareSize + " ------------------ ");
        switch (timeCompareSize) {
            case 1://结束时间小于开始时间
                if (getNextOrLast == 1) {
                    finalTime = eTime;
                } else {
                    finalTime = sTime;
                }
                break;
            case 2://开始时间与结束时间相同
                finalTime = sTime;
                break;
            case 3://结束时间大于开始时间
                if (getNextOrLast == 1) {
                    finalTime = sTime;
                } else {
                    finalTime = eTime;
                }
                break;
        }
        //判断是或否大于当前时间
        String currentTime = new SimpleDateFormat(sFormat).format(new Date(System.currentTimeMillis()));
        int checkTime = getTimeCompareSize(finalTime, currentTime, sFormat);
        if (checkTime == 3) {
            Toast.makeText(context, "选择日期有误", Toast.LENGTH_SHORT).show();
            finalTime = currentTime;
        }
        return finalTime;
    }

    /**
     * 判断2个时间大小
     * yyyy-MM-dd 格式
     *
     * @param startTime
     * @param endTime   只能算到年月日，时分不能算
     * @return
     */
    public int getTimeCompareSize(String startTime, String endTime, String format) {
        int i = 0;
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);//年-月-日
        try {
            Date date1 = dateFormat.parse(startTime);//开始时间
            Date date2 = dateFormat.parse(endTime);//结束时间
            // 1 endTime小于startTime 2 startTime=endTime 3 endTime大于startTime
            if (date2.getTime() < date1.getTime()) {
                i = 1;
            } else if (date2.getTime() == date1.getTime()) {
                i = 2;
            } else if (date2.getTime() > date1.getTime()) { //正常情况下的逻辑操作.
                i = 3;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return i;
    }

    /**
     * 20. date2比date1多的天数(日期时间差)
     *
     * @param date1
     * @param date2
     * @return
     */
    public int differentDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);

        if (year1 != year2) {//不同年
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) { //闰年
                    timeDistance += 366;
                } else {//不是闰年
                    timeDistance += 365;
                }
            }
            return timeDistance + (day2 - day1);
        } else {//同一年
            return day2 - day1;
        }
    }


    /**
     * 21 日期格式转换
     *
     * @param date
     * @param format
     * @return
     */
    public String changeDateFormart(String date, String format, String newFromat) {
        String newDates = null;
        SimpleDateFormat newFormat = null;
        if (TextUtils.isEmpty(newFromat)) {
            newFormat = new SimpleDateFormat("yyyy-MM-dd");
        } else {
            newFormat = new SimpleDateFormat(newFromat);
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try {
            Date parse = simpleDateFormat.parse(date);
            newDates = newFormat.format(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDates;
    }

    /**
     * 上午下午全天对应的天数
     *
     * @return
     */
    public float textToIntTime(boolean isOneDay, String s, String e) {
        LogUtils.e("TimeUtils", "S = " + s + " , E = " + e + " ,  isOneDay = " + isOneDay);
        if (isOneDay) {//同一天的情况下
            if ((s.contains("下午") && e.contains("上午")) || s.contains("下午") && e.contains("全天")) {
                //开始时间选择下午，结束时间选择上午，选择有误
                return -1;
            } else if (s.equals(e)) {//两个相等，同为上午或者同为下午,半天显示0天
                return 0;
            } else if (!s.equals(e) || s.contains("全天") || e.contains("全天")) {
                return 1;
            }
        } else {//不是同一天的情况  16 下午   17上午
            if ((s.equals(e) && !s.equals("全天")) || (s.contains("下午") && e.contains("全天"))) {//时间相等(都不是全天的情况下) 多加半天
                return 0.5f;
            } else if (s.contains("下午") && e.contains("上午")) {
                return 0;
            } else if (((s.contains("上午") || s.contains("全天")) && (e.contains("下午") || e.contains("全天")))) {
                return 1;
            } else if (s.contains("全天") && e.contains("上午")) {
                return 0.5f;
            }
        }
        return 0;
    }

    /**
     * 获取整点时间
     *
     * @param hourAndMunite 时分 HH:mm
     */
    public String getIntegerTime(String hourAndMunite) {
        String[] hourMSp = hourAndMunite.split(":");
        if (hourMSp != null) {
            Integer hour = Integer.valueOf(hourMSp[0]);
            Integer munite = Integer.valueOf(hourMSp[1]);
            if (munite == 0 || munite == 30) {
                return hourAndMunite;
            } else if (munite > 0 && munite < 30) {
                return hour + ":" + "30";
            } else if (munite > 30 && munite <= 59) {
                return (hour + 1) + ":" + "00";
            }
        }
        return null;
    }


}
