package top.rizon.springbestpractice.auth;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import top.rizon.springbestpractice.dao.po.User;

/**
 * @author Rizon
 * @date 2019/12/25
 */
@Mapper(componentModel = "spring")
public interface AuthObjMapper {
    AuthObjMapper INSTANCE = Mappers.getMapper(AuthObjMapper.class);

    /**
     * 将用户转换为authUser
     *
     * @param user
     * @return
     */
    SimpleAuthUser toAuthUser(User user);
}
