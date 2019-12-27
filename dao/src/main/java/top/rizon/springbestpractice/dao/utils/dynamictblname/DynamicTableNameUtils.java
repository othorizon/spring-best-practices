package top.rizon.springbestpractice.dao.utils.dynamictblname;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import top.rizon.springbestpractice.common.exception.BaseServerException;
import top.rizon.springbestpractice.common.utils.DataDate;
import top.rizon.springbestpractice.common.utils.DateTimeUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Rizon
 * @date 2019-07-10
 */
@Slf4j
public class DynamicTableNameUtils {
    private static final String HISTORY_FAKE_TABLE_NAME = "HistoryFakeTableName";
    private static final ThreadLocal<DynamicTableNameParam> LOCAL_TABLE_NAME = new ThreadLocal<>();

    /**
     * 注入到mybatis-plus拦截器中
     *
     * @param paginationInterceptor
     */
    public static void registerDynamicTableName(PaginationInterceptor paginationInterceptor) {
        Map<String, ITableNameHandler> tableNameHandlerMap = new HashMap<>(1);
        tableNameHandlerMap.put(HISTORY_FAKE_TABLE_NAME, (metaObject, sql, tableName) -> {
            DynamicTableNameParam nameParam = LOCAL_TABLE_NAME.get();

            if (nameParam != null
                    && tableName.equals(nameParam.getFakeTblName())
                    && StringUtils.isNotBlank(nameParam.getRealTblName())) {
                return nameParam.getRealTblName();
            }
            throw new BaseServerException("动态表名替换错误，没有发现替换值:" + tableName);
        });
        DynamicTableNameParser tableNameParser = new DynamicTableNameParser();
        tableNameParser.setTableNameHandlerMap(tableNameHandlerMap);
        //register
        paginationInterceptor.setSqlParserList(Collections.singletonList(tableNameParser));
    }

    public static void startTableName(String fakeTableName, String tableName) {
        endTableName();
        LOCAL_TABLE_NAME.set(new DynamicTableNameParam(fakeTableName, tableName));
        log.info("start dynamic table query:{}", tableName);
    }

    public static void startHistoryTableName(DataDate date) {
        startTableName(HISTORY_FAKE_TABLE_NAME, "history_" + date.toString(DateTimeUtils.YYYYMMDD_NOLINE));
    }

    public static void endTableName() {
        LOCAL_TABLE_NAME.remove();
    }

    @AllArgsConstructor
    @Getter
    private static class DynamicTableNameParam {
        private String fakeTblName;
        private String realTblName;
    }
}
