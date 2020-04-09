package com.ccc.eirc.park.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ccc.eirc.park.domain.Park;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <a>Title:ParkMapper</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/30 19:37
 * @Version 1.0.0
 */
@Mapper
@Repository
public interface ParkMapper extends BaseMapper<Park> {

    Park findByName(String username);
}
