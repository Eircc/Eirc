package com.ccc.eirc.job.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccc.eirc.commons.domain.EircConstant;
import com.ccc.eirc.commons.domain.QueryRequest;
import com.ccc.eirc.commons.utils.SortUtil;
import com.ccc.eirc.job.domain.JobLog;
import com.ccc.eirc.job.mapper.JobLogMapper;
import com.ccc.eirc.job.service.JobLogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * <a>Title:JobLogServiceImpl</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/4/1 13:29
 * @Version 1.0.0
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class JobLogServiceImpl extends ServiceImpl<JobLogMapper, JobLog> implements JobLogService {






    @Override
    @Transactional
    public void saveJob(JobLog jobLog) {
        this.save(jobLog);
    }

    @Override
    public IPage<JobLog> findJobLogs(QueryRequest request, JobLog jobLog) {
        LambdaQueryWrapper<JobLog> queryWrapper = new LambdaQueryWrapper<>();

        if (StringUtils.isNotBlank(jobLog.getBeanName())) {
            queryWrapper.like(JobLog::getBeanName, jobLog.getBeanName());
        }
        if (StringUtils.isNotBlank(jobLog.getMethodName())) {
            queryWrapper.like(JobLog::getMethodName, jobLog.getMethodName());
        }
        if (StringUtils.isNotBlank(jobLog.getStatus())) {
            queryWrapper.like(JobLog::getStatus, jobLog.getStatus());
        }

        Page<JobLog> page = new Page<>(request.getPageNum(), request.getPageSize());
        SortUtil.handlePageSort(request, page, "createTime", EircConstant.ORDER_DESC, true);
        return this.page(page, queryWrapper);
    }

    @Override
    @Transactional
    public void jobLogsDelete(String[] jobLogIds) {
        List<String> list = Arrays.asList(jobLogIds);
        this.baseMapper.deleteBatchIds(list);
    }
}
