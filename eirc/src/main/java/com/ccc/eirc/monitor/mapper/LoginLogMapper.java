package com.ccc.eirc.monitor.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ccc.eirc.monitor.domain.LoginLog;
import com.ccc.eirc.system.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <a>Title:LoginMapper</a>
 * <a>Author：<a>
 * <a>Description：<a>
 * <p>
 * 系统登录日志
 *
 * @Author ccc
 * @Date 2020/3/23 17:16
 * @Version 1.0.0
 */
@Mapper
@Repository
public interface LoginLogMapper extends BaseMapper<LoginLog> {
    /**
     * 保存用户登录日志
     *
     * @param loginLog
     */
    void saveLoginLog(LoginLog loginLog);

    /**
     * 获取总访问次数
     *
     * @return
     */
    Long findTotalVisitCount();

    /**
     * 获取今次访问次数
     *
     * @return
     */
    Long findTodayVisitCount();

    /**
     * 获取当天ip
     *
     * @return
     */
    Long findTodayIp();

    /**
     * 最近七天访问情况
     *
     * @return
     * @param user
     */
    List<Map<String, Object>> findLastSevenDaysVisitCount(User user);
}
