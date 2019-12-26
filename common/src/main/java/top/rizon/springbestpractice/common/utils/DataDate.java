package top.rizon.springbestpractice.common.utils;

import com.alibaba.fastjson.annotation.JSONCreator;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.Contract;
import org.joda.time.DateTime;
import top.rizon.springbestpractice.common.exception.BaseServerException;

import java.util.Date;

/**
 * @author Rizon
 * @date 2019-08-19
 */
@EqualsAndHashCode(callSuper = true)
public class DataDate extends Date {
    private static String DATA_DATE_FORMAT = DateTimeUtils.YYYYMMDD;
    private static final String MOCK_CUR_TIME = System.getProperty("MOCK_CUR_TIME");
    /**
     * 保存一个时间数据用于快速检查是否为today对象
     */
    private Long fastTodayTime;

    public DataDate() {
        this(new DateTime().toString(DATA_DATE_FORMAT));
        this.fastTodayTime = this.getTime();
    }

    @Contract(pure = true)
    public DataDate(Date date) {
        this(date.getTime());
    }

    public DataDate(long date) {
        this(new DateTime(date).toString(DATA_DATE_FORMAT));
    }

    @JsonCreator
    @JSONCreator
    public DataDate(String dateStr) {
        super(DateTimeUtils.parse(dateStr, DATA_DATE_FORMAT).getTime());
    }

    public static DataDate of(String dateStr) {
        try {
            return new DataDate(dateStr);
        } catch (Exception ex) {
            throw new BaseServerException("日期格式错误:%s,期望的格式为:%s", dateStr, DATA_DATE_FORMAT);
        }

    }

    public static DataDate of(Date date) {
        return new DataDate(date);
    }

    public static DataDate of(DateTime datetime) {
        return new DataDate(datetime.getMillis());
    }


    public boolean isToday() {
        return DataDate.isToday(this);
    }

    public static boolean isToday(Date date) {
        //快速日期检查
        if (date instanceof DataDate &&
                ((DataDate) date).fastTodayTime != null &&
                ((DataDate) date).fastTodayTime == date.getTime()) {
            return true;
        }

        DateTime now = new DateTime(DataDate.now().getTime());
        long beginMills = now.millisOfDay().withMinimumValue().getMillis();
        long endMills = now.millisOfDay().withMaximumValue().getMillis();
        long thisMills = date.getTime();
        return thisMills >= beginMills && thisMills <= endMills;
    }

    public static DataDate now() {
        if (MOCK_CUR_TIME != null) {
            DataDate date = new DataDate(MOCK_CUR_TIME);
            date.fastTodayTime = date.getTime();
            return date;
        }
        return new DataDate();
    }

    @JsonValue
    @Contract(pure = true)
    public String toQuery() {
        return DateTimeUtils.format(this, DATA_DATE_FORMAT);
    }

    @Contract(pure = true)
    public String toEsId() {
        return DateTimeUtils.format(this, DateTimeUtils.YYYYMMDD_NOLINE);
    }

    @Contract(pure = true)
    public Date toDate() {
        return new Date(this.getTime());
    }

    public String toString(String pattern) {
        return DateTimeUtils.format(this, pattern);
    }

    @Override
    public String toString() {
        return toQuery();
    }

    public static class Builder {
        public static DataDate newInstance(String pattern) {
            return new DataDate() {
                {
                    DATA_DATE_FORMAT = pattern;
                }
            };
        }
    }
}
