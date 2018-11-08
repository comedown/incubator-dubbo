package org.apache.demo.mine.spring.aop;

import org.apache.dubbo.demo.mine.Lockable;
import org.apache.dubbo.demo.mine.Setable;
import org.springframework.stereotype.Component;

@Component
public class SetImpl implements Setable {
    @Override
    public void setState(int state) {
        System.out.println(state);
    }
}
