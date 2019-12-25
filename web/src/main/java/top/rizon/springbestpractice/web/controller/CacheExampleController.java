package top.rizon.springbestpractice.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.rizon.springbestpractice.common.model.response.Response;
import top.rizon.springbestpractice.web.service.CacheExampleService;

/**
 * 缓存的几种写法
 * 使用缓存是要保证对缓存数据的操作是无副作用的，防止污染缓存数据
 *
 * @author Rizon
 * @date 2019/12/25
 */
@RestController
@RequestMapping("cache")
@RequiredArgsConstructor
@Slf4j
public class CacheExampleController {
    private final CacheExampleService service;

    /**
     * 使用spring的缓存注解
     * 一个请求域的缓存，只在当前请求中使用
     *
     * @return
     */
    @GetMapping("byAnnotation")
    public Response<?> byAnnotation() {
        log.info("requestScoped cache:{}", service.requestScopedByAnnotation());
        return Response.success(service.requestScopedByAnnotation());
    }

    /**
     * 到期失效的缓存
     * 不使用spring的缓存注解
     *
     * @return
     */
    @GetMapping("expireCache")
    public Response<?> expireCache() {
        log.info("expireCache:{}", service.expireCache());
        return Response.success(service.expireCache());
    }
}
