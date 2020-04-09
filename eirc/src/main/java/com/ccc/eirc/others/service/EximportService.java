package com.ccc.eirc.others.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ccc.eirc.commons.domain.QueryRequest;
import com.ccc.eirc.others.domain.Eximport;

import java.util.List;

/**
 * <a>Title:EximportService</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/29 13:48
 * @Version 1.0.0
 */
public interface EximportService extends IService<Eximport> {

    /**
     * 查询 （分页）
     * @param request QueryRequest
     * @param eximport Eximport
     * @return IPage<Eximport>
     */
    IPage<Eximport> findEximports(QueryRequest request, Eximport eximport);

    /**
     * 批量插入
     * @param list List<Eximport>
     */
    void batchInsert(List<Eximport> list);
}
