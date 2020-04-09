package com.ccc.eirc.monitor.controller;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.ccc.eirc.commons.annotation.ControllerEndpoint;
import com.ccc.eirc.commons.controller.BaseController;
import com.ccc.eirc.commons.domain.EircResopnse;
import com.ccc.eirc.commons.domain.QueryRequest;
import com.ccc.eirc.monitor.domain.LoginLog;
import com.ccc.eirc.monitor.service.LoginLogService;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * <a>Title:LoginLogController</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/28 14:40
 * @Version 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("loginLog")
public class LoginLogController extends BaseController {

    @Autowired
    private LoginLogService loginLogService;

    /**
     * 登陆日志 并分页
     * @param loginLog  登录日志
     * @param request   request
     * @return          EircResopnse()
     */
    @GetMapping("list")
    @RequiresPermissions("loginlog:view")
    public EircResopnse loginLogList(LoginLog loginLog, QueryRequest request){
        Map<String,Object> dataTable = getDataTable(this.loginLogService.findLoginLogs(loginLog,request));
        return new EircResopnse().success().data(dataTable);
    }

    /**
     * 删除登陆日志
     * @param ids 对象集合
     * @return EircResopnse()
     */
    @GetMapping("delete/{ids}")
    @RequiresPermissions("loginlog:delete")
    @ControllerEndpoint(exceptionMessage = "删除登陆日志失败")
    public EircResopnse deleteLoginLog(@NotBlank(message = "{required}") @PathVariable String ids){
        String[] loginLogId = ids.split(StringPool.COMMA);
        this.loginLogService.deleteLoginLogs(loginLogId);
        return new EircResopnse().success();
    }

    /**
     * 导出Excel
     * @param loginLog 对象
     * @param request 参数
     * @param response response
     */
    @GetMapping("excel")
    @RequiresPermissions("loginlog:export")
    @ControllerEndpoint(exceptionMessage = "导出Excel失败")
    public void export(LoginLog loginLog, QueryRequest request, HttpServletResponse response){
        List<LoginLog> loginLogs = this.loginLogService.findLoginLogs(loginLog,request).getRecords();
        ExcelKit.$Export(LoginLog.class,response).downXlsx(loginLogs,false);
    }
}

