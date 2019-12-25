package top.rizon.springbestpractice.auth;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import top.rizon.springbestpractice.dao.helper.UserHelper;
import top.rizon.springbestpractice.dao.po.User;

/**
 * @author Rizon
 * @date 2019/12/25
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserHelper userHelper;

    @Cacheable(value = "queryUserCacheable")
    @Nullable
    public User queryUserCacheable(String token) {
        return userHelper.getOne(Wrappers.<User>lambdaQuery()
                .eq(User::getToken, token));
    }
}
