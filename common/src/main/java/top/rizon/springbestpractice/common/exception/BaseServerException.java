package top.rizon.springbestpractice.common.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 基础异常类
 *
 * @author Rizon
 * @date 2019/1/24
 */
public class BaseServerException extends RuntimeException {
    /**
     * 自定义的拼接在Response的errMessage前面的错误信息
     * 不会展示在页面上
     */
    @Accessors(chain = true)
    @Setter
    @Getter
    private String errMessage;

    /**
     * message 参数
     */
    @Accessors(chain = true)
    @Setter
    @Getter
    private Object[] msgArgs;

    public BaseServerException() {
    }

    public BaseServerException(String message, Object... args) {
        super(String.format(message, args));
        this.msgArgs = args;
    }

    public BaseServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseServerException(Throwable cause, String message, Object... args) {
        super(String.format(message, args), cause);
        this.msgArgs = args;
    }

    public BaseServerException(Throwable cause) {
        super(cause);
    }
}
