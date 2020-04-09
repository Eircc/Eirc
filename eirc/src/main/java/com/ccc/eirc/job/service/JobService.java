package com.ccc.eirc.job.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ccc.eirc.commons.domain.QueryRequest;
import com.ccc.eirc.job.domain.Job;

/**
 * <a>Title:JobService</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/30 15:52
 * @Version 1.0.0
 */
public interface JobService extends IService<Job> {
    /**
     * 获取信息
     *
     * @param request request
     * @param job     对象
     * @return
     */
    IPage<Job> findJobs(QueryRequest request, Job job);

    /**
     * 新增
     *
     * @param job 对象
     */
    void create(Job job);

    /**
     * 删除
     *
     * @param ids 对象
     */
    void deleteJobs(String[] ids);

    /**
     * 修改
     *
     * @param job 对象
     */
    void updateJob(Job job);

    /**
     * 更新后
     *
     * @param jobId
     * @return
     */
    Job findJob(Long jobId);

/*    *//**
     * 执行定时任务
     *
     * @param jobIds
     *//*
    void run(String jobIds);

    *//**
     * 暂停定时任务
     *
     * @param jobIds
     *//*
    void pause(String jobIds);

    *//**
     * 恢复定时任务
     *
     * @param jobIds
     *//*
    void resume(String jobIds);

    int updateBatch(String jobIds, String status);*/
}
