package com.ccc.eirc.job.util;

import com.ccc.eirc.job.domain.Job;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;

/**
 * <a>Title:ScheduleUtils</a>
 * <a>Author：<a>
 * <a>Description：<a>
 * <p>
 * 定时任务工具类
 *
 * @Author ccc
 * @Date 2020/4/1 13:56
 * @Version 1.0.0
 */
@Slf4j
public class ScheduleUtils {

    protected ScheduleUtils() {

    }

    //    任务名称前缀
    private static final String JOB_NAME_PREFIX = "TASK_";

    //    获取触发器 key
    private static TriggerKey getTriggerKey(Long jobId) {
        return TriggerKey.triggerKey(JOB_NAME_PREFIX + jobId);
    }

    //    获取jobKey
    private static JobKey getJobKey(Long jobId) {
        return JobKey.jobKey(JOB_NAME_PREFIX + jobId);
    }


    public static CronTrigger getCronTrigger(Scheduler scheduler, Long JobId) {
        try {
            return (CronTrigger) scheduler.getTrigger(getTriggerKey(JobId));
        } catch (Exception e) {
            log.error("获取Cron表达式失败", e);
        }
        return null;
    }

    /**
     * 创建定时任务
     *
     * @param scheduler   scheduler
     * @param scheduleJob scheduleJob
     */
    public static void createScheduleJob(Scheduler scheduler, Job scheduleJob) {
        try {
//            构建job信息
            JobDetail jobDetail = JobBuilder.newJob(ScheduleJob.class).withIdentity(getJobKey(scheduleJob.getJobId())).build();

//            表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression())
                    .withMisfireHandlingInstructionDoNothing();

//            按照新的 cronExpression 表达式构建一个新的 trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(scheduleJob.getJobId()))
                    .withSchedule(scheduleBuilder).build();

//            放入参数,运行时得方法可以获取
            jobDetail.getJobDataMap().put(Job.JOB_PARAM_KEY, scheduleJob);

            scheduler.scheduleJob(jobDetail, trigger);

            if (scheduleJob.getStatus().equals(Job.ScheduleStatus.PAUSE.getValue())) {
                pauseJob(scheduler, scheduleJob.getJobId());
            }
        } catch (Exception e) {
            log.error("创建定时任务失败", e);
        }
    }

    /**
     * 更新定时任务
     *
     * @param scheduler
     * @param scheduleJob
     */
    public static void updateScheduleJob(Scheduler scheduler, Job scheduleJob) {
        try {
            TriggerKey triggerKey = getTriggerKey(scheduleJob.getJobId());

//            表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression())
                    .withMisfireHandlingInstructionDoNothing();
            CronTrigger trigger = getCronTrigger(scheduler, scheduleJob.getJobId());
            if (trigger == null) {
                throw new SchedulerException("获取Cron表达式失败");
            } else {
//                按照新的 cronExpression 表达式构建一个新的 trigger
                trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
//                参数
                trigger.getJobDataMap().put(Job.JOB_PARAM_KEY, scheduleJob);
            }

            scheduler.rescheduleJob(triggerKey, trigger);

//            暂停任务
            if (scheduleJob.getStatus().equals(Job.ScheduleStatus.PAUSE.getValue())) {
                pauseJob(scheduler, scheduleJob.getJobId());
            }
        } catch (SchedulerException e) {
            log.error("更新定时任务失败", e);
        }
    }

    /**
     * 立即执行任务
     *
     * @param scheduler   scheduler
     * @param scheduleJob scheduleJob
     */
    public static void run(Scheduler scheduler, Job scheduleJob) {
        try {
//            参数
            JobDataMap dataMap = new JobDataMap();
            dataMap.put(Job.JOB_PARAM_KEY, scheduleJob);

            scheduler.triggerJob(getJobKey(scheduleJob.getJobId()), dataMap);
        } catch (SchedulerException e) {
            log.error("执行定时任务失败", e);
        }
    }

    /**
     * 暂停任务
     *
     * @param scheduler scheduler
     * @param jobId     jobId
     */
    public static void pauseJob(Scheduler scheduler, Long jobId) {
        try {
            scheduler.pauseJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            log.error("暂停定时任务失败", e);
        }
    }

    /**
     * 恢复任务
     *
     * @param scheduler scheduler
     * @param jobId     jobId
     */
    public static void resumeJob(Scheduler scheduler, Long jobId) {
        try {
            scheduler.resumeJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            log.error("恢复定时任务失败", e);
        }
    }

    /**
     * 删除定时任务
     *
     * @param scheduler scheduler
     * @param jobId     jobId
     */
    public static void deleteScheduleJob(Scheduler scheduler, Long jobId) {
        try {
            scheduler.deleteJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            log.error("删除定时任务失败", e);
        }
    }


}
