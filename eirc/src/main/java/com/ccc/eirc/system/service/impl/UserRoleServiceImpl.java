package com.ccc.eirc.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccc.eirc.system.domain.UserRole;
import com.ccc.eirc.system.mapper.UserRoleMapper;
import com.ccc.eirc.system.service.UserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <a>Title:UserRoleServiceImpl</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/24 20:25
 * @Version 1.0.0
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS,readOnly = true,rollbackFor = Exception.class)
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper,UserRole> implements UserRoleService {


    /**
     * 删除用户的时候同时删除用户的角色
     * @param userIds 用户 id 集合
     */
    @Override
    @Transactional
    public void deleteUserRolesByUserId(List<String> userIds) {
        this.baseMapper.delete(new QueryWrapper<UserRole>().lambda().in(UserRole::getUserId,userIds));
    }

    /**
     * 删除角色对应的用户
     * @param userIds 用户 id 集合
     */
    @Override
    @Transactional
    public void deleteUserRolesByRoleId(List<String> userIds) {
        this.baseMapper.delete(new QueryWrapper<UserRole>().lambda().in(UserRole::getUserId,userIds));
    }
}
