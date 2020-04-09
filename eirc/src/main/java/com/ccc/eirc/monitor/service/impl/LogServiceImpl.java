package com.ccc.eirc.monitor.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccc.eirc.commons.domain.EircConstant;
import com.ccc.eirc.commons.domain.QueryRequest;
import com.ccc.eirc.commons.utils.SortUtil;
import com.ccc.eirc.monitor.domain.SystemLog;
import com.ccc.eirc.monitor.mapper.LogMapper;
import com.ccc.eirc.monitor.service.LogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * <a>Title:LogServiceImpl</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/28 16:56
 * @Version 1.0.0
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class LogServiceImpl extends ServiceImpl<LogMapper, SystemLog> implements LogService {

    /**
     * 获取系统日志
     *
     * @param systemLog 对象
     * @param request   参数
     * @return
     */
    @Override
    @Transactional
    public IPage<SystemLog> findLogs(SystemLog systemLog, QueryRequest request) {
        QueryWrapper<SystemLog> queryWrapper = new QueryWrapper<>();

        if (StringUtils.isNotBlank(systemLog.getUsername())) {
            queryWrapper.lambda().eq(SystemLog::getUsername, systemLog.getUsername().toLowerCase());
        }
        if (StringUtils.isNotBlank(systemLog.getOperation())) {
            queryWrapper.lambda().eq(SystemLog::getOperation, systemLog.getOperation());
        }
        if (StringUtils.isNotBlank(systemLog.getLocation())) {
            queryWrapper.lambda().eq(SystemLog::getLocation, systemLog.getLocation());
        }
        if (StringUtils.isNotBlank(systemLog.getCreateTimeFrom()) && StringUtils.isNotBlank(systemLog.getCreateTimeTo())) {
            queryWrapper.lambda().eq(SystemLog::getCreateTime, systemLog.getCreateTime()).le(SystemLog::getCreateTime, systemLog.getCreateTime());
        }

        Page<SystemLog> page = new Page<>(request.getPageNum(), request.getPageSize());
        SortUtil.handlePageSort(request, page, "createTime", EircConstant.ORDER_DESC, true);

        return this.page(page, queryWrapper);
    }

    /**
     * 删除系统日志
     *
     * @param logIds 对象
     */
    @Override
    @Transactional
    public void deleteLogs(String[] logIds) {
        List<String> list = Arrays.asList(logIds);
        this.removeByIds(list);
    }
}
