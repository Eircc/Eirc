package com.ccc.eirc.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ccc.eirc.commons.domain.QueryRequest;
import com.ccc.eirc.system.domain.User;

import java.util.List;
import java.util.Map;

/**
 * <a>Title:UserService</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/23 14:42
 * @Version 1.0.0
 */
public interface UserService extends IService<User> {
    /**
     * 根据用户名查询用户信息 登陆认证
     *
     * @param username 用户名
     * @return
     */
    User findByName(String username);


    /**
     * 更新的登陆时间
     *
     * @param username 用户名
     */
    void updateLoginTime(String username);

    /**
     * 用户注册
     *
     * @param username 用户名
     * @param password 密码
     */
    void regist(String username, String password);

    /**
     * 通过用户名查找用户详细信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    User findUserDetailList(String username);

    /**
     * 查询用户信息并分页
     *
     * @param user    用户对象
     * @param request 分页参数
     * @return
     */
    IPage<User> findUserDetailList(User user, QueryRequest request);

    /**
     * 新增用户
     *
     * @param user 新增对象
     */
    void createUser(User user);

    /**
     * 删除用户
     *
     * @param userIds 删除对象的id id数组
     */
    void deleteUsers(String[] userIds);

    /**
     * 修改用户信息
     *
     * @param user 修改对象
     */
    void updateUser(User user);

    /**
     * 重置用户密码
     *
     * @param usernames 重置对象
     */
    void resetPassword(String[] usernames);

    /**
     * 修改用户密码
     *
     * @param username 用户名
     * @param password 新密码
     */
    void updatePassword(String username, String password);

    /**
     * 修改头像
     *
     * @param username 用户吗
     * @param avatar   头像图片
     */
    void updateAvatar(String username, String avatar);

    /**
     * 修改系统配置
     *
     * @param username 用户
     * @param theme    主题
     * @param isTab    是否开启Tab
     */
    void updateTheme(String username, String theme, String isTab);

    /**
     * 修改个人信息
     * @param user 修改对象
     */
    void updateProfile(User user);
}
