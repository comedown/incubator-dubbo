package org.apache.dubbo.demo.mine.spring.aop;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;

import java.lang.annotation.Annotation;

@Aspect
public class TargetAspect implements Order {

    @Override
    public int value() {
        return 0;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
