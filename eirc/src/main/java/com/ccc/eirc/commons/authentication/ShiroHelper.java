package com.ccc.eirc.commons.authentication;

import com.ccc.eirc.commons.annotation.Helper;
import org.apache.shiro.authz.AuthorizationInfo;

/**
 * <a>Title:ShiroHelper</a>
 * <a>Author：<a>
 * <a>Description：<a>
 * <p>
 * 获取当前用户的角色和权限集合
 *
 * @Author ccc
 * @Date 2020/3/22 21:57
 * @Version 1.0.0
 */
@Helper
public class ShiroHelper extends ShiroRealm {
    /**
     * 获取当前用户的角色和权限集合
     *
     * @return
     */
    public AuthorizationInfo getCurrentUserAuthorizationInfo() {
        return super.doGetAuthorizationInfo(null);
    }
}
