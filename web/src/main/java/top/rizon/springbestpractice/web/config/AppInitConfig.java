package top.rizon.springbestpractice.web.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 程序启动后的初始化配置
 *
 * @author Rizon
 * @date 2019-08-27
 */
@Slf4j
@Component
public class AppInitConfig implements ApplicationListener<ApplicationReadyEvent> {
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Map<String, InitConfig> beans = event.getApplicationContext().getBeansOfType(InitConfig.class);
        for (Map.Entry<String, InitConfig> configEntry : beans.entrySet()) {
            log.info("start init config:{}", configEntry.getKey());
            try {
                configEntry.getValue().init();
            } catch (Exception ex) {
                log.error(String.format("config '%s' init failed", configEntry.getKey()), ex);
                System.exit(1);
                return;
            }
        }
    }


}
