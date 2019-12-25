package top.rizon.springbestpractice.common.handler;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Rizon
 * @date 2019/12/3
 */
public abstract class AbstractAuthHandler implements HandlerInterceptor {
    @Override
    public abstract boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception;
}
