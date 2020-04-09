package com.ccc.eirc.monitor.controller;

import com.ccc.eirc.commons.annotation.ControllerEndpoint;
import com.ccc.eirc.commons.domain.EircResopnse;
import com.ccc.eirc.commons.utils.DateUtil;
import com.ccc.eirc.monitor.domain.EircHttpTrace;
import com.ccc.eirc.monitor.endPoint.EircHttpTraceEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <a>Title:EircActuatorController</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/28 17:32
 * @Version 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("febs/actuator")
public class EircActuatorController {

    @Autowired
    private EircHttpTraceEndpoint httpTraceEndpoint;

    @GetMapping("httptrace")
    @RequiresPermissions("httptrace:view")
    @ControllerEndpoint(exceptionMessage = "请求追踪失败")
    public EircResopnse httptrace(String method, String url) {
        EircHttpTraceEndpoint.EircHttpTraceDescriptor trace = httpTraceEndpoint.trace();
        List<HttpTrace> httpTracesList = trace.getTraces();
        List<EircHttpTrace> eircHttpTraces = new ArrayList<>();
        httpTracesList.forEach(t -> {
            EircHttpTrace eircHttpTrace = new EircHttpTrace();
            eircHttpTrace.setRequestTime(DateUtil.formatInstant(t.getTimestamp(), DateUtil.FULL_TIME_SPLIT_PATTERN));
            eircHttpTrace.setMethod(t.getRequest().getMethod());
            eircHttpTrace.setUrl(t.getRequest().getUri());
            eircHttpTrace.setStatus(t.getResponse().getStatus());
            eircHttpTrace.setTimeTaken(t.getTimeTaken());
            if (StringUtils.isNotBlank(method) && StringUtils.isNotBlank(url)) {
                if (StringUtils.containsIgnoreCase(method, eircHttpTrace.getMethod())
                        && StringUtils.containsIgnoreCase(eircHttpTrace.getUrl().toString(), url))
                    eircHttpTraces.add(eircHttpTrace);
            } else if (StringUtils.isNotBlank(method)) {
                if (StringUtils.equalsIgnoreCase(method, eircHttpTrace.getMethod()))
                    eircHttpTraces.add(eircHttpTrace);
            } else if (StringUtils.isNotBlank(url)) {
                if (StringUtils.containsIgnoreCase(eircHttpTrace.getUrl().toString(), url))
                    eircHttpTraces.add(eircHttpTrace);
            } else {
                eircHttpTraces.add(eircHttpTrace);
            }
        });
        Map<Object, Object> data = new HashMap<>();
        data.put("rows", eircHttpTraces);
        data.put("total", eircHttpTraces.size());
        return new EircResopnse().success().data(data);
    }
}
