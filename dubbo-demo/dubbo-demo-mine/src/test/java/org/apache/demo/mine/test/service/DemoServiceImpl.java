package org.apache.demo.mine.test.service;

import org.apache.demo.mine.test.annotation.Info;
import org.apache.dubbo.demo.DemoService;

public class DemoServiceImpl implements DemoService {

    @Info("default")
    @Override
    public String sayHello(String name) {
        return "Hello," + name;
    }
}
