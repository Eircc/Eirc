package com.ccc.eirc.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccc.eirc.commons.authentication.ShiroRealm;
import com.ccc.eirc.commons.domain.EircConstant;
import com.ccc.eirc.commons.domain.QueryRequest;
import com.ccc.eirc.commons.utils.SortUtil;
import com.ccc.eirc.system.domain.Role;
import com.ccc.eirc.system.domain.RoleMenu;
import com.ccc.eirc.system.mapper.RoleMapper;
import com.ccc.eirc.system.service.RoleMenuService;
import com.ccc.eirc.system.service.RoleService;
import com.ccc.eirc.system.service.UserRoleService;
import com.ccc.eirc.system.service.UserService;
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
 * <a>Title:RoleServiceImpl</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/24 16:18
 * @Version 1.0.0
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMenuService roleMenuService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private ShiroRealm shiroRealm;

    /**
     * 通过用户名查找用户校色
     *
     * @param username
     * @return
     */
    @Override
    @Transactional
    public List<Role> findUserRole(String username) {
        return this.baseMapper.findUserRole(username);
    }

    /**
     * 查找所有角色
     *
     * @param role 角色
     * @return
     */
    @Override
    @Transactional
    public List<Role> findRoles(Role role) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(role.getRoleName()))
            queryWrapper.lambda().like(Role::getRoleName, role.getRoleName());
        return this.baseMapper.selectList(queryWrapper);
    }

    /**
     * 查询所有角色 并分页
     *
     * @param role    对象
     * @param request request
     * @return
     */
    @Override
    @Transactional
    public IPage<Role> findRoles(Role role, QueryRequest request) {
        Page<Role> page = new Page<>(request.getPageNum(), request.getPageSize());
        SortUtil.handlePageSort(request, page, "createTime", EircConstant.ORDER_DESC, false);
        return this.baseMapper.findRolePage(page, role);
    }

    /**
     * 新增角色
     *
     * @param role 新增对象
     */
    @Override
    @Transactional
    public void create(Role role) {
        role.setCreateTime(new Date());
        this.baseMapper.insert(role);
        this.saveRoleMenus(role);
    }

    /**
     * 删除角色
     *
     * @param roleIds 删除对象
     */
    @Override
    @Transactional
    public void deleteRole(String roleIds) {
        List<String> list = Arrays.asList(roleIds.split(StringPool.COMMA));
//        删除角色
        this.baseMapper.delete(new QueryWrapper<Role>().lambda().in(Role::getRoleId, list));
//        删除角色对饮的菜单
        this.roleMenuService.deleteRoleMenusByRoleId(list);
//        删除角色对应的用户角色
        this.userRoleService.deleteUserRolesByRoleId(list);
    }

    /**
     * 跟新角色信息
     *
     * @param role 对象
     */
    @Override
    public void updateRole(Role role) {
        role.setModifyTime(new Date());
        this.updateById(role);
        List<String> roleIdList = new ArrayList<>();
        roleIdList.add(String.valueOf(role.getRoleId()));
//        将所有菜单全部删除
        this.roleMenuService.deleteRoleMenusByRoleId(roleIdList);
//        重新存入修改后角色对应的菜单
        saveRoleMenus(role);

//        清除缓存
        shiroRealm.clearCache();
    }

    /**
     * 新增角色的同时添加角色拥有的菜单
     *
     * @param role 对象
     */
    private void saveRoleMenus(Role role) {
        if (StringUtils.isNotBlank(role.getMenuIds())) {
            String[] menuIds = role.getMenuIds().split(StringPool.COMMA);
            List<RoleMenu> roleMenus = new ArrayList<>();
            Arrays.stream(menuIds).forEach(menuId -> {
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setMenuId(Long.valueOf(menuId));
                roleMenu.setRoleId(role.getRoleId());
                roleMenus.add(roleMenu);
            });
            roleMenuService.saveBatch(roleMenus);
        }
    }

}
