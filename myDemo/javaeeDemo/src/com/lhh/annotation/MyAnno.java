package com.lhh.annotation;

import java.lang.annotation.*;

/**
 * @author lihonghao
 * @data 2021/1/11 19:46
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Inherited
public @interface MyAnno {
    String value() default "有注解";
}
