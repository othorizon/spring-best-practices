package cn.boommanpro.config.valid.config;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * @author <a href="mailto:boommanpro@gmail.com">BoomManPro</a>
 * @date 2019/12/26 13:51
 */
@Data
@Validated
@ConfigurationProperties(value = "authentication.cas-config")
public class CasConfigProperties {

    /**
     * CAS服务登录地址 e.g https://cas.example.org:8443/cas
     */
    @NotBlank(message = "casServerUrl不能为空 e.g https://cas.example.org:8443/cas")
    private String casServerUrl;

    /**
     * CAS服务登录地址 https://cas.example.org:8443/cas/login
     */
    @NotBlank(message = "casServerLoginUrl不能为空 e.g https://cas.example.org:8443/cas/login")
    private String casServerLoginUrl;

    /**
     * CAS服务登出地址 e.g https://cas.example.org:8443/cas/logout?service=${authentication.cas-config.app-server-url}
     */
    @NotBlank(message = "casServerLogoutUrl不能为空 e.g https://cas.example.org:8443/cas/logout?service=${authentication.cas-config.app-server-url}")
    private String casServerLogoutUrl;

    /**
     * app地址 e.g http://localhost:8080
     */
    @NotBlank(message = "appServerUrl不能为空 e.g http://localhost:8080")
    private String appServerUrl;

    /**
     * app 登录地址 e.g /login"
     */
    @NotBlank(message = "appLoginUrl不能为空 e.g /login")
    private String appLoginUrl;

    /**
     * app登出地址 e.g /api/logout
     */
    @NotBlank(message = "appLogoutUrl不能为空 e.g /api/logout")
    private String appLogoutUrl;
}
