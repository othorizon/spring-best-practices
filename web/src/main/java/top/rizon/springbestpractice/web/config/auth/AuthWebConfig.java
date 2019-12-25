package top.rizon.springbestpractice.web.config.auth;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.rizon.springbestpractice.common.handler.AbstractAuthHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * 认证拦截配置
 *
 * @author Rizon
 * @date 2019/12/3
 */
@ConditionalOnProperty(value = "auth.enable", havingValue = "true", matchIfMissing = true)
@Configuration
@RequiredArgsConstructor
@Slf4j
public class AuthWebConfig implements WebMvcConfigurer {
    private final AbstractAuthHandler authHandler;
    private final AuthConf authConf;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("register auth interceptor:{}", authHandler.getClass());
        registry.addInterceptor(authHandler)
                .excludePathPatterns(authConf.getExcludePath());
    }


    @Configuration
    @ConfigurationProperties(prefix = "auth", ignoreInvalidFields = true)
    @Data
    public static class AuthConf {
        private List<String> excludePath = new ArrayList<>();
    }
}
