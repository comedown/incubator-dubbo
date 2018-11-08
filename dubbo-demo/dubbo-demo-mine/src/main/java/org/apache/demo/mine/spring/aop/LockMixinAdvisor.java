package org.apache.demo.mine.spring.aop;

import org.apache.dubbo.demo.mine.Lockable;
import org.apache.dubbo.demo.mine.Setable;
import org.springframework.aop.support.DefaultIntroductionAdvisor;

public class LockMixinAdvisor extends DefaultIntroductionAdvisor {

    public LockMixinAdvisor() {
        super(new LockMixin(), Setable.class);
    }
}
