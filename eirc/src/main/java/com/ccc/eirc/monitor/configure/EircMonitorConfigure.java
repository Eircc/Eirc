package com.ccc.eirc.monitor.configure;

import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <a>Title:EircMonitorConfigure</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/28 14:39
 * @Version 1.0.0
 */
@Configuration
public class EircMonitorConfigure {

    @Bean
    public HttpTraceRepository inMemoryHttpTraceRepository(){
        return new InMemoryHttpTraceRepository();
    }
}
