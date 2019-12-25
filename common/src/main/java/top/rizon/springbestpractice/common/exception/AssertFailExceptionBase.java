package top.rizon.springbestpractice.common.exception;

/**
 * @author Rizon
 * @date 2019/1/24
 */
public class AssertFailExceptionBase extends BaseServerException {
    public AssertFailExceptionBase() {
    }

    public AssertFailExceptionBase(String message) {
        super(message);
    }

    public AssertFailExceptionBase(String message, Object... args) {
        super(message, args);
    }
}
