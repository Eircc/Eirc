package com.ccc.eirc.commons.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <a>Title:ControllerEndpoint</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @MenuController
 * @Author ccc
 * @Date 2020/3/25 16:32
 * @Version 1.0.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ControllerEndpoint {

    String operation() default "";

    String exceptionMessage() default "EIRC系统内部异常!";
}
