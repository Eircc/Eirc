package com.ccc.eirc.monitor.controller;

import com.ccc.eirc.commons.domain.EircConstant;
import com.ccc.eirc.commons.utils.EircUtil;
import com.ccc.eirc.monitor.domain.JvmInfo;
import com.ccc.eirc.monitor.domain.ServerInfo;
import com.ccc.eirc.monitor.domain.TomcatInfo;
import com.ccc.eirc.monitor.endPoint.EircMetricsEndpoint.EircMetricResponse;
import com.ccc.eirc.monitor.helper.EircActuatorHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * <a>Title:ViewController</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/28 19:26
 * @Version 1.0.0
 */
@Slf4j
@Controller("monitorView")
@RequestMapping(EircConstant.VIEW_PREFIX + "monitor")
public class ViewController {

    @Autowired
    private EircActuatorHelper actuatorHelper;

    @GetMapping("online")
    @RequiresPermissions("online:view")
    public String online() {
        return EircUtil.view("monitor/online");
    }

    @GetMapping("log")
    @RequiresPermissions("log:view")
    public String log() {
        return EircUtil.view("monitor/log");
    }

    @GetMapping("loginlog")
    @RequiresPermissions("loginlog:view")
    public String loginLog() {
        return EircUtil.view("monitor/loginLog");
    }

    @GetMapping("httptrace")
    @RequiresPermissions("httptrace:view")
    public String httptrace() {
        return EircUtil.view("monitor/httpTrace");
    }

    @GetMapping("jvm")
    @RequiresPermissions("jvm:view")
    public String jvmInfo(Model model) {
        List<EircMetricResponse> jvm = actuatorHelper.getMetricResponseByType("jvm");
        JvmInfo jvmInfo = actuatorHelper.getJvmInfoFromMetricData(jvm);
        model.addAttribute("jvm", jvmInfo);
        return EircUtil.view("monitor/jvmInfo");
    }

    @GetMapping("tomcat")
    @RequiresPermissions("tomcat:view")
    public String tomcatInfo(Model model) {
        List<EircMetricResponse> tomcat = actuatorHelper.getMetricResponseByType("tomcat");
        TomcatInfo tomcatInfo = actuatorHelper.getTomcatInfoFromMetricData(tomcat);
        model.addAttribute("tomcat", tomcatInfo);
        return EircUtil.view("monitor/tomcatInfo");
    }

    @GetMapping("server")
    @RequiresPermissions("server:view")
    public String serverInfo(Model model) {
        List<EircMetricResponse> jdbcInfo = actuatorHelper.getMetricResponseByType("jdbc");
        List<EircMetricResponse> systemInfo = actuatorHelper.getMetricResponseByType("system");
        List<EircMetricResponse> processInfo = actuatorHelper.getMetricResponseByType("process");

        ServerInfo serverInfo = actuatorHelper.getServerInfoFromMetricData(jdbcInfo, systemInfo, processInfo);
        model.addAttribute("server", serverInfo);
        return EircUtil.view("monitor/serverInfo");
    }

    @GetMapping("swagger")
    public String swagger() {
        return EircUtil.view("monitor/swagger");
    }
}
