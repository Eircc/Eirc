package com.ccc.eirc.monitor.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ccc.eirc.commons.domain.QueryRequest;
import com.ccc.eirc.monitor.domain.LoginLog;
import com.ccc.eirc.system.domain.User;

import java.util.List;
import java.util.Map;

/**
 * <a>Title:LoginService</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/23 17:12
 * @Version 1.0.0
 */
public interface LoginLogService extends IService<LoginLog> {
    /**
     * 保存用户登录日志
     * @param loginLog 登录日志
     */
    void saveLoginLog(LoginLog loginLog);

    /**
     * 获取访问次数
     * @return result
     */
    Long findTotalVisitCount();

    /**
     * 获取当天访问次数
     * @return result
     */
    Long findTodayVisitCount();

    /**
     * 获取当天的ip
     * @return result
     */
    Long findTodayIp();

    /**
     * 获取最近七天访问情况
     * @param user 对象
     * @return result
     */
    List<Map<String, Object>> findLastSevenDaysVisitCount(User user);

    /**
     * 获取登录日志 （日志管理）
     * @param loginLog 对象
     * @param request 参数
     * @return result
     */
    IPage<LoginLog> findLoginLogs(LoginLog loginLog, QueryRequest request);

    /**
     * 删除登陆日志
     * @param ids 对象
     */
    void deleteLoginLogs(String[] ids);
}
