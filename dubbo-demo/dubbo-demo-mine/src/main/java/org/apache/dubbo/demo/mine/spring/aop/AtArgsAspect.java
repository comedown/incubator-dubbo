package org.apache.dubbo.demo.mine.spring.aop;

import org.apache.dubbo.common.logger.Logger;
import org.apache.dubbo.common.logger.LoggerFactory;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

//@Component
@Aspect
public class AtArgsAspect implements Order {

    private static final Logger LOGGER = LoggerFactory.getLogger(AtArgsAspect.class);

    @Pointcut("@args()")
    private void atArgsPointcut() {
    }

    @Before("atArgsPointcut()")
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
