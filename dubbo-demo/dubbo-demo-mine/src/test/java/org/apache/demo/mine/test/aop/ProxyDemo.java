package org.apache.demo.mine.test.aop;

import org.apache.demo.mine.test.service.DemoServiceImpl;
import org.apache.dubbo.demo.DemoService;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ProxyDemo {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext("org.apache.demo.mine.test");

        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setBeanFactory(ac.getBeanFactory());
        proxyFactoryBean.setInterfaces(DemoService.class);
        proxyFactoryBean.setTarget(new DemoServiceImpl());
        proxyFactoryBean.setInterceptorNames("demoAdvice");

        DemoService demoService = (DemoService) proxyFactoryBean.getObject();
        System.out.println(demoService.sayHello(""));
    }

}
