package com.ccc.eirc.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ccc.eirc.commons.domain.MenuTree;
import com.ccc.eirc.system.domain.Menu;

import java.util.List;

/**
 * <a>Title:MenuService</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/24 16:09
 * @Version 1.0.0
 */
public interface MenuService extends IService<Menu> {
    /**
     * 查找用户权限集合
     * @param username 用户名
     * @return 用户权限集合
     */
    List<Menu> findUserPermissions(String username);

    /**
     * 获取用户的菜单集合
     * @param username 用户名
     * @return 用户菜单集合
     */
    MenuTree<Menu> findUserMenus(String username);


    /**
     * 获取树形菜单
     * @param menu
     * @return
     */
    MenuTree<Menu> findMenus(Menu menu);

    /**
     * 批量删除/单个删除 菜单或按钮
     * @param menuIds
     */
    void deleteMenus(String menuIds);

    /**
     * 新增菜单
     * @param menu
     */
    void createMenu(Menu menu);

    /**
     * 修改菜单/按钮
     * @param menu 菜单
     */
    void updateMenu(Menu menu);

    /**
     * 导出excel
     * @param menu
     * @return
     */
    List<Menu> findMenuList(Menu menu);

}
