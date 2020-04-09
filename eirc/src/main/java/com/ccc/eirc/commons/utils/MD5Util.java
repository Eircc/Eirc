package com.ccc.eirc.commons.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * <a>Title:MD5Util</a>
 * <a>Author：<a>
 * <a>Description：<a>
 * <p>
 * MD5加密
 *
 * @Author ccc
 * @Date 2020/3/23 17:27
 * @Version 1.0.0
 */
public class MD5Util {

    protected MD5Util() {

    }

    /**
     * md5
     */
    private static final String ALGORITH_NAME = "md5";

    /**
     * 散列次数
     */
    private static final int HASH_ITERATIONS = 5;

    /**
     * 盐值加密 用户名为 salt
     */
    public static String encrypt(String username, String password) {
        String salt = StringUtils.lowerCase(username);
        password = StringUtils.lowerCase(password);
//        加密方式  密码  盐 散列次数
        return new SimpleHash(ALGORITH_NAME, password, ByteSource.Util.bytes(salt), HASH_ITERATIONS).toHex();
    }

    public static void main(String[] args) {
        String username = "ccc";
        String pwd = "123456";
        String newPwd = MD5Util.encrypt(username, pwd);
        System.out.println("newPwd = " + newPwd);
    }
}
