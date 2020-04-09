package com.ccc.eirc.commons.validator;

import com.ccc.eirc.commons.annotation.IsCron;
import org.quartz.CronExpression;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * <a>Title:CronValidator</a>
 * <a>Author：<a>
 * <a>Description：<a>
 * <p>
 * 校验是否为合法的 Cron表达式
 *
 * @Author ccc
 * @Date 2020/3/28 17:38
 * @Version 1.0.0
 */
public class CronValidator implements ConstraintValidator<IsCron, String> {

    @Override
    public void initialize(IsCron isCron) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            return CronExpression.isValidExpression(value);
        } catch (Exception e) {
            return false;
        }
    }
}
