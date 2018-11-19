package org.apache.demo.mine.test.advise;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.demo.mine.test.annotation.Info;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@Component
public class DemoAdvice implements MethodBeforeAdvice, AfterReturningAdvice, MethodInterceptor {

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("Before advice：开始");
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.out.println("Around before: invocation=[" + invocation + "]");
        Object[] args = invocation.getArguments();
        Method method = invocation.getMethod();
        Info info = AnnotationUtils.findAnnotation(method, Info.class);

        Object result = null;
        if(info == null) {
            result = invocation.proceed();
        } else {
            result = "Hello," + info.value();
        }

        System.out.println("Around after");

        return result;
    }

    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        System.out.println("After returning advice：结束");
    }
}
