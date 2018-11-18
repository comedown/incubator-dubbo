package org.apache.dubbo.demo.mine.spring.aop;

import org.apache.dubbo.common.logger.Logger;
import org.apache.dubbo.common.logger.LoggerFactory;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;

import java.lang.annotation.Annotation;

/**
 * execution(修饰符? 返回类型 声明类型?方法名称(参数) 抛出异常?)、
 * 修饰符匹配、声明类型匹配、抛出异常匹配（带?的）是可选的
 * 返回类型匹配、方法名称匹配、参数匹配是必须的
 */
@Aspect
public class ExecutionAspect implements Order {

    private final static Logger LOGGER = LoggerFactory.getLogger(ExecutionAspect.class);

    @Pointcut("execution(* *.*.*(..))")
    private void executionPointCut() {

    }

    @Before("executionPointCut()")
    private void beforeAdvice() {
//        LOGGER.info("beforeAdvice");
        System.out.println(this.getClass().getName() + " -> beforeAdvice");
    }

    @Override
    public int value() {
        return 0;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
