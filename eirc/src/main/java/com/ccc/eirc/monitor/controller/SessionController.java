package com.ccc.eirc.monitor.controller;

import com.ccc.eirc.commons.domain.EircResopnse;
import com.ccc.eirc.monitor.domain.ActiveUser;
import com.ccc.eirc.monitor.service.SessionService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <a>Title:SessionController</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/28 18:59
 * @Version 1.0.0
 */
@RestController
@RequestMapping("session")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    /**
     * 获取在线用户
     * @param username 对象
     * @return result
     */
    @GetMapping("list")
    @RequiresPermissions("online:view")
    public EircResopnse list(String username){
        List<ActiveUser> list = sessionService.list(username);
        Map<String, Object> data = new HashMap<>();
        data.put("rows", list);
        data.put("total", CollectionUtils.size(list));
        return new EircResopnse().success().data(data);
    }

    /**
     * 删除
     * @param id 对象
     * @return result
     */
    @GetMapping("delete/{id}")
    @RequiresPermissions("user:kickout")
    public EircResopnse forceLogout(@PathVariable String id){
        sessionService.forceLogout(id);
        return new EircResopnse().success();
    }
}

