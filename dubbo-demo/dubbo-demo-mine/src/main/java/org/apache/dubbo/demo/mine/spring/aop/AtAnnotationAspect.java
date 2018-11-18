package org.apache.dubbo.demo.mine.spring.aop;

import org.apache.dubbo.common.logger.Logger;
import org.apache.dubbo.common.logger.LoggerFactory;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Component
@Aspect
public class AtAnnotationAspect implements Order {

    private static final Logger LOGGER = LoggerFactory.getLogger(AtAnnotationAspect.class);

    @Pointcut("@annotation(org.apache.dubbo.demo.mine.Young)")
    private void atAnnotationPointCut() {
    }

    @Before("atAnnotationPointCut()")
    private void beforeAdvice1() {
//        LOGGER.info("beforeAdvice1");
        System.out.println(this.getClass().getName() + " -> beforeAdvice1");
    }

    @Before("atAnnotationPointCut()")
    private void beforeAdvice() {
//        LOGGER.info("beforeAdvice0");
        System.out.println(this.getClass().getName() + " -> beforeAdvice0");
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
