package top.rizon.springbestpractice.common.utils.http;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.AntPathMatcher;

import java.io.IOException;
import java.util.List;

/**
 * RestTemplate请求认证header注入
 *
 * @author Rizon
 * @date 2019/12/3
 */
public abstract class BaseAuthHeaderHttpRequestInterceptor implements ClientHttpRequestInterceptor {
    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @NotNull
    @Override
    public ClientHttpResponse intercept(HttpRequest request, @NotNull byte[] body, @NotNull ClientHttpRequestExecution execution)
            throws IOException {
        String url = request.getURI().toString();
        List<String> includePathPatterns = includePathPatterns();
        //如果需要认证
        if (includePathPatterns == null ||
                includePathPatterns.stream().anyMatch(pattern -> PATH_MATCHER.match(pattern, url))) {
            process(request);
        }
        // 保证请求继续被执行
        return execution.execute(request, body);
    }

    /**
     * 拦截器的request处理方法
     * @param request HttpRequest
     */
    public abstract void process(HttpRequest request);

    /**
     * 需要认证请求的前缀
     *
     * @return 如果返回null则全部生效
     */
    @Nullable
    public abstract List<String> includePathPatterns();
}
