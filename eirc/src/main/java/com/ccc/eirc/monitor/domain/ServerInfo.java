package com.ccc.eirc.monitor.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * <a>Title:ServiceInfo</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * 服务（jdbc）
 *
 * @Author ccc
 * @Date 2020/3/28 20:20
 * @Version 1.0.0
 */
@Data
public class ServerInfo implements Serializable {

    private static final long serialVersionUID = 2164612739996536923L;
    /**
     * 应用已运行时长
     */
    private Double processUptime;
    /**
     * 应用 CPU占用率
     */
    private Double processCpuUsage;
    /**
     * 应用启动时间点
     */
    private String processStartTime;
    /**
     * 系统 CPU核心数
     */
    private Double systemCpuCount;
    /**
     * 系统 CPU 使用率
     */
    private Double systemCpuUsage;
    /**
     * 当前活跃 JDBC连接数
     */
    private Double jdbcConnectionsActive;
    /**
     * JDBC最小连接数
     */
    private Double jdbcConnectionsMin;
    /**
     * JDBC最大连接数
     */
    private Double jdbcConnectionsMax;
}
