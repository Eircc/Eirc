package com.ccc.eirc.commons.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ccc.eirc.commons.domain.EircConstant;
import com.ccc.eirc.system.domain.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import java.util.HashMap;
import java.util.Map;

/**
 * <a>Title:BaseController</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/23 15:03
 * @Version 1.0.0
 */
public class BaseController {
    //    获取当前用户对象
    private Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    //    获取当前用户对象的密码
    protected User getCurrentUser() {
        return (User) getSubject().getPrincipal();
    }

    //    获取 session 会话信息
    protected Session getSession() {
        return getSubject().getSession();
    }

    protected Session getSession(Boolean flag) {
        return getSubject().getSession(flag);
    }

    //    获取登陆令牌
    protected void login(AuthenticationToken token) {
        getSubject().login(token);
    }

    protected Map<String, Object> getDataTable(IPage<?> pageInfo) {
        return getDataTable(pageInfo, EircConstant.DATA_MAP_INITIAL_CAPACITY);
    }

    /**
     * 分页
     *
     * @param pageInfo
     * @param dataMapInitialCapacity
     * @return
     */
    protected Map<String, Object> getDataTable(IPage<?> pageInfo, int dataMapInitialCapacity) {
        Map<String, Object> data = new HashMap<>(dataMapInitialCapacity);
        data.put("rows", pageInfo.getRecords());
        data.put("total", pageInfo.getTotal());
        return data;
    }

}
