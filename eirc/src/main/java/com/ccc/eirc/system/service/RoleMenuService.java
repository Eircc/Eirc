package com.ccc.eirc.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ccc.eirc.system.domain.RoleMenu;

import java.util.List;

/**
 * <a>Title:RoleMenuService</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/26 19:26
 * @Version 1.0.0
 */
public interface RoleMenuService extends IService<RoleMenu> {

    /**
     * 通过菜单（按钮） id 删除
     * @param menuIds 菜单（按钮） id
     */
    void deleteRoleMenusByMenuId(List<String> menuIds);

    /**
     * 删除角色对应的菜单（删除角色时）
     * @param menuIds
     */
    void deleteRoleMenusByRoleId(List<String> menuIds);
}
