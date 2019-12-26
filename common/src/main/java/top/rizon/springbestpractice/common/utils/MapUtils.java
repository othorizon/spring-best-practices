package top.rizon.springbestpractice.common.utils;

import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * top.rizon.springbestpractice.common.utils.MapUtilsTest
 *
 * @author Rizon
 * @date 2019-06-22
 */
public class MapUtils {

    public static List<Map<String, Object>> flatMaps(@NonNull List<Map<String, Object>> maps, String joinKey) {
        return maps.stream().map(map -> flatMap(map, joinKey)).collect(Collectors.toList());
    }

    /**
     * map扁平化处理
     *
     * @param map
     * @param joinKey 拼接key的连接字符
     * @return
     */
    public static Map<String, Object> flatMap(@NonNull Map<String, Object> map, String joinKey) {
        return recursiveFlat(map, joinKey, new ArrayList<>());
    }

    public static List<Map<String, Object>> formatMaps(@NonNull List<Map<String, Object>> maps, String joinKey) {
        return maps.stream().map(map -> formatMap(map, joinKey)).collect(Collectors.toList());
    }

    /**
     * 将扁平化的map还原
     *
     * @param map
     * @param joinKey 扁平化时key的连接字符，用于分割key
     * @return
     */
    public static Map<String, Object> formatMap(@NonNull Map<String, Object> map, String joinKey) {
        Map<String, Object> result = new HashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            String[] keyPath = key.split(joinKey);
            putIntoMapByPath(result, value, keyPath);
        }
        return result;
    }


    private static Map<String, Object> recursiveFlat(Map<String, Object> map, String joinKey, List<String> path) {
        Map<String, Object> result = new HashMap<>();

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof Map) {
                ArrayList<String> newPth = new ArrayList<>(path);
                newPth.add(key);
                Map<String, Object> subMap = recursiveFlat((Map<String, Object>) value, joinKey, newPth);
                result.putAll(subMap);
            } else {
                String parentPath = StringUtils.join(path, joinKey);
                result.put(StringUtils.isEmpty(parentPath) ? key : parentPath.concat(joinKey).concat(key), value);
            }

        }
        return result;
    }

    private static void putIntoMapByPath(Map<String, Object> result, Object value, String[] keyPath) {
        Map<String, Object> map = result;
        for (int i = 0; i < keyPath.length - 1; i++) {
            String key = keyPath[i];
            if (result.containsKey(key)) {
                map = (Map) map.get(key);
            } else {
                HashMap<String, Object> newMap = new HashMap<>();
                map.put(key, newMap);
                map = newMap;
            }
        }
        String lastKey = keyPath[keyPath.length - 1];
        map.put(lastKey, value);
    }
}
