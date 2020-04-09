package com.ccc.eirc.commons.properties;

import lombok.Data;

/**
 * <a>Title:ShiroPeoperties</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/22 20:59
 * @Version 1.0.0
 */
@Data
public class ShiroPeoperties {

    //    session失效时间
    private long sessionTimeout;

    //    cookies失效时间
    private int cookieTimeout;

    //    循序访问路径
    private String anonUrl;

    //    登陆路径
    private String loginUrl;

    //    成功跳转路径
    private String successUrl;

    //    登出路径
    private String logoutUrl;

    //    为认证路径
    private String unauthorizedUrl;
}
