package com.ccc.eirc.others.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccc.eirc.commons.domain.QueryRequest;
import com.ccc.eirc.commons.properties.EircProperties;
import com.ccc.eirc.others.domain.Eximport;
import com.ccc.eirc.others.mapper.EximportMapper;
import com.ccc.eirc.others.service.EximportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <a>Title:EximportServiceImpl</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/29 13:48
 * @Version 1.0.0
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS,readOnly = true,rollbackFor = Exception.class)
public class EximportServiceImpl extends ServiceImpl<EximportMapper, Eximport> implements EximportService {

    @Autowired
    private EircProperties eircProperties;

    /**
     * 查询
     * @param request QueryRequest
     * @param eximport Eximport
     * @return
     */
    @Override
    public IPage<Eximport> findEximports(QueryRequest request, Eximport eximport) {
        Page<Eximport> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.page(page);
    }

    /**
     * 批量插入
     * @param list List<Eximport>
     */
    @Override
    @Transactional
    public void batchInsert(List<Eximport> list) {
        saveBatch(list,eircProperties.getMaxBatchInsertNum());
    }
}
