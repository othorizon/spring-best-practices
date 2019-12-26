package top.rizon.springbestpractice.common.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * 拦截方法 打印执行日志
 * @author Rizon
 * @date 2019-03-18
 */
@Slf4j
@Aspect
@ConditionalOnProperty(value="dev.log.method-process", havingValue = "true" ,matchIfMissing = true)
@Component
public class MethodLogAspect {

    @Pointcut("execution(* top.rizon.springbestpractice..AopExampleService.*(..)) && !bean(methodLogAspect)")
    public void methodPointcut() {
    }


    @Around("methodPointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            long end = System.currentTimeMillis();
            log.info("执行" + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName()
                    + "方法," + parseParams(joinPoint.getArgs()) + ",耗时:" + (end - start) + " ms!");
            return result;
        } catch (Throwable e) {
            long end = System.currentTimeMillis();
            log.error(joinPoint + ",耗时:" + (end - start) + " ms,抛出异常 :" + e.getMessage());
            throw e;
        }
    }

    @AfterReturning(returning = "ret", pointcut = "methodPointcut()")
    public void doAfterReturning(Object ret) throws Throwable {
        log.info("返回值:" + ret);
    }

    private String parseParams(Object[] parames) {

        if (null == parames || parames.length <= 0) {
            return "该方法没有参数";

        }
        StringBuilder param = new StringBuilder("请求参数 # 个:[ ");
        int i = 0;
        for (Object obj : parames) {
            i++;
            if (i == 1) {
                param.append(obj.toString());
                continue;
            }
            param.append(" ,").append(obj.toString());
        }
        return param.append(" ]").toString().replace("#", String.valueOf(i));
    }
}
