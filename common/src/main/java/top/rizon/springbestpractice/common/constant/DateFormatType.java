package top.rizon.springbestpractice.common.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import top.rizon.springbestpractice.common.exception.BaseServerException;
import top.rizon.springbestpractice.common.utils.DataDate;
import top.rizon.springbestpractice.common.utils.DateTimeUtils;

import java.util.Arrays;

/**
 * 借助枚举实现的策略模式，避免过于复杂的if-else的代码
 *
 * @author Rizon
 * @date 2019/12/26
 */
@Getter
@AllArgsConstructor
public enum DateFormatType {
    /**/
    DATE("DATE", 0) {
        @Override
        public String format(DataDate dataDate) {
            return dataDate.toString(DateTimeUtils.YYYYMMDD);
        }
    },
    DATE_TIME("DATE_TIME", 1) {
        @Override
        public String format(DataDate dataDate) {
            return dataDate.toString(DateTimeUtils.YYYYMMDDHHMMSS);
        }
    },
    TIME("TIME", 2) {
        @Override
        public String format(DataDate dataDate) {
            return dataDate.toString(DateTimeUtils.HHMMSS);
        }
    };
    private String code;
    @JsonValue
    private Integer type;

    /**
     * 格式化日期
     */
    public abstract String format(DataDate dataDate);

    @JsonCreator
    public static DateFormatType valueOfByType(Integer type) {
        return Arrays.stream(DateFormatType.values())
                .filter(t -> t.getType().equals(type))
                .findAny()
                .orElseThrow(() -> new BaseServerException("error DateFormatType:" + type));
    }

    /**
     * 即使参数为int类型,requestBody转换时依然会按照string处理
     *
     * @param type
     * @return
     */
    @JsonCreator
    public static DateFormatType valueOfByType(String type) {
        return valueOfByType(type == null ? null : Integer.parseInt(type));
    }
}
