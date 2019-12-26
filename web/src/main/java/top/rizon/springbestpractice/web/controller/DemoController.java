package top.rizon.springbestpractice.web.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import top.rizon.springbestpractice.common.constant.DateFormatType;
import top.rizon.springbestpractice.common.model.response.Response;
import top.rizon.springbestpractice.common.utils.DataDate;
import top.rizon.springbestpractice.web.service.AopExampleService;

/**
 * @author Rizon
 * @date 2019/12/26
 */
@RestController
@RequestMapping("demo")
@RequiredArgsConstructor
@Slf4j
public class DemoController {
    private final AopExampleService aopService;

    @GetMapping("aop")
    public ResponseEntity<?> aopDemo() {
        return ResponseEntity.ok(aopService.sleepMethod());
    }

    @GetMapping("badRequest")
    public ResponseEntity<?> badRequest() {
        return ResponseEntity.badRequest().body("http status 400");
    }

    /**
     * 演示了jsonCreator的使用
     * 和枚举实现的简单策略模式，避免过于复杂的if-else
     * <p>
     * 示例：
     * <p>
     * POST /demo/formatDate
     * <p>
     * {
     * "date":"2019-10-10",
     * "formatType":1
     * }
     */
    @PostMapping("formatDate")
    public Response<String> formatDate(@RequestBody DateFormatParam param) {
        return Response.success(param.getFormatType().format(param.getDate()));
    }

    @Data
    public static class DateFormatParam {
        /**
         * 通过配置jsonCreator指定jackson转换对象时的构造函数
         */
        private DataDate date;
        /**
         * 枚举类也可以直接作为参数接收，
         * 在没有特殊处理情况下会使用name去转换
         * 但是可以通过配置jsonCreator 去指定jackson的构造函数
         */
        private DateFormatType formatType;
    }

}
