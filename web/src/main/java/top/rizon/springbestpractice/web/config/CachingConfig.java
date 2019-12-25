package top.rizon.springbestpractice.web.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.Duration;

/**
 * 缓存配置
 *
 * @author Rizon
 * @date 2019-08-28
 */
@Configuration
@EnableCaching
@ConditionalOnProperty(value = "cache.enable", havingValue = "true")
@ConfigurationProperties(prefix = "cache")
public class CachingConfig {
    @Setter
    private Duration expireDuration = Duration.ofMinutes(3);

    /**
     * expireAfterWrite 缓存管理器
     */
    @Primary
    @Bean("expireCacheManager")
    public CacheManager cacheManager() {
        CaffeineCacheManager manager = new CaffeineCacheManager();
        manager.setCaffeine(Caffeine.newBuilder()
                .expireAfterWrite(expireDuration)
        );
        return manager;
    }

}
