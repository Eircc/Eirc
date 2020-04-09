package com.ccc.eirc.park.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ccc.eirc.commons.domain.QueryRequest;
import com.ccc.eirc.park.domain.Order;

/**
 * <a>Title:OrderService</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/4/2 16:42
 * @Version 1.0.0
 */
public interface OrderService extends IService<Order> {

    /**
     * 预定车位
     * @param
     * @param userId userId
     */
    void createOrder(Long id, Long userId);

    /**
     * 订单详情
     * @param request request
     * @param order park
     * @param userId userId
     * @return
     */
    IPage<Order> findUserOrderDetails(QueryRequest request, Order order, Long userId);
}
