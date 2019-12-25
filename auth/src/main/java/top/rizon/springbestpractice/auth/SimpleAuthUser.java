package top.rizon.springbestpractice.auth;

import lombok.Data;
import top.rizon.springbestpractice.common.model.dto.AuthUser;

/**
 * @author Rizon
 * @date 2019/12/25
 */
@Data
public class SimpleAuthUser implements AuthUser {
    private Long id;
    private String name;
    private String token;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getToken() {
        return token;
    }
}
