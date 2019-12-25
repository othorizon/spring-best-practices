package top.rizon.springbestpractice.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.rizon.springbestpractice.common.model.response.Response;
import top.rizon.springbestpractice.common.utils.http.SimpleRestTemplateUtils;
import top.rizon.springbestpractice.web.config.auth.RestTemplateAuthInterceptor;

import javax.servlet.http.HttpServletRequest;

/**
 * http请求演示
 *
 * @author Rizon
 * @date 2019/12/25
 */
@RestController
@RequestMapping("http")
@RequiredArgsConstructor
@Slf4j
public class HttpController {

    /**
     * 请求服务时拦截器会注入认证信息
     * @see RestTemplateAuthInterceptor
     * @param request
     * @return
     */
    @GetMapping
    public Response<?> restTemplate(HttpServletRequest request) {
        return SimpleRestTemplateUtils.doGetRes(
                request.getRequestURL() + "/result", null, null,
                new ParameterizedTypeReference<Response<String>>() {
                });
    }

    @GetMapping("result")
    public Response<String> requestResult() {
        return Response.success("this is demo result");
    }
}
