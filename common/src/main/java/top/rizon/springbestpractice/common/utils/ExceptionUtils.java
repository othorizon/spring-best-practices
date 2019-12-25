package top.rizon.springbestpractice.common.utils;

import jodd.exception.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Rizon
 * @date 2019-10-21
 */
public class ExceptionUtils {
    public static String rootCauseToString(Exception ex) {
        return ExceptionUtil.exceptionStackTraceToString(ExceptionUtil.getRootCause(ex));
    }

    public static String rootCauseToString(Exception ex, int limit) {
        return StringUtils.substring(rootCauseToString(ex), 0, limit);
    }

}
