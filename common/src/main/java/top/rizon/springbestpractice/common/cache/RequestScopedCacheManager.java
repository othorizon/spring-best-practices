package top.rizon.springbestpractice.common.cache;

import org.jetbrains.annotations.NotNull;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * request请求域的缓存工具
 *
 * @author Rizon
 * @date 2019/12/13
 */
public class RequestScopedCacheManager implements CacheManager {

    private static final ThreadLocal<Map<String, Cache>> THREAD_LOCAL_CACHE = ThreadLocal.withInitial(ConcurrentHashMap::new);

    @Override
    public Cache getCache(@NotNull String name) {
        final Map<String, Cache> cacheMap = THREAD_LOCAL_CACHE.get();
        return cacheMap.computeIfAbsent(name, this::createCache);
    }

    private Cache createCache(String name) {
        return new ConcurrentMapCache(name);
    }

    @NotNull
    @Override
    public Collection<String> getCacheNames() {
        return THREAD_LOCAL_CACHE.get().keySet();
    }

    /**
     * threadLocal使用必须主动remove防止内存泄漏
     */
    public void clearCaches() {
        THREAD_LOCAL_CACHE.remove();
    }

}
