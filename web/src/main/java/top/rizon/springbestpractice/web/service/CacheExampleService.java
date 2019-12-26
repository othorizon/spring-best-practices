package top.rizon.springbestpractice.web.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import top.rizon.springbestpractice.common.exception.BaseServerException;
import top.rizon.springbestpractice.web.model.vo.CacheExampleVo;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 缓存使用应当注意只对缓存数据做读操作，避免side-effect，防止污染缓存数据
 *
 * @author Rizon
 * @date 2019/12/25
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CacheExampleService {
    private static final String MANUAL_CACHE_KEY = "MANUAL_CACHE_KEY";
    private Cache<Object, List<CacheExampleVo>> cache;

    @PostConstruct
    public void init() {
        cache = CacheBuilder.newBuilder()
                .maximumSize(5)
                .expireAfterWrite(5, TimeUnit.SECONDS)
                .build();
    }

    /**
     * 与其他的基于Spring Aop代理的注解（如 @Transactional）相同，
     * 必须应用在public方法，而且不能在同类中自调用,否则代理无法生效
     */
    @Cacheable(value = "byAnnotation", cacheManager = "requestScopedCacheManager")
    public List<CacheExampleVo> requestScopedByAnnotation() {
        log.info("init requestScoped cache data");
        return Arrays.asList(new CacheExampleVo(), new CacheExampleVo());
    }

    public List<CacheExampleVo> expireCache() {
        try {
            return cache.get(MANUAL_CACHE_KEY, () -> {
                log.info("init expireCache data");
                return Arrays.asList(new CacheExampleVo(), new CacheExampleVo());
            });
        } catch (ExecutionException e) {
            throw new BaseServerException(e);
        }
    }
}
