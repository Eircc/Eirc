package com.ccc.eirc.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccc.eirc.system.domain.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <a>Title:RoleMapper</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/24 16:21
 * @Version 1.0.0
 */
@Mapper
@Repository
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 通过用户名查找用户角色
     * @param username
     * @return
     */
    List<Role> findUserRole(String username);

    /**
     * 查询所有角色
     * @param page 分页
     * @param role 对象
     * @return
     */
    IPage<Role> findRolePage(Page<Role> page, Role role);
}
