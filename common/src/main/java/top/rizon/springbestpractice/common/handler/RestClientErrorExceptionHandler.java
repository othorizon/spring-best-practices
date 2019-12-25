package top.rizon.springbestpractice.common.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientResponseException;
import top.rizon.springbestpractice.common.exception.BaseServerException;

/**
 * @author Rizon
 * @date 2019/12/13
 */
@Slf4j
public class RestClientErrorExceptionHandler {

    /**
     * 处理异常
     *
     * @param ex
     */
    public <R> R doHandler(RestClientResponseException ex) {
        if (ex instanceof HttpClientErrorException.Unauthorized) {
            throw new BaseServerException("request third-part server auth failed")
                    .setErrMessage("request third-part server auth failed:" + ex);
        }
        throw new BaseServerException("request failed:%s", ex.getResponseBodyAsString()).setErrMessage("request failed:" + ex);
    }

}
