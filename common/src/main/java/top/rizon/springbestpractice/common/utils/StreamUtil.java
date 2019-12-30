package top.rizon.springbestpractice.common.utils;

import lombok.NonNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * java8 stream 的一些用法的封装
 *
 * @author Rizon
 * @date 2019/12/30
 */
public class StreamUtil {

    public static <T, K> Map<K, T> list2Map(@NonNull Collection<T> list, @NonNull Function<? super T, K> keyFunc) {
        return list.stream().collect(Collectors.toMap(keyFunc, Function.identity(),
                (u, v) -> {
                    throw new IllegalStateException(String.format("Multiple entries with same key,%s=%s,%s=%s",
                            keyFunc.apply(u), u,
                            keyFunc.apply(v), v));
                },
                HashMap::new));
    }
}
