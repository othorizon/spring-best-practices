package top.rizon.springbestpractice.common.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * 在一些场景中我们屏蔽掉了一些服务端异常情况让接口可以正常返回数据，
 * 但是又希望可以从接口吐出这些异常信息以供调试使用，
 * 此时可以借助该工具实现
 *
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
