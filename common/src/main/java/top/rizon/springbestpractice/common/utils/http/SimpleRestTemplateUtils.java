package top.rizon.springbestpractice.common.utils.http;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import top.rizon.springbestpractice.common.exception.BaseServerException;
import top.rizon.springbestpractice.common.handler.RestClientErrorExceptionHandler;
import top.rizon.springbestpractice.common.model.response.Response;
import top.rizon.springbestpractice.common.utils.ObjUtil;

import java.net.URI;
import java.util.Map;

/**
 * @author Rizon
 * @date 2019/12/2
 */
@Component
@Slf4j
public class SimpleRestTemplateUtils {
    private static final RestClientErrorExceptionHandler DEFAULT_ERROR_HANDLER = new RestClientErrorExceptionHandler();
    @Setter
    private Integer connectTimeout = 3000;
    @Setter
    private Integer readTimeout = 6000;
    @Getter
    private static RestTemplate restTemplate;

    public static <R> R doGetRes(String url, Map<String, String> queryMap, HttpHeaders httpHeaders, ParameterizedTypeReference<R> responseType) {
        return doGetRes(url, queryMap, httpHeaders, responseType, null);
    }

    public static <R> R doGetRes(String url, Map<String, String> queryMap, HttpHeaders httpHeaders, ParameterizedTypeReference<R> responseType, RestClientErrorExceptionHandler exceptionHandler) {
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        if (queryMap != null) {
            params.setAll(queryMap);
        }
        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParams(params)
                .encode().build().toUri();
        log.info("get uri:{}", uri);

        ResponseEntity<R> exchange;
        try {
            exchange = restTemplate.exchange(uri, HttpMethod.GET,
                    new HttpEntity<>(httpHeaders), responseType);
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            if (exceptionHandler == null) {
                return DEFAULT_ERROR_HANDLER.doHandler(ex);
            } else {
                return exceptionHandler.doHandler(ex);
            }
        }
        return exchange.getBody();
    }

    @Nullable
    public static <T, R> R doPost(String url, T queryMap, ParameterizedTypeReference<Response<R>> responseType) {
        Response<R> response = doPostRes(url, queryMap, responseType);
        responseCheck(response);
        return response.getResult();
    }

    @Nullable
    public static <T, R> R doPostRes(String url, T queryMap, ParameterizedTypeReference<R> responseType) {
        return doPostRes(url, queryMap, responseType, null);
    }

    @Nullable
    public static <T, R> R doPostRes(String url, T queryMap, ParameterizedTypeReference<R> responseType, RestClientErrorExceptionHandler exceptionHandler) {
        log.info("post url:{},param:{}", url, queryMap);
        HttpHeaders authHeader = new HttpHeaders();
        authHeader.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<T> entity = new HttpEntity<>(queryMap, authHeader);
        ResponseEntity<R> exchange;
        try {
            exchange = restTemplate.exchange(url, HttpMethod.POST, entity, responseType);
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            if (exceptionHandler == null) {
                return DEFAULT_ERROR_HANDLER.doHandler(ex);
            } else {
                return exceptionHandler.doHandler(ex);
            }
        }
        return exchange.getBody();
    }

    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
        SimpleRestTemplateUtils.restTemplate = new RestTemplate(factory);
        return SimpleRestTemplateUtils.restTemplate;
    }

    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(ObjUtil.defaultValue(connectTimeout, 3000));
        factory.setReadTimeout(ObjUtil.defaultValue(readTimeout, 5000));
        return factory;
    }

    @Contract("null -> fail")
    public static void responseCheck(@Nullable Response<?> response) {
        if (response == null || response.getStatus() != Response.CODE_SUCCESS) {
            throw new BaseServerException("http请求返回数据错误:" + response);
        }
    }

}
