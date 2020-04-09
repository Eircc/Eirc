package com.ccc.eirc.monitor.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * <a>Title:TomcatInfo</a>
 * <a>Author：<a>
 * <a>Description：<a>
 * <p>
 * tomcat
 *
 * @Author ccc
 * @Date 2020/3/28 20:06
 * @Version 1.0.0
 */
@Data
public class TomcatInfo implements Serializable {

    private static final long serialVersionUID = 3252746599931952020L;
    /**
     * tomcat 已创建 session 数
     */
    private Double tomcatSessionsCreated;
    /**
     * tomcat 已过期 session 数
     */
    private Double tomcatSessionsExpired;
    /**
     * tomcat 当前活跃 session 数
     */
    private Double tomcatSessionsActiveCurrent;
    /**
     * tomcat 活跃 session 数峰值
     */
    private Double tomcatSessionsActiveMax;
    /**
     * 超过session 最大配置后，拒绝的 session 个数
     */
    private Double tomcatSessionsRejected;
    /**
     * 发送的字节数
     */
    private Double tomcatGlobalSent;
    /**
     * request 请求最长耗时
     */
    private Double tomcatGlobalRequestMax;
    /**
     * tomcat 全局异常数量
     */
    private Double tomcatGlobalError;
    /**
     * tomcat 当前线程数（包括守护线程）
     */
    private Double tomcatThreadsCurrent;
    /**
     * tomcat 配置的线程最大数
     */
    private Double tomcatThreadsConfigMax;
    /**
     * tomcat 当前繁忙线程数
     */
    private Double tomcatThreadsBusy;
}
