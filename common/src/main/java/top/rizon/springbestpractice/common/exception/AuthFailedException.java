package top.rizon.springbestpractice.common.exception;

/**
 * @author Rizon
 * @date 2019/12/2
 */
public class AuthFailedException extends BaseServerException{
    public AuthFailedException() {
    }

    public AuthFailedException(String message, Object... args) {
        super(message, args);
    }
}
