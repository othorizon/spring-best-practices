package top.rizon.springbestpractice.web.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Rizon
 * @date 2019/12/19
 */
@EnableAsync
@Configuration
@ConfigurationProperties("async-conf")
@Data
public class AsyncThreadPoolConfig {
    /**
     * 核心线程数（默认线程数）
     */
    private Integer corePoolSize = 20;
    /**
     * 最大线程数
     */
    private Integer maxPoolSize = 100;
    /**
     * 允许线程空闲时间（单位：默认为秒）
     */
    private Integer keepAliveTime = 10;
    /**
     * 缓冲队列大小
     */
    private Integer queueCapacity = 200;
    /**
     * 线程池名前缀
     */
    private String threadNamePrefix = "Async-Task-";

    @Bean
    public ThreadPoolTaskExecutor asyncTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveTime);
        executor.setThreadNamePrefix(threadNamePrefix);

        // 线程池对拒绝任务的处理策略
        // CallerRunsPolicy：由调用线程（提交任务的线程）处理该任务
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 初始化
        executor.initialize();
        return executor;
    }

}
