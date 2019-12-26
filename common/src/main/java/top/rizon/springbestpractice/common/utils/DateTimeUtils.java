package top.rizon.springbestpractice.common.utils;

import lombok.NonNull;
import org.joda.time.DateTime;
import top.rizon.springbestpractice.common.exception.BaseServerException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Rizon
 * @date 2019/12/26
 */
public class DateTimeUtils {
    public static final String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYYMMDDHHMM = "yyyy-MM-dd HH:mm";
    public static final String YYYYMMDDHHMM_ZH = "yyyy年MM月dd HH点mm分";
    public static final String YYYYMMDD_ZH = "yyyy年MM月dd日";
    public static final String YYYYMMDDHHMMSS_ZH = "yyyy年MM月dd日 HH点mm分ss秒";
    public static final String YYYYMMDD = "yyyy-MM-dd";
    public static final String YYYYMM = "yyyy-MM";
    public static final String YYYYMM_NOLINE = "yyyyMM";
    public static final String YYYYMM_ZH = "yyyy年MM月";
    public static final String YYYY = "yyyy";
    public static final String HHMMSS = "HH:mm:ss";
    public static final String MMDDHHMMSS = "MM-dd HH:mm:ss";
    public static final String DDHHMMSS = "dd HH:mm:ss";

    /***
     * 没有中划线
     */
    public static final String YYYYMMDD_NOLINE = "yyyyMMdd";
    public static final String YYYYMMDDHH_NOLINE = "yyyyMMddHH";
    public static final String YYYYMMDDHHMM_NOLINE = "yyyyMMddHHmm";


    @NonNull
    public static Date parse(@NonNull String timeStr, @NonNull String pattern) {
        try {
            return new SimpleDateFormat(pattern).parse(timeStr);
        } catch (ParseException ex) {
            throw new BaseServerException(ex,
                    "could not parse date: %s,pattern:%s", timeStr, pattern);
        }
    }

    @NonNull
    public static String format(@NonNull Date date, @NonNull String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }

    /**
     * 获取给定时间所在月份的天数
     */
    public static int getMaxDayOfMonth(Date date) {
        return new DateTime(date).dayOfMonth().withMaximumValue().getDayOfMonth();
    }

    /**
     * 当月最后一天的最大时间
     */
    public static Date getLastDayOfMonth(Date date) {
        return new DateTime(date).dayOfMonth().withMaximumValue().millisOfDay().withMaximumValue().toDate();
    }

    /**
     * 当月第一天的最小时间最小时间
     */
    public static Date getFirstDayOfMonth(DateTime date) {
        return date.dayOfMonth().withMinimumValue().withTimeAtStartOfDay().toDate();
    }

}
