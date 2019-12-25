package top.rizon.springbestpractice.common.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Rizon
 * @date 2019-10-19
 */
public class ResponseUtil {
    private static final ThreadLocal<String> ERROR_MSG = new ThreadLocal<>();

    public static void setThreadErrorMsg(String message) {
        ERROR_MSG.remove();
        ERROR_MSG.set(message);
    }

    public static String getErrorMsg() {
        return ERROR_MSG.get();
    }

    public static void appendThreadErrorMsg(String message) {
        ERROR_MSG.set(message + ";" + StringUtils.defaultString(ERROR_MSG.get()));
    }

    public static String popErrorMsg() {
        String msg = ERROR_MSG.get();
        ERROR_MSG.remove();
        return msg;
    }

}
