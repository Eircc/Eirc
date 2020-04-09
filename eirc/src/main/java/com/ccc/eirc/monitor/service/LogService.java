package com.ccc.eirc.monitor.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ccc.eirc.commons.domain.QueryRequest;
import com.ccc.eirc.monitor.domain.SystemLog;

/**
 * <a>Title:LogService</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/28 16:54
 * @Version 1.0.0
 */
public interface LogService extends IService<SystemLog> {
    /**
     * 获取系统日志
     *
     * @param systemLog 对象
     * @param request   参数
     * @return result
     */
    IPage<SystemLog> findLogs(SystemLog systemLog, QueryRequest request);

    /**
     * 删除系统日志
     *
     * @param logIds 对象
     */
    void deleteLogs(String[] logIds);
}
