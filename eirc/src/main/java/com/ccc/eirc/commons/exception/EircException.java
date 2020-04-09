package com.ccc.eirc.commons.exception;

/**
 * <a>Title:EircException</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/23 17:06
 * @Version 1.0.0
 */
public class EircException extends RuntimeException {

    private static final long serialVersionUID = -1687957953225933086L;

    public EircException(String message) {
        super(message);
    }
}
