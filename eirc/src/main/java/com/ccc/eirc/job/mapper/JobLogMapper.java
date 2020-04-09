package com.ccc.eirc.job.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ccc.eirc.job.domain.JobLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <a>Title:JobLogMapper</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/4/1 13:30
 * @Version 1.0.0
 */
@Mapper
@Repository
public interface JobLogMapper extends BaseMapper<JobLog> {

}
