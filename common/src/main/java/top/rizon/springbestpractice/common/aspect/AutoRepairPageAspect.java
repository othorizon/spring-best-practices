package top.rizon.springbestpractice.common.aspect;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import top.rizon.springbestpractice.common.model.response.PageResponse;

import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.util.Collection;

/**
 * <p>分页查询的切片处理</p>
 * <b>只对controller包下的类生效</b>
 * <p>当分页查询的页码参数大于最后一页，则改为最后一页的页码重新查询数据</p>
 * <p>使用配置 {@code base-server.auto-repair-page} 全局禁用该功能</p>
 * <p>使用注解 {@link DisableAutoRepairPage} 在方法上禁用该功能</p>
 *
 * <p>
 * 其实spring对于aop的实现是通过动态代理（jdk的动态代理或者cglib的动态代理），
 * 它只是使用了aspectJ的Annotation，并没有使用它的编译期和织入器,
 * 所以受限于这点，有些增强就做不到，比如 调用自己的方法就无法走代理
 * </p>
 * <p>
 * 参考：
 * AspectJ语法：https://blog.csdn.net/sunlihuo/article/details/52701548
 * AspectJ与Spring Aop的区别：https://zhuanlan.zhihu.com/p/50612298
 *
 * @author Rizon
 * @date 2019-09-25
 */
@Slf4j
@Aspect
@Component
@ConditionalOnProperty(matchIfMissing = true, value = "base-server.auto-repair-page", havingValue = "true")
public class AutoRepairPageAspect {

    /**
     * className+ 表示匹配子类型
     */
    @Pointcut("execution(top.rizon.springbestpractice.common.model.response.Response+ top.rizon.springbestpractice..controller..*(..))" +
            " && !@annotation(top.rizon.springbestpractice.common.aspect.AutoRepairPageAspect.DisableAutoRepairPage)")
    public void methodPointcut() {
    }

    @Around("methodPointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        try {
            //只有返回的是PageResponseParam 才会处理
            if (!(result instanceof PageResponse)) {
                return result;
            }
            PageResponse<?> pageResponse = (PageResponse<?>) result;
            /*
            不进行空数据的判断，只判断页码
            if (!hasEmptyData(pageResponse)) {
                return result;
            }
            */
            if (pageResponse.getPagination() == null) {
                return result;
            }
            if (pageResponse.getPagination().getTotalPage() < 1) {
                //当总页码为0时，则设置当前页码为1 并直接返回结果
                pageResponse.getPagination().setPage(1);
                return result;
            }
            if (pageResponse.getPagination().getPage() <= pageResponse.getPagination().getTotalPage()) {
                return result;
            }
            //如果页码是超过了最后一页那么将页面改为最后一页 重新执行方法

            //设置新的页码 因为response中的pageParam对象和参数中的pageParam对象是同一个对象，所以
            pageResponse.getPagination().setPage((int) pageResponse.getPagination().getTotalPage());
            //重新执行方法
            log.info("page greatThan totalPage, reset page and reProceed method");
            return joinPoint.proceed();
        } catch (Exception ex) {
            log.error("aop process PageResponseParam failed", ex);
        }
        return result;
    }

    /**
     * 是否包含任意一个 使用了tag标记的空值属性
     *
     * @return
     */
    private boolean hasEmptyData(Object dataObj) throws IllegalAccessException {
        for (Field field : FieldUtils.getFieldsListWithAnnotation(dataObj.getClass(), ResDataTag.class)) {

            ResDataTag tag = field.getAnnotation(ResDataTag.class);
            Object value = FieldUtils.readField(field, dataObj, true);

            if (tag.nullAsEmpty() && value == null) {
                return true;
            }
            if (value instanceof Collection && CollectionUtils.isEmpty((Collection<?>) value)) {
                return true;
            }
            //递归查看
            if (hasEmptyData(value)) {
                return true;
            }
        }
        return false;
    }

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ResDataTag {
        /**
         * 如果值是null是否也看作返回了空对象去重新计算页面查询数据
         * 默认false
         * 不推荐使用null作为判断条件
         */
        boolean nullAsEmpty() default false;
    }

    /**
     * 禁用自动修复页码功能
     */
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    public @interface DisableAutoRepairPage {
    }

}
