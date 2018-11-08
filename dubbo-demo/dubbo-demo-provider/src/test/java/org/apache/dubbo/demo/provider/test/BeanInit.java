package org.apache.dubbo.demo.provider.test;

import org.apache.dubbo.demo.provider.ThreadConfigure;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ThreadConfigure.class})
public class BeanInit {
    @Test
    public void test1() {
        System.out.println("测试@PostConstruct");
    }

}
