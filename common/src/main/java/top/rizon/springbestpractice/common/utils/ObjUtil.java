package top.rizon.springbestpractice.common.utils;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author Rizon
 * @date 2019/12/25
 */
public class ObjUtil {

    public static <T> T defaultValue(@Nullable T value, @NonNull T defaultValue) {
        return value == null ? defaultValue : value;
    }
}
