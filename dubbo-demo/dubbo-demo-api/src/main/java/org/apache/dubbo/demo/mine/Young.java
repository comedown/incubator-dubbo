package org.apache.dubbo.demo.mine;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Young {
    int value() default 0;
}
