package com.ccc.eirc.commons.exception;

/**
 * <a>Title:RdisConnectException</a>
 * <a>Author：<a>
 * <a>Description：<a>
 * <p>
 * Redis 连接异常
 *
 * @Author ccc
 * @Date 2020/3/22 19:57
 * @Version 1.0.0
 */
public class RedisConnectException extends Exception {
    //    序列化接口
    private static final long serialVersionUID = 3731337003480854505L;

    public RedisConnectException(String message) {
        super(message);
    }

}
