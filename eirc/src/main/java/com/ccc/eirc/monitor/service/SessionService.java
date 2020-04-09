package com.ccc.eirc.monitor.service;

import com.ccc.eirc.monitor.domain.ActiveUser;

import java.util.List;

/**
 * <a>Title:SessionService</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/28 19:00
 * @Version 1.0.0
 */
public interface SessionService {
    /**
     * 获取在线信息
     * @param username 对象
     * @return result
     */
    List<ActiveUser> list(String username);

    /**
     * 删除
     * @param sessionId 对象
     */
    void forceLogout(String sessionId);
}
