package top.rizon.springbestpractice.common.model.request;

import lombok.Data;

import javax.validation.Valid;

/**
 * 公共请求参数
 *
 * @author Rizon
 * @date 2019-02-13
 */
@Data
public class BaseReqParam {
    @Valid
    private PageParam pageParam = new PageParam();
}
