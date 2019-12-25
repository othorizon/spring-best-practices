package top.rizon.springbestpractice.web.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 健康检查
 *
 * @author Rizon
 * @date 2019/12/25
 */
@Slf4j
@RestController
@RequestMapping("/health")
@RequiredArgsConstructor
public class ServerHealthController {
    private final VersionInfo versionInfo;


    /**
     * 服务心跳检测接口
     *
     * @return
     */
    @GetMapping(value = "/ping")
    public String ping() {
        return "pong";
    }


    @GetMapping
    public ServerHealthResponse healthController() {
        return new ServerHealthResponse();
    }

    @GetMapping("version")
    public VersionInfo buildVersion() {
        return versionInfo;
    }


    @Data
    @Component
    @ConfigurationProperties(prefix = "project-build-version-info")
    public static class VersionInfo {
        private String version;
        private String buildTimestamp;
        private String scmVersion;
    }

    @Data
    public static class ServerHealthResponse {
        private int status = 200;
        private String message = "success";
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date serverTime = new Date();
    }
}
