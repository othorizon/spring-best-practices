package top.rizon.springbestpractice.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Contract;
import top.rizon.springbestpractice.common.exception.AssertFailExceptionBase;
import top.rizon.springbestpractice.common.exception.BaseServerException;

/**
 * 用于一些常见的断言
 * required* 相关的方法则可以在一行代码中完成验证和取值，更为简洁
 *
 * @author Rizon
 * @date 2018/9/11
 * @see com.google.common.base.Preconditions
 */
public class Assert {
    public static void isTrue(boolean test, String msg, Object... msgArgs) {
        if (!test) {
            throw new AssertFailExceptionBase(msg, msgArgs);
        }
    }

    public static void notEmpty(String str, String msg) {
        if (StringUtils.isEmpty(str)) {
            throw new AssertFailExceptionBase(msg);
        }
    }

    public static void notBlank(String str, String msg) {
        if (StringUtils.isBlank(str)) {
            throw new AssertFailExceptionBase(msg);
        }
    }


    @Contract("null, _, _ -> fail")
    public static void notNull(Object obj, String msg, Object... msgArgs) {
        if (null == obj) {
            throw new AssertFailExceptionBase(msg, msgArgs);
        }
    }

    @Contract("!null, _, _ -> !null; null, _, _ -> fail")
    public static <T> T requiredNotNull(T obj, String msg, Object... msgArgs) {
        if (null != obj) {
            return obj;
        } else {
            throw new AssertFailExceptionBase(msg, msgArgs);
        }
    }

    @Contract("!null, _, _ -> !null; null, _, _ -> fail")
    public static String requiredNotEmpty(String obj, String msg, Object... msgArgs) {
        if (StringUtils.isNotEmpty(obj)) {
            return obj;
        } else {
            throw new AssertFailExceptionBase(msg, msgArgs);
        }
    }

    @Contract("!null, _, _ -> !null; null, _, _ -> fail")
    public static String requiredNotBlank(String obj, String msg, Object... msgArgs) {
        if (StringUtils.isNotBlank(obj)) {
            return obj;
        } else {
            throw new AssertFailExceptionBase(msg, msgArgs);
        }
    }

    /**
     * 期望只有一个或零个结果
     *
     * @param size
     */
    public static void oneOrEmpty(int size) {
        if (size > 1) {
            throw new BaseServerException("TOO_MANY_RESULTS_EXCEPTION", size);
        }
    }
}
