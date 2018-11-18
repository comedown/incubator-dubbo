package org.apache.dubbo.demo.mine.spring.aop;

import org.apache.dubbo.common.logger.Logger;
import org.apache.dubbo.common.logger.LoggerFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Component
@Aspect
public class ArgAspect implements Order {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArgAspect.class);

    @Pointcut("@args()")
    private void argPointCut() {
    }

    @Before("argPointCut()")
    private void beforeAdvice(JoinPoint point) {
//        LOGGER.info("beforeAdvice");
        System.out.println(this.getClass().getName() + " -> beforeAdvice " + point.getSignature().getName());
    }

    @After("argPointCut()")
    private void afterAdvice(JoinPoint point) {
        System.out.println(this.getClass().getName() + " -> afterReturningAdvice " + point.getSignature().getName());
    }

    @AfterReturning(pointcut = "argPointCut()", returning = "retVal")
    private void afterReturningAdvice(JoinPoint point, Object retVal) {
        if(retVal instanceof Void) {
            System.out.println(this.getClass().getName() + " -> return void.");
        }
        System.out.println(this.getClass().getName() + " -> afterReturningAdvice " + point.getSignature().getName());
    }

    @AfterThrowing(pointcut = "argPointCut()", throwing = "ex")
    private void afterThrowingAdvice(JoinPoint point, Exception ex) {
        System.out.println(this.getClass().getName() + " -> afterThrowingAdvice" + point.getSignature().getName());
    }

    @Around("argPointCut()")
    private void aroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println(this.getClass().getName() + " -> aroundAdvice before method.");
        proceedingJoinPoint.proceed();
        System.out.println(this.getClass().getName() + " -> aroundAdvice after method.");
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
