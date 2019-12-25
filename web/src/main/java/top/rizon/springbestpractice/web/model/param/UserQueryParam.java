package top.rizon.springbestpractice.web.model.param;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.rizon.springbestpractice.common.model.request.BaseReqParam;

/**
 * @author Rizon
 * @date 2019/12/25
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserQueryParam extends BaseReqParam {
    private String emailLike;
}
