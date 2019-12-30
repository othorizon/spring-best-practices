package top.rizon.springbestpractice.common.utils;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

/**
 * @author Rizon
 * @date 2019/12/30
 * @see StreamUtil
 */
public class StreamUtilTest {

    List<Person> persons = Arrays.asList(
            new Person("zhang", "A1", 12, "M"),
            new Person("wang", "B1", 15, "M"),
            new Person("li", "A1", 11, "W"),
            new Person("wu", "B2", 11, "W"));

    /**
     * 使用stream和google的maps工具类的list转map
     */
    @Test
    public void list2Map() {
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

    /**
     * {@code reduce(accumulator) } ：参数是一个执行双目运算的 Functional Interface ，
     * 假如这个参数表示的操作为op，stream中的元素为x, y, z, …，则 reduce() 执行的就是{@code x op y op z ... } ，
     * 所以要求op这个操作具有结合性(associative)，即满足：{@code (x op y) op z = x op (y op z) }，
     * 满足这个要求的操作主要有：求和、求积、求最大值、求最小值、字符串连接、集合并集和交集等。
     * 另外，该函数的返回值是Optional的：
     * <p>
     * <pre>{@code
     * Optional <integer>sum1 = numStream.reduce((x, y) -> x + y);
     * }</pre>
     * <p>
     * {@code reduce(identity, accumulator) } ：可以认为第一个参数为默认值，但需要满足{@code identity op x = x }，
     * 所以对于求和操作， identity 的值为0，对于求积操作， identity 的值为1。返回值类型是stream元素的类型：
     * <p>
     * <pre>{@code
     * Integer sum2 = numStream.reduce(0, Integer::sum);
     * }</pre>
     * <p>
     * reduce 如果不加参数`identity`则返回的是optional类型的，reduce在进行双目运算时，
     * 其中一个场景是与`identity`做比较操作，因此我们应该满足 {@code identity op x = x }
     * <p>
     * 参考：https://www.cnblogs.com/zxf330301/p/6586750.html
     */
    @Test
    public void reduce() {
        Person identity = new Person(null, null, 0, null);
        List<Person> maxAge = persons.stream().collect(
                Collectors.collectingAndThen(
                        //按性别分组
                        Collectors.groupingBy(Person::getSex,
                                //每组取年龄最大的
                                Collectors.reducing(identity, BinaryOperator.maxBy(Comparator.comparing(Person::getAge)))),
                        //合并各组的值
                        p -> new ArrayList<>(p.values()))
        );
        System.out.println(maxAge);
    }

    /**
     * stream并发
     * parallelStream默认使用了fork-join框架，其默认线程数是CPU核心数。
     */
    @Test
    public void parallel() {
        //非并发遍历
        System.out.println("===非并发遍历===");
        persons.forEach(System.out::println);
        //并发遍历
        System.out.println("===并发遍历1===");
        persons.parallelStream().forEach(System.out::println);
        System.out.println("===并发遍历2===");
        persons.parallelStream().forEach(System.out::println);

    }

    @AllArgsConstructor
    @Data
    public static class Person {
        private String name;
        private String location;
        private Integer age;
        private String sex;
    }
}
