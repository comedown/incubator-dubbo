package org.apache.dubbo.demo.mine.spring.aop;

import org.apache.dubbo.demo.mine.Lockable;
import org.apache.dubbo.demo.mine.Setable;
import org.apache.dubbo.demo.mine.Young;
import org.springframework.stereotype.Component;

@Component
public class SetImpl implements Setable {
    @Override
    public void setState(int state) {
        System.out.println(state);
    }

    @Young(1)
    @Override
    public boolean isSetable() {
        return false;
    }
}
