package com.ccc.eirc.monitor.domain;

import lombok.Data;

import java.io.Serializable;
import java.net.URI;

/**
 * <a>Title:EircHttpTrace</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/28 17:49
 * @Version 1.0.0
 */
@Data
public class EircHttpTrace implements Serializable {

    private static final long serialVersionUID = -9094838420530833447L;

    /**
     * 请求时间
     */
    private String requestTime;
    /**
     * 请求方法
     */
    private String method;
    /**
     * 请求 url
     */
    private URI url;
    /**
     * 响应状态
     */
    private int status;
    /**
     * 耗时
     */
    private Long timeTaken;
}
