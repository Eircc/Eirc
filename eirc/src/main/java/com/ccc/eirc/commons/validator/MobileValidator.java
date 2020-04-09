package com.ccc.eirc.commons.validator;

import com.ccc.eirc.commons.annotation.IsMobile;
import com.ccc.eirc.commons.domain.Regexp;
import com.ccc.eirc.commons.utils.EircUtil;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * <a>Title:MobileValidator</a>
 * <a>Author：<a>
 * <a>Description：<a>
 * <p>
 * 校验是否为合法的手机号码
 *
 * @Author ccc
 * @Date 2020/3/23 14:51
 * @Version 1.0.0
 */
public class MobileValidator implements ConstraintValidator<IsMobile, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        try {
            if (StringUtils.isBlank(s)) {
                return true;
            } else {
                String regex = Regexp.MOBILE_REG;
                return EircUtil.match(regex, s);
            }
        } catch (Exception e) {
            return false;
        }
    }
}
