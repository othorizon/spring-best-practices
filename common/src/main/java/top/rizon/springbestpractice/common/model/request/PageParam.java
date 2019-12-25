package top.rizon.springbestpractice.common.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;

/**
 * @author Rizon
 * @date 2019/12/3
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class PageParam {
    /**
     * 当前页
     */
    @Min(value =1,message = "{min.page}")
    private int page = 1;

    /**
     * 页大小
     */
    @Min(value =1,message = "{min.pageSize}")
    private int pageSize = 10;

    /**
     * 总页数
     */
    private long totalPage;

    /**
     * 总记录数
     */
    private long totalRecord;

}
