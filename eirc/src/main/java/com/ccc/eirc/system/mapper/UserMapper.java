package com.ccc.eirc.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccc.eirc.system.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <a>Title:UserMapper</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/23 14:43
 * @Version 1.0.0
 */
@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {
    /**
     * 根据用户名查询用户信息 登陆认证
     *
     * @param username
     * @return
     */
    User findByName(String username);

    /**
     * 通过用户名查找用户详细信息
     * @param user
     * @return
     */
    List<User> findUserDetail(@Param("user") User user);

    /**
     * 查找用户详细信息
     * @param page 分页对象
     * @param user 用户对象，用于传递查询条件
     * @return Ipage
     */
    IPage<User> findUserDetailPage(Page page,@Param("user") User user);
}

