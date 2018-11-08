package org.apache.demo.mine.test.aop;

import org.apache.demo.mine.spring.aop.LockMixin;
import org.apache.demo.mine.spring.aop.LockMixinAdvisor;
import org.apache.dubbo.demo.mine.Lockable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {IntroductionDemo.IntroductionConfigure.class})
public class IntroductionDemo {
    @Autowired
    private Lockable lockable;
//
//    @Autowired
//    @Qualifier("lockMixin")
//    private Lockable lockMixin;

    @Test
    public void test1() {
        lockable.lock();
        lockable.setState(2);
    }

    @Configurable
    @ComponentScan("org.apache.demo.mine")
    static class IntroductionConfigure {

        @Bean
        public LockMixinAdvisor advisor() {
            return new LockMixinAdvisor();
        }

    }
}
