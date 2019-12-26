package cn.boommanpro.config.valid;

import cn.boommanpro.config.valid.config.CasConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author <a href="mailto:boommanpro@gmail.com">BoomManPro</a>
 * @date 2019/12/26 13:48
 */
@SpringBootApplication
@EnableConfigurationProperties(CasConfigProperties.class)
public class ConfigValidApplication {

    public static void main(String[] args) {
        new SpringApplication(ConfigValidApplication.class).run(args);
    }
}
