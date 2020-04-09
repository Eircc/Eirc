package com.ccc.eirc.job.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ccc.eirc.job.domain.Job;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <a>Title:JobMapper</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/30 16:09
 * @Version 1.0.0
 */
@Mapper
@Repository
public interface JobMapper extends BaseMapper<Job> {
    /**
     * 定时任务
     *
     * @return
     */
    List<Job> queryList();
}
