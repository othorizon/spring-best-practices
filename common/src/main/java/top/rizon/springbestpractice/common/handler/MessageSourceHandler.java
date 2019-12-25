package top.rizon.springbestpractice.common.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Rizon
 * @date 2019/12/13
 */
@Component
@RequiredArgsConstructor
public class MessageSourceHandler {
    private final HttpServletRequest request;
    private final MessageSource messageSource;

    public String getMessage(String code, @Nullable Object[] args, @Nullable String defaultMessage) {
        return messageSource.getMessage(code, args, defaultMessage, RequestContextUtils.getLocale(request));
    }


    public String getMessage(String code, @Nullable Object[] args) {
        return messageSource.getMessage(code, args, RequestContextUtils.getLocale(request));
    }
}
