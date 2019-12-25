package top.rizon.springbestpractice.common.utils;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.rizon.springbestpractice.common.exception.AuthFailedException;
import top.rizon.springbestpractice.common.model.dto.AuthUser;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Rizon
 * @date 2019/12/3
 */
public class AuthUtil {
    private static final String USER_KEY = "AUTH_USER_BEAN";

    public static void setAuthUser(HttpServletRequest request, AuthUser authUser) {
        request.setAttribute(USER_KEY, authUser);
    }

    /**
     * @throws AuthFailedException 认证失败会报错
     */
    @NonNull
    public static AuthUser authUserOrFailed() {
        Object authUser = getRequest().getAttribute(USER_KEY);
        if (!(authUser instanceof AuthUser)) {
            throw new AuthFailedException("AUTH_FAILED");
        }
        return (AuthUser) authUser;
    }

    @Nullable
    public static AuthUser getAuthUser(HttpServletRequest request) {
        Object authUser = request.getAttribute(USER_KEY);
        if (authUser instanceof AuthUser) {
            return (AuthUser) authUser;
        }
        return null;
    }

    private static HttpServletRequest getRequest() {
        return getRequestAttributes().getRequest();
    }

    private static ServletRequestAttributes getRequestAttributes() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            throw new AuthFailedException("requestAttributes is null,not http request");
        }
        return requestAttributes;
    }
}
