package top.rizon.springbestpractice.common.utils;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Rizon
 * @date 2019/12/30
 * @see StreamUtil
 */
public class StreamUtilTest {


    @Test
    public void list2Map() {
        List<Person> persons = Arrays.asList(
                new Person("zhang", "A1"),
                new Person("wang", "B1"),
                new Person("li", "A1"),
                new Person("wu", "B2"));
        // java8 stream 的list转map
        Map<String, Person> byName = StreamUtil.list2Map(persons, Person::getName);
        System.out.println("java8 stream: " + byName);
        // google maps工具类
        byName = Maps.uniqueIndex(persons, Person::getName);
        System.out.println("maps util: " + byName);

        System.out.println("====key重复的错误信息提示====");
        //key重复的错误信息提示
        try {
            StreamUtil.list2Map(persons, Person::getLocation);
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }

        try {
            Maps.uniqueIndex(persons, Person::getLocation);
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }

    @AllArgsConstructor
    @Data
    public static class Person {
        private String name;
        private String location;
    }
}
