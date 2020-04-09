package com.ccc.eirc.job.controller;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.ccc.eirc.commons.annotation.ControllerEndpoint;
import com.ccc.eirc.commons.controller.BaseController;
import com.ccc.eirc.commons.domain.EircResopnse;
import com.ccc.eirc.commons.domain.QueryRequest;
import com.ccc.eirc.job.domain.JobLog;
import com.ccc.eirc.job.service.JobLogService;
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
 * <a>Title:JobLogController</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/4/1 13:32
 * @Version 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("jobLog")
public class JobLogController extends BaseController {

    @Autowired
    private JobLogService jobLogService;

    /**
     * 获取调度日志
     *
     * @param request request
     * @param jobLog  jobLog
     * @return EircResopnse()
     */
    @GetMapping
    @RequiresPermissions("job:log:view")
    public EircResopnse jobLogList(QueryRequest request, JobLog jobLog) {
        Map<String, Object> dataTable = getDataTable(this.jobLogService.findJobLogs(request, jobLog));
        return new EircResopnse().success().data(dataTable);
    }

    @GetMapping("delete/{jobIds}")
    @RequiresPermissions("job:log:delete")
    @ControllerEndpoint(exceptionMessage = "删除调度日志失败")
    public EircResopnse jobLogsDelete(@NotBlank(message = "{required}") @PathVariable String jobIds) {
        String[] jobLogIds = jobIds.split(StringPool.COMMA);
        this.jobLogService.jobLogsDelete(jobLogIds);
        return new EircResopnse().success();
    }


    @GetMapping("excel")
    @RequiresPermissions("job:log:export")
    @ControllerEndpoint(exceptionMessage = "导出Excel失败")
    public void export(QueryRequest request, JobLog jobLog, HttpServletResponse response) {
        List<JobLog> jobLogs = this.jobLogService.findJobLogs(request, jobLog).getRecords();
        ExcelKit.$Export(JobLog.class, response).downXlsx(jobLogs, false);
    }

}
