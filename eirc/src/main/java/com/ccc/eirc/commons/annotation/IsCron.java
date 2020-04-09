package com.ccc.eirc.commons.annotation;

import com.ccc.eirc.commons.validator.CronValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <a>Title:IsCron</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/28 17:37
 * @Version 1.0.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CronValidator.class)
public @interface IsCron {

    String message();

    Class<?>[] group() default {};

    Class<? extends Payload>[] payload() default {};
}
