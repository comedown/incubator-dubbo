package org.apache.demo.mine.spring.aop;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.dubbo.demo.mine.Lockable;
import org.apache.dubbo.demo.mine.LockedException;
import org.springframework.aop.support.DelegatingIntroductionInterceptor;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class LockMixin extends DelegatingIntroductionInterceptor implements Lockable, InitializingBean, DisposableBean {
    private boolean locked;

    public void lock() {
        this.locked = true;
    }

    public void unlock() {
        this.locked = false;
    }

    public boolean locked() {
        return this.locked;
    }

    @Override
    public void setState(int state) {

    }

    public Object invoke(MethodInvocation invocation) throws Throwable {
        if (locked() && invocation.getMethod().getName().indexOf("set") == 0) {
            System.out.println("can not set");
            throw new LockedException();
        }
        return super.invoke(invocation);
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("disposable");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("init");
    }
}
