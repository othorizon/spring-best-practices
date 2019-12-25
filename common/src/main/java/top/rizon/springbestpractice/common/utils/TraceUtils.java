package top.rizon.springbestpractice.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.MDC;


/**
 * 系统运行时打印方便调试与追踪信息的工具类.
 * 使用MDC存储traceID, 一次trace中所有日志都自动带有该ID,
 * 可以方便的用grep命令在日志文件中提取该trace的所有日志.
 *
 * @author Rizon
 * @date 2019/10/24
 */
@Slf4j
public class TraceUtils {
    private static final String TRACE_KEY = "TRACE_KEY";
    private static final int TRACE_ID_LENGTH = 8;


    /**
     * 开始Trace, 将指定key的value放入MDC.
     */
    public static void beginTaskTrace(String requestId) {
        try {
            MDC.put(TRACE_KEY, requestId);
        } catch (Exception ex) {
            log.error("trace begin error", ex);
        }
    }

    /**
     * 结束Trace.
     * 清除traceId.
     */
    public static void endTaskTrace() {
        try {
            MDC.remove(TRACE_KEY);
        } catch (Exception ex) {
            log.error("trace end error", ex);
        }
    }

    public static String getTraceId() {
        try {
            return MDC.get(TRACE_KEY);
        } catch (Exception ex) {
            log.error("get trace id error", ex);
            return null;
        }
    }

    public static String randomTraceId() {
        return RandomStringUtils.randomAlphanumeric(TRACE_ID_LENGTH);
    }
}
