package org.apache.demo.mine.test.aop;

import org.apache.dubbo.demo.mine.spring.aop.LockMixin;
import org.apache.dubbo.demo.mine.spring.aop.LockMixinAdvisor;
import org.apache.dubbo.demo.mine.Lockable;
import org.apache.dubbo.demo.mine.Setable;
import org.apache.dubbo.demo.mine.spring.aop.SetImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {IntroductionDemo.IntroductionConfigure.class})
//@ContextConfiguration(locations = {"classpath:META-INF/spring-introduction.xml"})
public class IntroductionDemo {
//    @Autowired
//    private Lockable lockable;

    @Autowired
    @Qualifier("proxyFactoryBean")
    private Setable setable;
//
//    @Autowired
//    @Qualifier("lockMixin")
//    private Lockable lockMixin;

    @Test
    public void test1() {
        ((Lockable) setable).lock();
        setable.setState(1);
    }

    @Configurable
//    @ComponentScan("org.apache.demo.mine")
    static class IntroductionConfigure {
        @Bean
        public Setable setImpl() {
            return new SetImpl();
        }

        @Bean
        public LockMixin lockIntroduction() {
            return new LockMixin();
        }

        @Bean(name = "lockMixinAdvisor")
        public LockMixinAdvisor advisor() {
            return new LockMixinAdvisor(lockIntroduction());
        }

        @Bean(name = "proxyFactoryBean")
        public ProxyFactoryBean proxy(Setable setImpl) {
            ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
//            try {
//                proxyFactoryBean.setProxyInterfaces(new Class[] {Setable.class});
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            }
            proxyFactoryBean.setInterfaces(Setable.class);
            proxyFactoryBean.setTarget(setImpl);
            proxyFactoryBean.setInterceptorNames("lockMixinAdvisor");

            return proxyFactoryBean;
        }

    }
}
