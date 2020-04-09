package com.ccc.eirc.job.controller;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.ccc.eirc.commons.annotation.ControllerEndpoint;
import com.ccc.eirc.commons.controller.BaseController;
import com.ccc.eirc.commons.domain.EircResopnse;
import com.ccc.eirc.commons.domain.QueryRequest;
import com.ccc.eirc.job.domain.Job;
import com.ccc.eirc.job.service.JobService;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * <a>Title:JobControllere</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/30 16:00
 * @Version 1.0.0
 */
@Slf4j
@Validated
@RestController
@RequestMapping("job")
public class JobController extends BaseController {

    @Autowired
    private JobService jobService;

    /**
     * 获取job信息
     *
     * @param request request
     * @param job     job
     * @return EircResopnse()
     */
    @GetMapping
    @RequiresPermissions("job:view")
    public EircResopnse jobList(QueryRequest request, Job job) {
        Map<String, Object> dataTable = getDataTable(this.jobService.findJobs(request, job));
        return new EircResopnse().success().data(dataTable);
    }

    @GetMapping("cron/check")
    public boolean checkCron(String cron) {
        try {
            return CronExpression.isValidExpression(cron);
        } catch (Exception e) {
            return false;
        }
    }

    @PostMapping
    @RequiresPermissions("job:add")
    @ControllerEndpoint(operation = "新增信息", exceptionMessage = "新增失败")
    public EircResopnse addJob(@Valid Job job) {
        this.jobService.create(job);
        return new EircResopnse().success();
    }

    @GetMapping("delete/{jobIds}")
    @RequiresPermissions("job:delete")
    @ControllerEndpoint(operation = "删除信息", exceptionMessage = "删除失败")
    public EircResopnse deleteJobs(@NotBlank(message = "{required}") @PathVariable String jobIds) {
        String[] ids = jobIds.split(StringPool.COMMA);
        this.jobService.deleteJobs(ids);
        return new EircResopnse().success();
    }

    @PostMapping("update")
    @RequiresPermissions("job:update")
    @ControllerEndpoint(operation = "修改信息", exceptionMessage = "修改失败")
    public EircResopnse updateJob(@Valid Job job) {
        this.jobService.updateJob(job);
        return new EircResopnse().success();
    }

    @GetMapping("excel")
    @RequiresPermissions("job:export")
    @ControllerEndpoint(exceptionMessage = "导出excel失败")
    public void export(QueryRequest request, Job job, HttpServletResponse response) {
        List<Job> jobs = this.jobService.findJobs(request, job).getRecords();
        ExcelKit.$Export(Job.class, response).downXlsx(jobs, false);
    }

/*    @GetMapping("run/{jobIds}")
    @RequiresPermissions("job:run")
    @ControllerEndpoint(operation = "执行定时任务", exceptionMessage = "执行定时任务失败")
    public EircResopnse runJob(@NotBlank(message = "{required}") @PathVariable String jobIds) {
        this.jobService.run(jobIds);
        return new EircResopnse().success();
    }

    @GetMapping("pause/{jobIds}")
    @RequiresPermissions("job:pause")
    @ControllerEndpoint(operation = "暂停定时任务", exceptionMessage = "暂停定时任务失败")
    public EircResopnse pauseJob(@NotBlank(message = "{required}") @PathVariable String jobIds) {
        this.jobService.pause(jobIds);
        return new EircResopnse().success();
    }

    @GetMapping("resume/{jobIds}")
    @RequiresPermissions("job:resume")
    @ControllerEndpoint(operation = "恢复定时任务", exceptionMessage = "恢复定时任务失败")
    public EircResopnse resumeJob(@NotBlank(message = "{required}") @PathVariable String jobIds) {
        this.jobService.resume(jobIds);
        return new EircResopnse().success();
    }*/

}
