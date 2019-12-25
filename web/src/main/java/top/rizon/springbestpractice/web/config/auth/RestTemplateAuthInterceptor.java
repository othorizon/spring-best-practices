package top.rizon.springbestpractice.web.config.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;
import top.rizon.springbestpractice.common.utils.AuthUtil;
import top.rizon.springbestpractice.common.utils.http.BaseAuthHeaderHttpRequestInterceptor;

import java.util.List;

/**
 * 拦截restTemplate请求，注入认证信息的demo
 *
 * @author Rizon
 * @date 2019/12/3
 */
@ConditionalOnProperty(value = "auth.enable", havingValue = "true", matchIfMissing = true)
@Component
@RequiredArgsConstructor
@Slf4j
public class RestTemplateAuthInterceptor extends BaseAuthHeaderHttpRequestInterceptor {
    private final AuthWebConfig.AuthConf authConf;

    @Override
    public void process(HttpRequest request) {
        log.info("http request interceptor");
        request.getHeaders().add("AUTH-TOKEN", AuthUtil.authUserOrFailed().getToken());
    }


    @Override
    @Nullable
    public List<String> includePathPatterns() {
        //返回null表示全部生效
        return null;
    }
}
