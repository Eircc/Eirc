package com.ccc.eirc.job.controller;

import com.ccc.eirc.commons.domain.EircConstant;
import com.ccc.eirc.commons.utils.EircUtil;
import com.ccc.eirc.job.domain.Job;
import com.ccc.eirc.job.service.JobService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.constraints.NotBlank;

/**
 * <a>Title:ViewController</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/30 15:51
 * @Version 1.0.0
 */
@Controller("jobView")
@RequestMapping(EircConstant.VIEW_PREFIX + "job")
public class ViewController {

    @Autowired
    private JobService jobService;

    /**
     * 访问页面
     *
     * @return
     */
    @GetMapping("job")
    @RequiresPermissions("job:view")
    public String online() {
        return EircUtil.view("job/job");
    }

    /**
     * 添加
     *
     * @return
     */
    @GetMapping("job/add")
    @RequiresPermissions("job:add")
    public String jobAdd() {
        return EircUtil.view("job/jobAdd");
    }

    @GetMapping("job/update{jobId}")
    @RequiresPermissions("job:update")
    public String jobUpdate(@NotBlank(message = "{required}") @PathVariable Long jobId, Model model) {
        Job job = jobService.findJob(jobId);
        model.addAttribute("job", job);
        return EircUtil.view("job/jobUpdate");
    }

    @GetMapping("log")
    @RequiresPermissions("job:log:view")
    public String log() {
        return EircUtil.view("job/jobLog");
    }

}
