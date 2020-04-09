package com.ccc.eirc.commons.function;

import com.baomidou.mybatisplus.extension.api.R;
import com.ccc.eirc.commons.exception.RedisConnectException;
import org.apache.poi.ss.formula.functions.T;

/**
 * <a>Title:JedisExecutor</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/4/1 15:33
 * @Version 1.0.0
 */
@FunctionalInterface
public interface JedisExecutor {

    R excute(T t) throws RedisConnectException;

}
