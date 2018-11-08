package org.apache.dubbo.demo.provider;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.ImportResource;

import javax.annotation.PostConstruct;

@Configurable
public class ThreadConfigure {
    private String initString;

    @PostConstruct
    public void init() {
        initString = System.getProperty("user.dir", "default");
        System.out.println("init:" + initString);
    }

    public ThreadConfigure() {
        System.out.println("Construct");
    }
}
