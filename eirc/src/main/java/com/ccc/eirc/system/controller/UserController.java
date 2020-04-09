package com.ccc.eirc.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.ccc.eirc.commons.annotation.ControllerEndpoint;
import com.ccc.eirc.commons.controller.BaseController;
import com.ccc.eirc.commons.domain.EircResopnse;
import com.ccc.eirc.commons.domain.QueryRequest;
import com.ccc.eirc.commons.exception.EircException;
import com.ccc.eirc.commons.utils.MD5Util;
import com.ccc.eirc.system.domain.User;
import com.ccc.eirc.system.service.UserService;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * <a>Title:UserController</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/27 13:02
 * @Version 1.0.0
 */
@Slf4j
@Validated
@RestController
@RequestMapping("user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * 获取用户详情
     *
     * @param username 用户名
     * @return
     */
    @GetMapping("{username}")
    public User getUser(@NotBlank(message = "{required}") String username) {
        return this.userService.findUserDetailList(username);
    }

    /**
     * 检查用户是否存在
     *
     * @param username 用户名
     * @param userId   用户id
     * @return
     */
    @GetMapping("check/{username}")
    public boolean checkUserName(@NotBlank(message = "{required}") @PathVariable String username, String userId) {
        return this.userService.findByName(username) == null || StringUtils.isNotBlank(userId);
    }

    /**
     * 用户信息查询并分页
     *
     * @param user    对象
     * @param request 参数
     * @return
     */
    @GetMapping("list")
    @RequiresPermissions("user:view")
    public EircResopnse userList(User user, QueryRequest request) {
        Map<String, Object> dataTable = getDataTable(this.userService.findUserDetailList(user, request));
        return new EircResopnse().success().data(dataTable);
    }

    /**
     * 新增用户
     *
     * @param user 新增对象
     * @return
     */
    @PostMapping
    @RequiresPermissions("user:add")
    @ControllerEndpoint(operation = "新增用户", exceptionMessage = "新增用户失败")
    public EircResopnse addUser(@Valid User user) {
        this.userService.createUser(user);
        return new EircResopnse().success();
    }

    /**
     * 删除用户  批量/单个
     *
     * @param userIds 删除对象的id
     * @return
     */
    @GetMapping("delete/{userIds}")
    @RequiresPermissions("user:delete")
    @ControllerEndpoint(operation = "删除用户", exceptionMessage = "删除用户失败")
    public EircResopnse deleteUser(@NotBlank(message = "{required}") @PathVariable String userIds) {
        String[] ids = userIds.split(StringPool.COMMA);
        this.userService.deleteUsers(ids);
        return new EircResopnse().success();
    }

    /**
     * 修改用户
     *
     * @param user 修改对象
     * @return
     */
    @PostMapping("update")
    @RequiresPermissions("user:update")
    @ControllerEndpoint(operation = "修改用户", exceptionMessage = "修改用户失败")
    public EircResopnse updateUser(@Valid User user) {
        if (user.getUserId() == null)
            throw new EircException("用户Id为空");
        this.userService.updateUser(user);
        return new EircResopnse().success();
    }

    /**
     * 重置用户密码
     *
     * @param usernames 重置密码对象
     * @return
     */
    @PostMapping("password/reset/{usernames}")
    @RequiresPermissions("user:password:reset")
    @ControllerEndpoint(exceptionMessage = "重置密码失败")
    public EircResopnse resetPassword(@NotBlank(message = "{required}") @PathVariable String usernames) {
        String[] usernameArr = usernames.split(StringPool.COMMA);
        this.userService.resetPassword(usernameArr);
        return new EircResopnse().success();
    }

    /**
     * 修改用户密码
     *
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return
     */
    @PostMapping("password/update")
    @ControllerEndpoint(exceptionMessage = "修改密码失败")
    public EircResopnse updatePassword(
            @NotBlank(message = "{required}") String oldPassword,
            @NotBlank(message = "{required}") String newPassword) {
//        获取当前登录用户
        User user = getCurrentUser();
        if (!StringUtils.equals(user.getPassword(), MD5Util.encrypt(user.getUsername(), oldPassword))) {
            throw new EircException("原密码不正确!");
        }
        userService.updatePassword(user.getUsername(), newPassword);
        return new EircResopnse().success();
    }

    /**
     * 修改头像
     *
     * @param image 头像
     * @return
     */
    @GetMapping("avatar/{image}")
    @ControllerEndpoint(exceptionMessage = "修改头像失败")
    public EircResopnse updateAvatar(@NotBlank(message = "{required}") @PathVariable String image) {
        User user = getCurrentUser();
        this.userService.updateAvatar(user.getUsername(), image);
        return new EircResopnse().success();
    }

    /**
     * 修改系统配置
     *
     * @param theme 系统配置
     * @param isTab 是否开启 Tab
     * @return
     */
    @PostMapping("theme/update")
    @ControllerEndpoint(exceptionMessage = "修改系统配置失败")
    public EircResopnse updateTheme(String theme, String isTab) {
        User user = getCurrentUser();
        this.userService.updateTheme(user.getUsername(), theme, isTab);
        return new EircResopnse().success();
    }

    /**
     * 修改个人信息
     *
     * @param user 修改对象
     * @return
     */
    @PostMapping("profile/update")
    @ControllerEndpoint(exceptionMessage = "修改个人信息失败")
    public EircResopnse pdateProfile(User user) {
        User currentUser = getCurrentUser();
        user.setUserId(currentUser.getUserId());
        this.userService.updateProfile(user);
        return new EircResopnse().success();
    }

    /**
     * 导出 Excel
     *
     * @param queryRequest
     * @param user
     * @param response
     */
    @GetMapping("excel")
    @RequiresPermissions("user:export")
    @ControllerEndpoint(exceptionMessage = "导出Excel失败")
    public void export(QueryRequest queryRequest, User user, HttpServletResponse response) {
        List<User> users = this.userService.findUserDetailList(user, queryRequest).getRecords();
        ExcelKit.$Export(User.class, response).downXlsx(users, false);
    }


}
