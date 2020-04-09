package com.ccc.eirc.commons.annotation;


import com.ccc.eirc.commons.validator.MobileValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <a>Title:IsMobile</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/23 14:49
 * @Version 1.0.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MobileValidator.class)
public @interface IsMobile {
    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
