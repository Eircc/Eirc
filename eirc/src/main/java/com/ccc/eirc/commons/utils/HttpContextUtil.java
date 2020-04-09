package com.ccc.eirc.commons.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * <a>Title:HttpContextUtil</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/23 17:40
 * @Version 1.0.0
 */
public class HttpContextUtil {
    private HttpContextUtil(){

    }

    public static HttpServletRequest getHttpServletRequest(){
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }
}
