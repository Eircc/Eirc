package com.ccc.eirc.monitor.service.impl;

import com.ccc.eirc.commons.utils.AddressUtil;
import com.ccc.eirc.commons.utils.DateUtil;
import com.ccc.eirc.monitor.domain.ActiveUser;
import com.ccc.eirc.monitor.service.SessionService;
import com.ccc.eirc.system.domain.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <a>Title:SessionServiceImpl</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/28 19:00
 * @Version 1.0.0
 */
@Service
public class SessionServiceImpl implements SessionService {
    @Autowired
    private SessionDAO sessionDAO;

    /**
     * 获取在线用户
     *
     * @param username 对象
     * @return result
     */
    @Override
    public List<ActiveUser> list(String username) {
        String currentSessionId = (String) SecurityUtils.getSubject().getSession().getId();

        List<ActiveUser> list = new ArrayList<>();
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        for (Session session : sessions) {
            ActiveUser activeUser = new ActiveUser();
            User user;
            SimplePrincipalCollection principalCollection;
            if (session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY) == null) {
                continue;
            } else {
                principalCollection = (SimplePrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
                user = (User) principalCollection.getPrimaryPrincipal();
                activeUser.setUsername(user.getUsername());
                activeUser.setUserId(user.getUserId().toString());
            }
            activeUser.setId((String) session.getId());
            activeUser.setHost(session.getHost());
            activeUser.setStartTimestamp(DateUtil.getDateFormat(session.getStartTimestamp(), DateUtil.FULL_TIME_SPLIT_PATTERN));
            activeUser.setLastAccessTime(DateUtil.getDateFormat(session.getLastAccessTime(), DateUtil.FULL_TIME_SPLIT_PATTERN));
            long timeout = session.getTimeout();
            activeUser.setStatus(timeout == 0L ? "0" : "1");
            String address = AddressUtil.getCityInfo(activeUser.getHost());
            activeUser.setLocation(address);
            activeUser.setTimeout(timeout);
            if (StringUtils.equals(currentSessionId, activeUser.getId())) {
                activeUser.setCurrent(true);
            }
            if (StringUtils.isBlank(username) || StringUtils.equalsIgnoreCase(activeUser.getUsername(), username)) {
                list.add(activeUser);
            }
        }
        return list;
    }

    /**
     * 删除
     *
     * @param sessionId 对象
     */
    @Override
    public void forceLogout(String sessionId) {
        Session session = sessionDAO.readSession(sessionId);
        session.setTimeout(0);
        session.stop();
        sessionDAO.delete(session);
    }
}
