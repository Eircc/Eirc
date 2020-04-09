package com.ccc.eirc.job.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * <a>Title:TestTask</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/4/1 15:05
 * @Version 1.0.0
 */
@Slf4j
@Component
public class TestTask {

    public void test(String params) {
        log.info("我是带参数的test方法，正在被执行，参数为：{}" , params);
    }
    public void test1() {
        log.info("我是不带参数的test1方法，正在被执行");
    }
}
