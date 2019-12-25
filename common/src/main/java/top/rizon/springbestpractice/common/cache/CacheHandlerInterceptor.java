package top.rizon.springbestpractice.common.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 清理 RequestScopedCacheManager的缓存
 *
 * @author Rizon
 * @date 2019/12/13
 */
@RequiredArgsConstructor
public class CacheHandlerInterceptor extends HandlerInterceptorAdapter {

    private final RequestScopedCacheManager requestScopedCacheManager;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        requestScopedCacheManager.clearCaches();
        return true;
    }

    /**
     * afterCompletion与postHandler不同，即使抛出异常后也会被执行
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        requestScopedCacheManager.clearCaches();
    }

}
