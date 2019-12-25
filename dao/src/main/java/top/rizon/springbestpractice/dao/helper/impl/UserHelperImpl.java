package top.rizon.springbestpractice.dao.helper.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.rizon.springbestpractice.dao.helper.UserHelper;
import top.rizon.springbestpractice.dao.mapper.UserMapper;
import top.rizon.springbestpractice.dao.po.User;

/**
 * @author Rizon
 * @date 2019/12/25
 */
@Service
public class UserHelperImpl extends ServiceImpl<UserMapper, User> implements UserHelper {
}
