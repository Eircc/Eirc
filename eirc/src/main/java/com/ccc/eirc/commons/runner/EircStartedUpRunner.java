package com.ccc.eirc.commons.runner;

import com.ccc.eirc.commons.properties.EircProperties;
import com.ccc.eirc.commons.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

/**
 * <a>Title:EircStartedUpRunner</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/27 16:39
 * @Version 1.0.0
 */
@Slf4j
@Component
public class EircStartedUpRunner implements ApplicationRunner {

    @Autowired
    private ConfigurableApplicationContext context;
    @Autowired
    private EircProperties eircProperties;
    @Autowired
    private RedisService redisService;

    @Value("${server.port:8080}")
    private String port;
    @Value("${server.servlet.context-path:}")
    private String contextPath;
    @Value("${spring.profiles.active}")
    private String active;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
//            测试 Redis 是否正常连接
            redisService.hasKye("eirc_test");
        } catch (Exception e) {
            log.error(" ____   __    __    __ ");
            log.error("| |_   / /\\  | | | |");
            log.error("|_|   /_/--\\ |_| |_|__");
            log.error("                        ");
            log.error("EIRC 系统启动失败，{}", e.getMessage());
            log.error("Redis 连接异常，请检查Redis连接配置并确保Redis服务已启动");
//            关闭 EIRC
            context.close();
        }
        if (context.isActive()) {
            InetAddress address = InetAddress.getLocalHost();
//            拼接端口号
            String url = String.format("http://%s:%s", address.getHostAddress(), port);
            String loginUrl = eircProperties.getShiro().getLoginUrl();
            if (StringUtils.isNotBlank(loginUrl))
                url += loginUrl;
            log.info(" __    ___   _      ___   _     ____ _____  ____ ");
            log.info("/ /`  / / \\ | |\\/| | |_) | |   | |_   | |  | |_  ");
            log.info("\\_\\_, \\_\\_/ |_|  | |_|   |_|__ |_|__  |_|  |_|__ ");
            log.info("                                                      ");
            log.info("EIRC 停车系统启动完毕，地址：{}", url);

            boolean auto = eircProperties.isAutoOpenBrowser();
            if (auto && StringUtils.equalsIgnoreCase(active, "develop")) ;
            {
                String os = System.getProperty("os.name");
//                默认为 windows时才自动打开页面
                if (StringUtils.containsIgnoreCase(os, "windows")) {
//                    使用默认浏览器打开系统登录页
                    Runtime.getRuntime().exec("cmd  /c  start " + url);
                }
            }
        }
    }
}
