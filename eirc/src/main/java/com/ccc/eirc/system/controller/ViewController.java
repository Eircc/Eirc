package com.ccc.eirc.system.controller;

import com.ccc.eirc.commons.authentication.ShiroHelper;
import com.ccc.eirc.commons.controller.BaseController;
import com.ccc.eirc.commons.domain.EircConstant;
import com.ccc.eirc.commons.service.ValidateCodeService;
import com.ccc.eirc.commons.utils.DateUtil;
import com.ccc.eirc.commons.utils.EircUtil;
import com.ccc.eirc.system.domain.User;
import com.ccc.eirc.system.service.UserService;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.ExpiredSessionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;

/**
 * <a>Title:ViewController</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/23 16:04
 * @Version 1.0.0
 */
@Controller("systemView")
public class ViewController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private ShiroHelper shiroHelper;

    @Autowired
    private ValidateCodeService validateCodeService;

    /**
     * 登陆之前判断是否为 AJAX 请求
     *
     * @param request
     * @return
     */
    @GetMapping("login")
    @ResponseBody
    public ModelAndView login(HttpServletRequest request) {
        if (EircUtil.isAjaxRequest(request)) {
            throw new ExpiredSessionException();
        } else {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName(EircUtil.view("login"));
            return modelAndView;
        }
    }

    /**
     * 未认证的 跳转错误页面
     *
     * @return
     */
    @GetMapping("unauthorized")
    public String unauthorized() {
        return EircUtil.view("error/403");
    }

    /**
     * 跳转登陆页面
     *
     * @return
     */
    @GetMapping("/")
    public String redirectIndex() {
        return "redirect:/index";
    }

    /**
     * 将登录者信息带到index页面
     *
     * @param model
     * @return
     */
    @GetMapping("index")
    public String index(Model model) {

        AuthorizationInfo authorizationinfo = shiroHelper.getCurrentUserAuthorizationInfo();
        User user = super.getCurrentUser();
        User currentUserDetail = userService.findByName(user.getUsername());
        currentUserDetail.setPassword("It's a secret");
        model.addAttribute("user", currentUserDetail);
        model.addAttribute("permissions", authorizationinfo.getStringPermissions());
        model.addAttribute("roles", authorizationinfo.getRoles());
        return "index";
    }

    /**
     * index
     *
     * @return
     */
    @RequestMapping(EircConstant.VIEW_PREFIX + "index")
    public String pageIndex() {
        return EircUtil.view("index");
    }

    /**
     * 登出
     *
     * @return
     */
    @GetMapping(EircConstant.VIEW_PREFIX + "layout")
    public String layuot() {
        return EircUtil.view("layout");
    }

    /**
     * 修改密码
     *
     * @return
     */
    @GetMapping(EircConstant.VIEW_PREFIX + "password/update")
    public String passwordUpdate() {
        return EircUtil.view("system/user/passwordUpdate");
    }

    /**
     * 个人信息
     *
     * @return
     */
    @GetMapping(EircConstant.VIEW_PREFIX + "user/profile")
    public String userProfile() {
        return EircUtil.view("system/user/userProfile");
    }

    /**
     * @return
     */
    @GetMapping(EircConstant.VIEW_PREFIX + "user/avatar")
    public String userAvatar() {
        return EircUtil.view("system/user/avatar");
    }

    /**
     * 跟新个人信息
     *
     * @return
     */
    @GetMapping(EircConstant.VIEW_PREFIX + "user/profile/update")
    public String profileUpdate() {
        return EircUtil.view("system/user/profileUpdate");
    }

    /**
     * 查看用户
     *
     * @return
     */
    @GetMapping(EircConstant.VIEW_PREFIX + "system/user")
    @RequiresPermissions("user:view")
    public String systemUser() {
        return EircUtil.view("system/user/user");
    }

    /**
     * 用户添加
     *
     * @return
     */
    @GetMapping(EircConstant.VIEW_PREFIX + "system/user/add")
    @RequiresPermissions("user:add")
    public String systemUserAdd() {
        return EircUtil.view("system/user/userAdd");
    }

    /**
     * 用户详情
     *
     * @param username
     * @param model
     * @return
     */
    @GetMapping(EircConstant.VIEW_PREFIX + "system/user/detail/{username}")
    @RequiresPermissions("user:view")
    public String systemUserDetail(@PathVariable String username, Model model) {
        resolveUserModel(username, model, true);
        return EircUtil.view("system/user/userDetail");
    }

    /**
     * 跟新
     *
     * @param username
     * @param model
     * @return
     */
    @GetMapping(EircConstant.VIEW_PREFIX + "system/user/update/{username}")
    @RequiresPermissions("user:update")
    public String systemUserUpdate(@PathVariable String username, Model model) {
        resolveUserModel(username, model, false);
        return EircUtil.view("system/user/userUpdate");
    }

    /**
     * 权限
     *
     * @return
     */
    @GetMapping(EircConstant.VIEW_PREFIX + "system/role")
    @RequiresPermissions("role:view")
    public String systemRole() {
        return EircUtil.view("system/role/role");
    }

    /**
     * 菜单
     *
     * @return
     */
    @GetMapping(EircConstant.VIEW_PREFIX + "system/menu")
    @RequiresPermissions("menu:view")
    public String systemMenu() {
        return EircUtil.view("system/menu/menu");
    }

    /**
     * 部门
     *
     * @return
     */
    @GetMapping(EircConstant.VIEW_PREFIX + "system/dept")
    @RequiresPermissions("dept:view")
    public String systemDept() {
        return EircUtil.view("system/dept/dept");
    }
    

    /**
     * 404
     *
     * @return
     */
    @GetMapping(EircConstant.VIEW_PREFIX + "404")
    public String error404() {
        return EircUtil.view("error/404");
    }

    /**
     * 403
     *
     * @return
     */
    @GetMapping(EircConstant.VIEW_PREFIX + "403")
    public String error403() {
        return EircUtil.view("error/403");
    }

    /**
     * 500
     *
     * @return
     */
    @GetMapping(EircConstant.VIEW_PREFIX + "500")
    public String error500() {
        return EircUtil.view("error/500");
    }

    /**
     * 用户信息
     *
     * @param username
     * @param model
     * @param transform
     */
    private void resolveUserModel(String username, Model model, Boolean transform) {
        User user = userService.findByName(username);
        model.addAttribute("user", user);
        if (transform) {
            @NotBlank(message = "{required}") String sex = user.getSex();
            if (User.SEX_MALE.equals(sex))
                user.setSex("男");
            else if (User.SEX_FEMALE.equals(sex))
                user.setSex("女");
            else
                user.setSex("奥里给");
        }
        if (user.getLastLoginTime() != null) {
            model.addAttribute("lastLoginTime", DateUtil.getDateFormat(user.getLastLoginTime(), DateUtil.FULL_TIME_SPLIT_PATTERN));
        }
    }

}
