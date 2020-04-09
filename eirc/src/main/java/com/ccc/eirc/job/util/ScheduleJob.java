package com.ccc.eirc.job.util;

import com.ccc.eirc.commons.utils.SpringContextUtil;
import com.ccc.eirc.job.domain.Job;
import com.ccc.eirc.job.domain.JobLog;
import com.ccc.eirc.job.service.JobLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * <a>Title:ScheduleJob</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/4/1 13:02
 * @Version 1.0.0
 */
@Slf4j
public class ScheduleJob extends QuartzJobBean {

    private ExecutorService service = Executors.newSingleThreadExecutor();

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {


        Job scheduleJob = (Job) context.getMergedJobDataMap().get(Job.JOB_PARAM_KEY);
//        获取spring bean
        JobLogService scheduleJobLogService = SpringContextUtil.getBean(JobLogService.class);

        JobLog jobLog = new JobLog();
        jobLog.setJobId(scheduleJob.getJobId());
        jobLog.setBeanName(scheduleJob.getBeanName());
        jobLog.setMethodName(scheduleJob.getMethodName());
        jobLog.setParams(scheduleJob.getParams());
        jobLog.setCreateTime(new Date());

        long startTime = System.currentTimeMillis();

        try {
//            执行任务
            log.info("任务准备执行，任务ID：{}", scheduleJob.getJobId());
            ScheduleRunnable task = new ScheduleRunnable(scheduleJob.getBeanName(), scheduleJob.getMethodName(), scheduleJob.getParams());
            Future<?> future = service.submit(task);
            future.get();
            long times = System.currentTimeMillis();
            jobLog.setTimes(times);
//            任务执行状态 0：成功 1：失败
            jobLog.setStatus(JobLog.JOB_SUCCESS);

            log.info("任务执行完毕，任务ID：{}，总共耗时：{}毫秒", scheduleJob.getJobId(), times);
        } catch (Exception e) {
            log.error("任务执行失败，任务ID：{}" + scheduleJob.getJobId(), e);
            long times = System.currentTimeMillis();
            jobLog.setTimes(times);
//            任务执行状态 0：成功 1：失败
            jobLog.setStatus(JobLog.JOB_FAIL);
            jobLog.setError(StringUtils.substring(e.toString(), 0, 2000));
        } finally {
            scheduleJobLogService.saveJob(jobLog);
        }


    }
}
