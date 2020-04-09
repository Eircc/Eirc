package com.ccc.eirc.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ccc.eirc.system.domain.UserRole;

import java.util.List;

/**
 * <a>Title:UserRoleService</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/24 20:24
 * @Version 1.0.0
 */
public interface UserRoleService extends IService<UserRole> {
    /**
     * 删除用户的时候同时删除用户的角色
     * @param userIds 用户id集合
     */
    void deleteUserRolesByUserId(List<String> userIds);

    /**
     * 删除角色对应的用户（删除角色时）
     * @param userIds
     */
    void deleteUserRolesByRoleId(List<String> userIds);
}
