package com.ccc.eirc.commons.domain;

import org.springframework.http.HttpStatus;

import java.util.HashMap;

/**
 * <a>Title:EircResopnse</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/23 16:20
 * @Version 1.0.0
 */
public class  EircResopnse extends HashMap<String, Object> {

    private static final long serialVersionUID = 8590144647936233607L;

    /**
     * HTTP 状态码
     *
     * @param status
     * @return
     */
    public EircResopnse code(HttpStatus status) {
        this.put("code", status.value());
        return this;
    }

    /**
     * 信息
     *
     * @param message
     * @return
     */
    public EircResopnse message(String message) {
        this.put("message", message);
        return this;
    }

    /**
     * 数据
     *
     * @param data
     * @return
     */
    public EircResopnse data(Object data) {
        this.put("data", data);
        return this;
    }

    /**
     * 成功
     *
     * @return
     */
    public EircResopnse success() {
        this.code(HttpStatus.OK);
        return this;
    }

    /**
     * 失败
     *
     * @return
     */
    public EircResopnse fail() {
        this.code(HttpStatus.INTERNAL_SERVER_ERROR);
        return this;
    }

    /**
     * 重写 put 方法
     *
     * @param key
     * @param value
     * @return
     */
    @Override
    public EircResopnse put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
