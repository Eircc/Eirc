package com.ccc.eirc.commons.authentication;

import com.ccc.eirc.commons.domain.EircConstant;
import com.ccc.eirc.system.domain.Menu;
import com.ccc.eirc.system.domain.Role;
import com.ccc.eirc.system.domain.User;
import com.ccc.eirc.system.service.MenuService;
import com.ccc.eirc.system.service.RoleService;
import com.ccc.eirc.system.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <a>Title:ShiroRealm</a>
 * <a>Author：<a>
 * <a>Description：<a>
 * <p>
 * 自定义实现 ShiroRealm，包含认证和授权
 *
 * @Author ccc
 * @Date 2020/3/22 21:35
 * @Version 1.0.0
 */
@Component
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuService menuService;

    /**
     * 授权
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        User user = (User) SecurityUtils.getSubject().getPrincipal();
        String username = user.getUsername();

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

//        获取用户角色的有序集合集合
        List<Role> roleList = this.roleService.findUserRole(username);
        Set<String> roleSet = roleList.stream().map(Role::getRoleName).collect(Collectors.toSet());
        simpleAuthorizationInfo.setRoles(roleSet);

//        获取用户权限有序集合
        List<Menu> permissionList = this.menuService.findUserPermissions(username);
        Set<String> permissionSet = permissionList.stream().map(Menu::getPerms).collect(Collectors.toSet());
        simpleAuthorizationInfo.setStringPermissions(permissionSet);

        return simpleAuthorizationInfo;

    }

    /**
     * 认证
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
//        获取用户输入的用户名和密码
        String username = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials());
//        1d685729d113cfd03872f154939bee1c
//        1d685729d113cfd03872f154939bee1c
//        查询用户信息
        User user = userService.findByName(username);
        String pwd = user.getPassword();
        if (user == null)
            throw new UnknownAccountException(EircConstant.LOGIN_ERROR_NOREGIST);
        if (!StringUtils.equals(password, user.getPassword()))
            throw new IncorrectCredentialsException(EircConstant.LOGIN_ERROR_USERNAMEORPASSWOED);
        if (User.STATUS_LOCK.equals(user.getStatus()))
            throw new LockedAccountException(EircConstant.LOGIN_ERROR_LOCKED);
//        任何值 数据库查询密码 class名
        return new SimpleAuthenticationInfo(user, password, getName());
    }

    /**
     * 清除当前用户权限缓存
     * 使用方法：在需要清除用户权限的地方注入 ShiroRealm,然后调用其 clearCache方法。
     */
    public void clearCache(){
        PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
        super.clearCache(principals);
    }
}
