package top.rizon.springbestpractice.common.utils.http;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * @author Rizon
 * @date 2019/12/3
 */
@Configuration
@Slf4j
public class RestTemplateAuthConfig implements ApplicationContextAware, InitializingBean {
    private ApplicationContext applicationContext;
    private final RestTemplate restTemplate;

    public RestTemplateAuthConfig(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, BaseAuthHeaderHttpRequestInterceptor> authInterceptors = applicationContext.getBeansOfType(BaseAuthHeaderHttpRequestInterceptor.class);
        List<ClientHttpRequestInterceptor> interceptors =
                ListUtils.emptyIfNull(restTemplate.getInterceptors());
        for (Map.Entry<String, BaseAuthHeaderHttpRequestInterceptor> entry : authInterceptors.entrySet()) {
            log.info("add restTemplate auth interceptor:{}", entry.getKey());
            interceptors.add(entry.getValue());
        }
        SimpleRestTemplateUtils.getRestTemplate().setInterceptors(interceptors);
    }
}
