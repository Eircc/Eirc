package com.ccc.eirc.monitor.controller;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.ccc.eirc.commons.annotation.ControllerEndpoint;
import com.ccc.eirc.commons.controller.BaseController;
import com.ccc.eirc.commons.domain.EircResopnse;
import com.ccc.eirc.commons.domain.QueryRequest;
import com.ccc.eirc.monitor.domain.SystemLog;
import com.ccc.eirc.monitor.service.LogService;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * <a>Title:LogController</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/28 16:57
 * @Version 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("log")
public class LogController extends BaseController {

    @Autowired
    private LogService logService;

    /**
     * 获取登录日志
     *
     * @param log     对象
     * @param request 参数
     * @return EircResopnse()
     */
    @GetMapping("list")
    @RequiresPermissions("log:view")
    public EircResopnse logList(SystemLog log, QueryRequest request) {
        Map<String, Object> dataTable = getDataTable(this.logService.findLogs(log, request));
        return new EircResopnse().success().data(dataTable);
    }

    /**
     * 删除系统日志
     *
     * @param ids 对象
     * @return EircResopnse()
     */
    @GetMapping("delete/{ids}")
    @RequiresPermissions("log:delete")
    @ControllerEndpoint(exceptionMessage = "删除系统日志失败")
    public EircResopnse deleteLogs(@NotBlank(message = "{required}") @PathVariable String ids) {
        String[] logIds = ids.split(StringPool.COMMA);
        this.logService.deleteLogs(logIds);
        return new EircResopnse().success();
    }

    /**
     * 导出系统日志
     *
     * @param log      对象
     * @param request  参数
     * @param response response
     */
    @GetMapping("excel")
    @RequiresPermissions("log:export")
    @ControllerEndpoint(exceptionMessage = "导出Excel失败")
    public void export(SystemLog log, QueryRequest request, HttpServletResponse response) {
        List<SystemLog> logs = this.logService.findLogs(log, request).getRecords();
        ExcelKit.$Export(SystemLog.class, response).downXlsx(logs, false);
    }
}
