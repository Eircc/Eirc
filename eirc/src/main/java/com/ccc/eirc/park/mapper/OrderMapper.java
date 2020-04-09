package com.ccc.eirc.park.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ccc.eirc.park.domain.Order;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <a>Title:OrderMapper</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/4/2 16:41
 * @Version 1.0.0
 */
@Mapper
@Repository
public interface OrderMapper extends BaseMapper<Order> {
}
