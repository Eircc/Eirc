package com.ccc.eirc.monitor.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ccc.eirc.monitor.domain.SystemLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <a>Title:LogMapper</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/28 16:55
 * @Version 1.0.0
 */
@Mapper
@Repository
public interface LogMapper extends BaseMapper<SystemLog> {
}
