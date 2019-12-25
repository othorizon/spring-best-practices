package top.rizon.springbestpractice.web.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import top.rizon.springbestpractice.common.cache.CacheHandlerInterceptor;
import top.rizon.springbestpractice.common.cache.RequestScopedCacheManager;
import top.rizon.springbestpractice.common.model.dto.AuthUser;
import top.rizon.springbestpractice.common.utils.AuthUtil;
import top.rizon.springbestpractice.common.utils.TraceUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Rizon
 * @date 2019/12/13
 */
@Configuration
@Slf4j
public class WebConfig implements WebMvcConfigurer {
    @Bean("requestScopedCacheManager")
    @Primary
    public RequestScopedCacheManager requestScopedCacheManager() {
        return new RequestScopedCacheManager();
    }

    @Bean
    public CacheHandlerInterceptor cacheHandlerInterceptor() {
        return new CacheHandlerInterceptor(requestScopedCacheManager());
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("register requestInterceptor");
        registry.addInterceptor(requestInterceptor()).addPathPatterns("/**");
        log.info("register requestScopedCacheManager");
        registry.addInterceptor(cacheHandlerInterceptor()).addPathPatterns("/**");
    }

    public static class RequestInterceptor extends HandlerInterceptorAdapter {
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            AuthUser user = AuthUtil.getAuthUser(request);
            TraceUtils.beginTaskTrace(String.format("%s-%s",
                    TraceUtils.randomTraceId(), user == null ? null : user.getId()));
            return true;
        }

        @Override
        public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
            TraceUtils.endTaskTrace();
        }
    }

}
