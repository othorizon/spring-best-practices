package top.rizon.springbestpractice.common.model.response;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import top.rizon.springbestpractice.common.exception.BaseServerException;
import top.rizon.springbestpractice.common.utils.ResponseUtil;
import top.rizon.springbestpractice.common.utils.TraceUtils;

/**
 * @author Rizon
 * @date 2019/1/25
 */
@Data
@Accessors(chain = true)
public class Response<T> {
    public static final Integer ERR_MESSAGE_LENGTH = 2048;
    public static final int CODE_SUCCESS = 200;
    public static final int CODE_FAILURE = 500;
    public static final int CODE_AUTH_FAILURE = 300;

    public static final String MESSAGE_SUCCESS = "成功";
    public static final String MESSAGE_FAILURE = "失败";

    private int status;
    private String message;

    private static boolean closeExStack;

    public static void setCloseExStack(boolean closeExStack) {
        Response.closeExStack = closeExStack;
    }

    private String errMessage;
    private String warnMsg;

    public String getRequestId() {
        return TraceUtils.getTraceId();
    }

    public String getWarnMsg() {
        if (closeExStack) {
            return null;
        }
        return warnMsg == null ? ResponseUtil.popErrorMsg() : warnMsg;
    }

    public String getErrMessage() {
        if (closeExStack) {
            return null;
        }
        return StringUtils.substring(errMessage, 0, ERR_MESSAGE_LENGTH);
    }

    private T result;

    public Response() {

    }

    public Response(int status, String message, T result) {
        this.status = status;
        this.message = message;
        this.result = result;
    }

    public Response(int status, String message, String errMessage, T result) {
        this.status = status;
        this.message = message;
        this.result = result;
        this.errMessage = errMessage;
    }


    public static <T> Response<T> success(T data) {
        return new Response<>(CODE_SUCCESS, MESSAGE_SUCCESS, data);
    }

    public static <T> Response<T> success(String message, T data) {
        return new Response<>(CODE_SUCCESS, message, data);
    }

    public static <T> Response<T> failure(T data) {
        return new Response<>(CODE_FAILURE, MESSAGE_FAILURE, data);
    }

    public static <T> Response<T> failure(String message, T data) {
        return new Response<>(CODE_FAILURE, message, data);
    }

    public static <T> Response<T> failure(String message, String errMessage, T data) {
        return new Response<>(CODE_FAILURE, message, errMessage, data);
    }

    public static <T> Response<T> response(int status, String message, T data) {
        return new Response<>(status, message, null, data);
    }

    public static <T> Response<T> response(int status, String message, String errMessage, T data) {
        return new Response<>(status, message, errMessage, data);
    }

    public void setResult(T result) {
        this.result = result;
    }

    public T getResult() {
        return result;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public static void checkSuccess(Response<?> response, boolean thrExpIfRetIsNull) {
        if (response.getStatus() != CODE_SUCCESS) {
            throw new BaseServerException("接口返回错误，status:%s,message:%s", response.getStatus(), response.getMessage());
        }
        if (thrExpIfRetIsNull && response.getResult() == null) {
            throw new BaseServerException("接口返回的result是null");
        }
    }
}
