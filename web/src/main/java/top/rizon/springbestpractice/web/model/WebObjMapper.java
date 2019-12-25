package top.rizon.springbestpractice.web.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import top.rizon.springbestpractice.dao.po.User;
import top.rizon.springbestpractice.web.model.vo.UserVo;

/**
 * @author Rizon
 * @date 2019/12/25
 */
@Mapper(componentModel = "spring")
public interface WebObjMapper {
    WebObjMapper INSTANCE = Mappers.getMapper(WebObjMapper.class);

    /**
     * 将用户转换为vo对象
     *
     * @param user
     * @return
     */
    @Mapping(source = "token",target = "loginToken")
    UserVo toVo(User user);
}
