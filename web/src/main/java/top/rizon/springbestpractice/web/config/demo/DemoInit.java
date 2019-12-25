package top.rizon.springbestpractice.web.config.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.rizon.springbestpractice.web.config.InitConfig;

/**
 * @author Rizon
 * @date 2019/12/25
 */
@Component
@Slf4j
public class DemoInit implements InitConfig {
    @Override
    public void init() throws Exception {
        log.info("this is demo init");
    }
}
