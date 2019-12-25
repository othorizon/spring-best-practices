package top.rizon.springbestpractice.auth;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import top.rizon.springbestpractice.common.exception.AuthFailedException;
import top.rizon.springbestpractice.common.handler.AbstractAuthHandler;
import top.rizon.springbestpractice.common.utils.AuthUtil;
import top.rizon.springbestpractice.dao.po.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Rizon
 * @date 2019/12/25
 */
@Component
@RequiredArgsConstructor
public class AuthenticationInterceptor extends AbstractAuthHandler {
    private static final String AUTH_HEADER = "AUTH-TOKEN";
    private final AuthService authService;
    private final AuthObjMapper authObjMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(AUTH_HEADER);
        if (StringUtils.isEmpty(token)) {
            throw new AuthFailedException("NOT_FOUND_AUTH_TOKEN",AUTH_HEADER);
        }
        User user = authService.queryUserCacheable(token);
        if (user == null) {
            throw new AuthFailedException();
        }
        AuthUtil.setAuthUser(request, authObjMapper.toAuthUser(user));
        return true;
    }
}
