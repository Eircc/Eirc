package com.ccc.eirc.commons.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * <a>Title:Helper</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/23 19:52
 * @Version 1.0.0
 */
@Component
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Helper {

    @AliasFor(annotation = Component.class)
    String value() default "";
}
