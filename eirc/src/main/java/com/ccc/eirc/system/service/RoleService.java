package com.ccc.eirc.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ccc.eirc.commons.domain.QueryRequest;
import com.ccc.eirc.system.domain.Role;

import java.util.List;

/**
 * <a>Title:RoleService</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/24 16:18
 * @Version 1.0.0
 */
public interface RoleService extends IService<Role> {
    /**
     * 通过用户名查找用户校色
     * @param username
     * @return
     */
    List<Role> findUserRole(String username);

    /**
     * 查找所有角色
     * @param role 角色
     * @return
     */
    List<Role> findRoles(Role role);

    /**
     * 查找所有角色 并分页
     * @param role 对象
     * @param request request
     * @return
     */
    IPage<Role> findRoles(Role role, QueryRequest request);

    /**
     * 新增角色
     * @param role 新增对象
     */
    void create(Role role);

    /**
     * 删除角色 ids
     * @param roleIds 删除对象
     */
    void deleteRole(String roleIds);

    /**
     * 修改角色
     * @param role 对象
     */
    void updateRole(Role role);
}
