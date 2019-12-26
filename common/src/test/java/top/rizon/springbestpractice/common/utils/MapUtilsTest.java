package top.rizon.springbestpractice.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

/**
 * @author Rizon
 * @date 2019/12/26
 */
public class MapUtilsTest {

    @Test
    public void flatMap() {
        String json = "{" +
                "    \"1\":\"value 1\"," +
                "    \"2\":{" +
                "        \"2.1\":{" +
                "            \"2.1.1\":\"value 2.1.1\"" +
                "        }" +
                "    }" +
                "}";
        Map<String, Object> map = JSONObject.parseObject(json, new TypeReference<Map<String, Object>>() {
        });
        Map<String, Object> flatMap = MapUtils.flatMap(map, ":");
        Assert.assertEquals("{\"1\":\"value 1\",\"2:2.1:2.1.1\":\"value 2.1.1\"}",
                JSONObject.toJSONString(flatMap));
    }

    @Test
    public void formatMap() {
        String json = "{\"1\":\"value 1\",\"2:2.1:2.1.1\":\"value 2.1.1\"}";
        Map<String, Object> map = JSONObject.parseObject(json, new TypeReference<Map<String, Object>>() {
        });
        Map<String, Object> formatMap = MapUtils.formatMap(map, ":");
        Assert.assertEquals("{\"1\":\"value 1\",\"2\":{\"2.1\":{\"2.1.1\":\"value 2.1.1\"}}}",
                JSONObject.toJSONString(formatMap));
    }
}
