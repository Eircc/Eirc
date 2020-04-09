package com.ccc.eirc.job.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ccc.eirc.commons.domain.QueryRequest;
import com.ccc.eirc.job.domain.JobLog;

/**
 * <a>Title:JobLogService</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/4/1 13:29
 * @Version 1.0.0
 */
public interface JobLogService {

    /**
     * 任务执行日志
     *
     * @param jobLog jobLog
     */
    void saveJob(JobLog jobLog);

    /**
     * 查询
     *
     * @param request request
     * @param jobLog  jobLog
     * @return
     */
    IPage<JobLog> findJobLogs(QueryRequest request, JobLog jobLog);

    /**
     * 删除
     *
     * @param jobLogIds jobLogIds
     */
    void jobLogsDelete(String[] jobLogIds);
}
