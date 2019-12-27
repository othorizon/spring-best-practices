package top.rizon.springbestpractice.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.rizon.springbestpractice.common.model.request.PageParam;
import top.rizon.springbestpractice.common.model.response.PageResponse;
import top.rizon.springbestpractice.common.model.response.Response;
import top.rizon.springbestpractice.common.utils.DataDate;
import top.rizon.springbestpractice.web.service.SqlExampleService;

/**
 * @author Rizon
 * @date 2019/12/27
 */
@RestController
@RequestMapping("demo/sql")
@RequiredArgsConstructor
@Slf4j
public class SqlExampleController {
    private final SqlExampleService service;

    /**
     * 该案例中，历史数据为按日期拆分的表(history_20191227)，通过动态表名实现 按日期查询分表
     * <p>
     * 演示了动态表名和PageHelper
     *
     * @param date      默认值为本案例提供的演示表
     * @param pageParam nullable 为null则不分页
     */
    @GetMapping("queryHistory")
    public Response<?> queryDateTable(@RequestParam(required = false, defaultValue = "2019-12-27") DataDate date, @Validated PageParam pageParam) {
        if (pageParam != null) {
            return PageResponse.success(service.selectHistoryByDate(date, pageParam), pageParam);
        } else {
            return Response.success(service.selectHistoryByDate(date, null));
        }
    }
}
