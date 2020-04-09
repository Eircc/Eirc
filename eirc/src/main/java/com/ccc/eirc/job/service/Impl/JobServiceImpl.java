package com.ccc.eirc.job.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccc.eirc.commons.domain.EircConstant;
import com.ccc.eirc.commons.domain.QueryRequest;
import com.ccc.eirc.commons.utils.SortUtil;
import com.ccc.eirc.job.domain.Job;
import com.ccc.eirc.job.mapper.JobMapper;
import com.ccc.eirc.job.service.JobService;
import com.ccc.eirc.job.util.ScheduleUtils;
import org.apache.commons.lang3.StringUtils;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <a>Title:JobServiceImpl</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/30 15:54
 * @Version 1.0.0
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class JobServiceImpl extends ServiceImpl<JobMapper, Job> implements JobService {


/*
    @Autowired
    private Scheduler scheduler;


    */
/**
     * 项目启动时,初始化定时器
     *//*

    @PostConstruct
    public void init() {
        List<Job> scheduleJobList = this.baseMapper.queryList();
//        如果不存在,则创建
        scheduleJobList.forEach(scheduleJob -> {
            CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getJobId());
            if (cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
            } else {
                ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
            }
        });
    }
*/


    @Override
    public IPage<Job> findJobs(QueryRequest request, Job job) {

        LambdaQueryWrapper<Job> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(job.getBeanName())) {
            queryWrapper.eq(Job::getBeanName, job.getBeanName());
        }
        if (StringUtils.isNotBlank(job.getMethodName())) {
            queryWrapper.eq(Job::getMethodName, job.getMethodName());
        }
        if (StringUtils.isNotBlank(job.getParams())) {
            queryWrapper.like(Job::getParams, job.getParams());
        }
        if (StringUtils.isNotBlank(job.getRemark())) {
            queryWrapper.like(Job::getRemark, job.getRemark());
        }
        if (StringUtils.isNotBlank(job.getStatus())) {
            queryWrapper.eq(Job::getStatus, job.getStatus());
        }

        if (StringUtils.isNotBlank(job.getCreateTimeFrom()) && StringUtils.isNotBlank(job.getCreateTimeTo())) {
            queryWrapper
                    .ge(Job::getCreateTime, job.getCreateTimeFrom())
                    .le(Job::getCreateTime, job.getCreateTimeTo());
        }

        Page<Job> page = new Page<>(request.getPageNum(), request.getPageSize());

        SortUtil.handlePageSort(request, page, "create_Time", EircConstant.ORDER_DESC, false);
        return this.page(page, queryWrapper);
    }

    @Override
    @Transactional
    public void create(Job job) {
        job.setCreateTime(new Date());
        this.save(job);
    }

    @Override
    @Transactional
    public void deleteJobs(String[] ids) {
        List<String> list = Arrays.asList(ids);
        this.baseMapper.deleteBatchIds(list);
    }

    @Override
    @Transactional
    public void updateJob(Job job) {
        job.setCreateTime(new Date());
        this.baseMapper.updateById(job);
    }

    @Override
    public Job findJob(Long jobId) {
        return this.getById(jobId);
    }

/*    @Override
    @Transactional
    public void run(String jobIds) {
        String[] list = jobIds.split(StringPool.COMMA);
        Arrays.stream(list).forEach(jobId -> ScheduleUtils.run(scheduler, this.findJob(Long.valueOf(jobId))));
    }

    @Override
    @Transactional
    public void pause(String jobIds) {
        String[] list = jobIds.split(StringPool.COMMA);
        Arrays.stream(list).forEach(jobId -> ScheduleUtils.pauseJob(scheduler, Long.valueOf(jobId)));
        this.updateBatch(jobIds, Job.ScheduleStatus.PAUSE.getValue());
    }

    @Override
    @Transactional
    public void resume(String jobIds) {
        String[] list = jobIds.split(StringPool.COMMA);
        Arrays.stream(list).forEach(jobId -> ScheduleUtils.resumeJob(scheduler, Long.valueOf(jobId)));
        this.updateBatch(jobIds, Job.ScheduleStatus.NORMAL.getValue());
    }

    @Override
    @Transactional
    public int updateBatch(String jobIds, String status) {
        List<String> list = Arrays.asList(jobIds.split(StringPool.COMMA));
        Job job = new Job();
        job.setStatus(status);
        return this.baseMapper.update(job, new LambdaQueryWrapper<Job>().in(Job::getJobId, list));
    }*/
}
