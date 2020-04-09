package com.ccc.eirc.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ccc.eirc.system.domain.Dept;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <a>Title:DeptMapper</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/27 20:51
 * @Version 1.0.0
 */
@Mapper
@Repository
public interface DeptMapper extends BaseMapper<Dept> {
}
