package com.ccc.eirc.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccc.eirc.commons.authentication.ShiroRealm;
import com.ccc.eirc.commons.domain.EircConstant;
import com.ccc.eirc.commons.domain.QueryRequest;
import com.ccc.eirc.commons.exception.EircException;
import com.ccc.eirc.commons.utils.EircUtil;
import com.ccc.eirc.commons.utils.MD5Util;
import com.ccc.eirc.commons.utils.SortUtil;
import com.ccc.eirc.system.domain.User;
import com.ccc.eirc.system.domain.UserRole;
import com.ccc.eirc.system.mapper.UserMapper;
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
 * <a>Title:UserServiceImpl</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/23 14:43
 * @Version 1.0.0
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private ShiroRealm shiroRealm;

    /**
     * 根据用户名查询用户信息 登陆认证
     *
     * @param username
     * @return
     */
    @Override
    @Transactional
    public User findByName(String username) {
        return this.baseMapper.findByName(username);
    }

    /**
     * 更新登陆时间
     *
     * @param username
     */
    @Override
    @Transactional
    public void updateLoginTime(String username) {
        User user = new User();
        user.setLastLoginTime(new Date());
        this.baseMapper.update(user, new LambdaQueryWrapper<User>().eq(User::getUsername, username));
    }

    /**
     * 用户注册
     *
     * @param username
     * @param password
     */
    @Override
    @Transactional
    public void regist(String username, String password) {
//        保存用户注册信息
        User user = new User();
        user.setUsername(username);
        user.setPassword(MD5Util.encrypt(username, password));
        user.setCreateTime(new Date());
        user.setStatus(User.STATUS_VALID);
        user.setSex(User.SEX_UNKNOW);
        user.setAvatar(User.DEFAULT_AVATAR);
        user.setTheme(User.THEME_BLACK);
        user.setIsTab(User.TAB_OPEN);
        user.setDescription("新注册用户！");
        this.save(user);

//        同时设置用户角色
        UserRole userRole = new UserRole();
        userRole.setRoleId(user.getUserId());
        userRole.setUserId(EircConstant.REGISTER_ROLE_ID);
        this.userRoleService.save(userRole);

    }

    /**
     * 通过用户名查找用户详细信息
     * @param username 用户名
     * @return
     */
    @Override
    @Transactional
    public User findUserDetailList(String username) {
        User param = new User();
        param.setUsername(username);
        List<User> users = this.baseMapper.findUserDetail(param);
        return CollectionUtils.isNotEmpty(users)?users.get(0):null;
    }

    /**
     * 查询用户信息并分页
     * @param user 用户
     * @param request
     * @return
     */
    @Override
    @Transactional
    public IPage<User> findUserDetailList(User user, QueryRequest request) {
        Page<User> page = new Page<>(request.getPageNum(), request.getPageSize());
        SortUtil.handlePageSort(request,page,"userId", EircConstant.ORDER_ASC,false);
        return this.baseMapper.findUserDetailPage(page,user);
    }

    /**
     * 添加
     * @param user 新增对象
     */
    @Override
    @Transactional
    public void createUser(User user) {
        user.setCreateTime(new Date());
        user.setStatus(User.STATUS_VALID);
        user.setAvatar(User.DEFAULT_AVATAR);
        user.setTheme(User.THEME_BLACK);
        user.setIsTab(User.TAB_OPEN);
        user.setPassword(MD5Util.encrypt((user.getUsername()),User.DEFAULT_PASSWORD));
        save(user);
//        保存用户角色
        String[] roles = user.getRoleId().split(StringPool.COMMA);
        setUserRoles(user,roles);
    }

    /**
     * 删除用户对象
     * @param userIds 删除对象的id id数组
     */
    @Override
    @Transactional
    public void deleteUsers(String[] userIds) {
        List<String> list = Arrays.asList(userIds);
//        删除用户
        this.removeByIds(list);
//        删除关联角色
        this.userRoleService.deleteUserRolesByUserId(list);
    }

    /**
     * 修改用户信息
     * @param user 修改对象
     */
    @Override
    @Transactional
    public void updateUser(User user) {
        String username = user.getUsername();
//        更新用户 不能修改别人的密码还有用户名
        user.setPassword(null);
        user.setUsername(null);
        user.setModifyTime(new Date());
        updateById(user);
//        更新关联角色
        this.userRoleService.remove(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId,user.getUserId()));
        String[] roles = user.getRoleId().split(StringPool.COMMA);
        setUserRoles(user,roles);

        User currentUser = EircUtil.getCurrentUser();
//        清楚缓存
        if (StringUtils.equalsIgnoreCase(currentUser.getUsername(),username)){
            shiroRealm.clearCache();
        }

    }

    /**
     * 重置用户密码
     * @param usernames 重置对象
     */
    @Override
    @Transactional
    public void resetPassword(String[] usernames) {
        Arrays.stream(usernames).forEach(username->{
            User user = new User();
            user.setPassword(MD5Util.encrypt(username,User.DEFAULT_PASSWORD));
            this.baseMapper.update(user,new LambdaQueryWrapper<User>().eq(User::getUsername,username));
        });
    }

    /**
     * 修改密码
     * @param username 用户名
     * @param password 新密码
     */
    @Override
    @Transactional
    public void updatePassword(String username, String password) {
        User user = new User();
        user.setPassword(MD5Util.encrypt(username, password));
        user.setModifyTime(new Date());
        this.baseMapper.update(user,new LambdaQueryWrapper<User>().eq(User::getUsername,username));
    }

    /**
     * 修改头像
     * @param username 用户吗
     * @param avatar 头像图片
     */
    @Override
    @Transactional
    public void updateAvatar(String username, String avatar) {
        User user = new User();
        user.setAvatar(avatar);
        user.setModifyTime(new Date());
        this.baseMapper.update(user,new LambdaQueryWrapper<User>().eq(User::getUsername,username));
    }

    /**
     * 修改系统配置
     * @param username 用户
     * @param theme    主题
     * @param isTab    是否开启Tab
     */
    @Override
    @Transactional
    public void updateTheme(String username, String theme, String isTab) {
        User user = new User();
        user.setTheme(theme);
        user.setIsTab(isTab);
        user.setModifyTime(new Date());
        this.baseMapper.update(user,new LambdaQueryWrapper<User>().eq(User::getUsername,username));
    }

    /**
     * 修改用户信息
     * @param user 修改对象
     */
    @Override
    @Transactional
    public void updateProfile(User user) {
        user.setUsername(null);
        user.setRoleId(null);
        user.setPassword(null);
        if (isCurrentUser(user.getId())){
            updateById(user);
        }else {
            throw new EircException("您无权修改别人的账号信息!");
        }
    }

    private boolean isCurrentUser(Long id) {
        User currentUser = EircUtil.getCurrentUser();
        return currentUser.getUserId().equals(id);
    }


    /**
     * 保存用户角色
     * @param user 用户对象
     * @param roles 对应角色
     */
    private void setUserRoles(User user, String[] roles) {
        List<UserRole> userRoles = new ArrayList<>();
        Arrays.stream(roles).forEach(roleId->{
            UserRole userRole = new UserRole();
            userRole.setUserId(user.getUserId());
            userRole.setRoleId(Long.valueOf(roleId));
            userRoles.add(userRole);
        });
        userRoleService.saveBatch(userRoles);
    }
}
