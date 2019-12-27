package top.rizon.springbestpractice.web.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.rizon.springbestpractice.common.model.request.PageParam;
import top.rizon.springbestpractice.common.utils.DataDate;
import top.rizon.springbestpractice.dao.mapper.HistoryMapper;
import top.rizon.springbestpractice.dao.po.HistoryPo;
import top.rizon.springbestpractice.dao.utils.dynamictblname.DynamicTableNameUtils;
import top.rizon.springbestpractice.web.controller.UserController;

import java.util.List;

/**
 * @author Rizon
 * @date 2019/12/27
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SqlExampleService {
    private final HistoryMapper historyMapper;

    /**
     * 动态表名demo,
     * 查询指定日期的分表
     * <p>
     * PageHelper实现的一种分页方式演示,
     * 与动态表名原理一样，都是threadLocal的全局变量注入到sql中
     * <p>
     * <p>
     * <b>不推荐PageHelper分页方式，仅作演示，mybatis-plus实现了分页逻辑</b>
     *
     * @see UserController#list Mybatis-Plus的分页方式
     */
    public List<HistoryPo> selectHistoryByDate(DataDate date, PageParam pageParam) {
        DynamicTableNameUtils.startHistoryTableName(date);

        Page<Object> page = null;
        if (pageParam != null) {
            page = PageHelper.startPage(pageParam.getPage(), pageParam.getPageSize(), true);
        }

        List<HistoryPo> historyPos = historyMapper.selectList(Wrappers.emptyWrapper());

        if (page != null) {
            pageParam.setTotalPage(page.getPages())
                    .setTotalRecord(page.getTotal());
        }

        //清理ThreadLocal
        DynamicTableNameUtils.endTableName();
        PageHelper.clearPage();
        return historyPos;
    }
}
