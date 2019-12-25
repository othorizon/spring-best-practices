package top.rizon.springbestpractice.common.model.dto;

/**
 * 认证模块采用接口形式，这样可以方便的替换认证服务和屏蔽实际认证业务的内部逻辑
 *
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

    /**
     * 登陆token
     *
     * @return
     */
    String getToken();
}
