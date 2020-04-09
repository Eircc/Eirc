package com.ccc.eirc.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccc.eirc.system.domain.RoleMenu;
import com.ccc.eirc.system.mapper.RoleMenuMapper;
import com.ccc.eirc.system.service.RoleMenuService;
import com.ccc.eirc.system.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <a>Title:RoleMenuServiceImpl</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/26 19:27
 * @Version 1.0.0
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS,readOnly = true,rollbackFor = Exception.class)
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {
    /**
     * 通过菜单（按钮） id 删除
     * @param menuIds 菜单（按钮） id
     */
    @Override
    @Transactional
    public void deleteRoleMenusByMenuId(List<String> menuIds) {
        this.baseMapper.delete(new QueryWrapper<RoleMenu>().lambda().in(RoleMenu::getMenuId,menuIds));
    }

    /**
     * 删除角色对应的菜单（删除角色时）
     * @param menuIds 角色 id 集合
     */
    @Override
    public void deleteRoleMenusByRoleId(List<String> menuIds) {
        this.baseMapper.delete(new QueryWrapper<RoleMenu>().lambda().in(RoleMenu::getMenuId,menuIds));
    }
}
