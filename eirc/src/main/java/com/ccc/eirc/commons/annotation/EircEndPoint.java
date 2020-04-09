package com.ccc.eirc.commons.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * <a>Title:EircEndPoint</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/28 17:34
 * @Version 1.0.0
 */
@Component
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EircEndPoint {

    @AliasFor(annotation = Component.class)
    String value() default "";
}
