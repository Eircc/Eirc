package com.ccc.eirc.commons.authentication;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

import java.util.concurrent.atomic.AtomicInteger;


/**
 * <a>Title:ShiroSessionListener</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * 监听 session 会话
 *
 * @Author ccc
 * @Date 2020/3/22 21:51
 * @Version 1.0.0
 */
public class ShiroSessionListener implements SessionListener {

    private final AtomicInteger sessionCount = new AtomicInteger(0);

    @Override
    public void onStart(Session session) {
        sessionCount.incrementAndGet();
    }

    @Override
    public void onStop(Session session) {
        sessionCount.decrementAndGet();
    }

    @Override
    public void onExpiration(Session session) {
        sessionCount.decrementAndGet();
    }
}
