package com.ccc.eirc.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccc.eirc.commons.authentication.ShiroRealm;
import com.ccc.eirc.commons.domain.MenuTree;
import com.ccc.eirc.commons.utils.TreeUtil;
import com.ccc.eirc.system.domain.Menu;
import com.ccc.eirc.system.mapper.MenuMapper;
import com.ccc.eirc.system.service.MenuService;
import com.ccc.eirc.system.service.RoleMenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private RoleMenuService roleMenuService;

    @Autowired
    private ShiroRealm shiroRealm;

    /**
     * 查找用户权限集合
     *
     * @param username
     * @return
     */
    @Override
    public List<Menu> findUserPermissions(String username) {
        return this.baseMapper.findUserPermissions(username);
    }

    /**
     * 获取用户的菜单
     *
     * @param username
     * @return
     */
    @Override
    public MenuTree<Menu> findUserMenus(String username) {
        List<Menu> menus = this.baseMapper.findUserMenus(username);
        List<MenuTree<Menu>> trees = this.convertMenus(menus);
        return TreeUtil.buildMenuTree(trees);
    }

    /**
     * 获取树形菜单
     *
     * @param menu
     * @return
     */
    @Override
    @Transactional
    public MenuTree<Menu> findMenus(Menu menu) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(menu.getMenuName())) {
            queryWrapper.lambda().eq(Menu::getMenuName, menu.getMenuName());
        }
        queryWrapper.lambda().orderByAsc(Menu::getOrderNum);
        List<Menu> menus = this.baseMapper.selectList(queryWrapper);
        List<MenuTree<Menu>> trees = this.convertMenus(menus);

        return TreeUtil.buildMenuTree(trees);
    }

    /**
     * 新增菜单
     *
     * @param menu
     */
    @Override
    @Transactional
    public void createMenu(Menu menu) {
        menu.setCreateTime(new Date());
        this.setMenu(menu);
        this.baseMapper.insert(menu);
    }

    /**
     * 修改菜单/按钮
     *
     * @param menu 菜单
     */
    @Override
    public void updateMenu(Menu menu) {
        menu.setCreateTime(new Date());
        this.setMenu(menu);
        this.baseMapper.updateById(menu);

    }

    /**
     * 导出excel
     *
     * @param menu
     * @return
     */
    @Override
    public List<Menu> findMenuList(Menu menu) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(menu.getMenuName())) {
            queryWrapper.lambda().like(Menu::getMenuName, menu.getMenuName());
        }
        queryWrapper.lambda().orderByAsc(Menu::getMenuId).orderByAsc(Menu::getOrderNum);
        return this.baseMapper.selectList(queryWrapper);
    }

    /**
     * 批量删除/单个删除 菜单或按钮
     *
     * @param menuIds
     */
    @Override
    @Transactional
    public void deleteMenus(String menuIds) {
//        以逗号分割字符串数组
        String[] menuIdsArray = menuIds.split(StringPool.COMMA);
        this.delete(Arrays.asList(menuIdsArray));

    }

    /**
     * 树转换成菜单
     *
     * @param menus
     * @return
     */
    private List<MenuTree<Menu>> convertMenus(List<Menu> menus) {
        List<MenuTree<Menu>> trees = new ArrayList<>();
        menus.forEach(menu -> {
            MenuTree<Menu> tree = new MenuTree<>();
            tree.setId(String.valueOf(menu.getMenuId()));
            tree.setParentId(String.valueOf(menu.getParentId()));
            tree.setTitle(menu.getMenuName());
            tree.setIcon(menu.getIcon());
            tree.setHref(menu.getUrl());
            tree.setData(menu);
            trees.add(tree);
        });
        return trees;
    }

    private void delete(List<String> menuIds) {
        List<String> list = new ArrayList<>(menuIds);
        removeByIds(menuIds);

        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Menu::getParentId, menuIds);
        List<Menu> menus = baseMapper.selectList(queryWrapper);
        if (CollectionUtils.isNotEmpty(menus)) {
            List<String> menuIdList = new ArrayList<>();
            menus.forEach(m ->
                    menuIdList.add(String.valueOf(m.getMenuId()))
            );
            list.addAll(menuIdList);
            this.roleMenuService.deleteRoleMenusByMenuId(list);
            this.delete(menuIdList);
        } else {
            this.roleMenuService.deleteRoleMenusByMenuId(list);
        }
    }


    private void setMenu(Menu menu) {
//        如果 parentId 为空 就是指为父节点 类似于新创一个菜单类别
        if (menu.getParentId() == null) {
            menu.setParentId(Menu.TOP_NODE);
//            如果按钮与菜单类型相同
            if (Menu.TYPE_BUTTON.equals(menu.getType())) {
                menu.setUrl(null);
                menu.setIcon(null);
            }
        }
    }
}
