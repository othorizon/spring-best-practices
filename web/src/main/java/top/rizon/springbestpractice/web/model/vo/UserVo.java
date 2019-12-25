package top.rizon.springbestpractice.web.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author Rizon
 * @date 2019/12/25
 */
@Data
public class UserVo {
    private Long id;
    @JsonProperty("userName")
    private String name;
    private Integer age;
    private String email;
    private String loginToken;
}
