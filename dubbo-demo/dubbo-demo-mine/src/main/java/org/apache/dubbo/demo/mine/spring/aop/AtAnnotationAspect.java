package org.apache.dubbo.demo.mine.spring.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Component
@Aspect
public class AtAnnotationAspect implements Order {

    @Pointcut("@annotation(org.apache.dubbo.demo.mine.Young)")
    private void atAnnotationPointCut() {
    }

    @Before("atAnnotationPointCut()")
    private void beforeAdvice1() {
        System.out.println("beforeAdvice1");
    }

    @Before("atAnnotationPointCut()")
    private void beforeAdvice() {
        System.out.println("beforeAdvice0");
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
