package com.ccc.eirc.system.controller;

import com.ccc.eirc.commons.annotation.Limit;
import com.ccc.eirc.commons.controller.BaseController;
import com.ccc.eirc.commons.domain.EircConstant;
import com.ccc.eirc.commons.domain.EircResopnse;
import com.ccc.eirc.commons.exception.EircException;
import com.ccc.eirc.commons.service.ValidateCodeService;
import com.ccc.eirc.commons.utils.MD5Util;
import com.ccc.eirc.monitor.domain.LoginLog;
import com.ccc.eirc.monitor.service.LoginLogService;
import com.ccc.eirc.system.domain.User;
import com.ccc.eirc.system.service.UserService;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <a>Title:LoginController</a>
 * <a>Author：<a>
 * <a>Description：<a>
 * <p>
 * 登陆
 *
 * @Author ccc
 * @Date 2020/3/23 16:27
 * @Version 1.0.0
 */
@Validated
@RestController
public class LoginController extends BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private ValidateCodeService validateCodeService;
    @Autowired
    private LoginLogService loginLogService;

    /**
     * 用户登录
     *
     * @param username   用户名
     * @param password   密码
     * @param verifyCode 验证码
     * @param rememberMe 记住我
     * @param request    request
     * @return success 成功 / fail 失败
     */
    @PostMapping(value = "login")
    @Limit(key = "login", period = 60, count = 20, name = "登录接口", prefix = "limit")
    public EircResopnse login(
            @NotBlank(message = "{required}") String username,
            @NotBlank(message = "{required}") String password,
            @NotBlank(message = "{required}") String verifyCode,
            boolean rememberMe,
            HttpServletRequest request) {
        HttpSession session = request.getSession();
        validateCodeService.check(session.getId(), verifyCode);
        password = MD5Util.encrypt(username.toLowerCase(), password);
        UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
        try {
            super.login(token);
            LoginLog loginLog = new LoginLog();
            loginLog.setUsername(username);
            loginLog.setSystemBrowserInfo();
            this.loginLogService.saveLoginLog(loginLog);
            return new EircResopnse().success();
        }catch (Exception e){
            return new EircResopnse().fail().message("用户名或密码错误");
        }
    }


    /**
     * 用户注册
     *
     * @param username
     * @param password
     * @return
     */
    @PostMapping("regist")
    public EircResopnse regist(
            @NotBlank(message = "{required}") String username,
            @NotBlank(message = "{password}") String password) {
        User user = this.userService.findByName(username);
        if (user != null) {
            throw new EircException("用户名已存在！");
        }
        this.userService.regist(username, password);
        return new EircResopnse().success();
    }

    /**
     * 更新登陆时间 获取系统访问记录 最近七天的访问情况
     *
     * @param username
     * @return
     */
    @GetMapping("index/{username}")
    public EircResopnse index(@NotBlank(message = "{required}") @PathVariable String username) {
//        跟新登陆时间
        this.userService.updateLoginTime(username);
        HashMap<String, Object> data = new HashMap<>(EircConstant.DATA_MAP_INITIAL_CAPACITY);

//        获取系统访问记录
        Long totalVisitCount = this.loginLogService.findTotalVisitCount();
        data.put("totalVisitCount", totalVisitCount);
        Long todayVisitCount = this.loginLogService.findTodayVisitCount();
        data.put("todayVisitCount", todayVisitCount);
        Long todayIp = this.loginLogService.findTodayIp();
        data.put("todayIp", todayIp);

//        获取近期访问记录
        List<Map<String, Object>> lastSevenVisitCount = this.loginLogService.findLastSevenDaysVisitCount(null);
        User param = new User();
        param.setUsername(username);
        List<Map<String, Object>> lastSevenUserVisitCount = this.loginLogService.findLastSevenDaysVisitCount(param);
        data.put("lastSevenUserVisitCount", lastSevenUserVisitCount);
        return new EircResopnse().success().data(data);
    }


    /**
     * 验证码
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @GetMapping(value = "images/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        validateCodeService.create(request, response);
    }

}
