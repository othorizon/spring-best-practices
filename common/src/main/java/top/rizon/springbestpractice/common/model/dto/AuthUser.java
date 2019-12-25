package top.rizon.springbestpractice.common.model.dto;

/**
 * @author Rizon
 * @date 2019/12/3
 */
public interface AuthUser {
    /**
     * 用户Id
     *
     * @return id
     */
    long getId();

    /**
     * 用户名
     *
     * @return name
     */
    String getName();
}
