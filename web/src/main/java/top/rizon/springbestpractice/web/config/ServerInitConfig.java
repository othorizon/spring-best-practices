package top.rizon.springbestpractice.web.config;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import top.rizon.springbestpractice.common.model.response.Response;

/**
 * @author Rizon
 * @date 2019/12/13
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class ServerInitConfig implements InitializingBean {
    private final BaseServerConfig config;


    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("setCloseExStack:{}", config.isCloseExStack());
        Response.setCloseExStack(config.isCloseExStack());
    }

    @Configuration
    @ConfigurationProperties(prefix = "base-server")
    @Data
    public static class BaseServerConfig {
        /**
         * 关闭response的errMessage输出，
         * 生产环境面向前端的服务建议关闭该配置，防止泄漏信息
         */
        private boolean closeExStack;
    }
}
