package com.ccc.eirc.park.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccc.eirc.commons.domain.EircConstant;
import com.ccc.eirc.commons.domain.QueryRequest;
import com.ccc.eirc.commons.utils.SortUtil;
import com.ccc.eirc.park.domain.Order;
import com.ccc.eirc.park.domain.Park;
import com.ccc.eirc.park.mapper.OrderMapper;
import com.ccc.eirc.park.service.OrderService;
import com.ccc.eirc.park.service.ParkService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <a>Title:OrderServiceImpl</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/4/2 16:42
 * @Version 1.0.0
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {


    @Autowired
    private ParkService parkService;

    @Override
    @Transactional
    public void createOrder(Long id, Long userId) {
        Order order = new Order();
        Park park = findPark(id);
        order.setUserId(userId);
        order.setParkId(id);
        order.setCreateTime(new Date());
        order.setModifyTime(park.getModifyTime());
        order.setMobile(park.getMobile());
        order.setPlace(park.getPlace());
        order.setPrice(park.getPrice());
        order.setUsername(park.getUsername());
        save(order);

        park.setStatus(Park.STATUS_ORDER);
        parkService.Update(park);
    }

    @Override
    public IPage<Order> findUserOrderDetails(QueryRequest request, Order order, Long userId) {
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();

        if (StringUtils.isNotBlank(order.getUsername())) {
            queryWrapper.like(Order::getUsername, order.getUsername());
        }
        if (StringUtils.isNotBlank(order.getPlace())) {
            queryWrapper.like(Order::getPlace, order.getPlace());
        }
        if (StringUtils.isNotBlank(order.getMobile())) {
            queryWrapper.like(Order::getMobile, order.getMobile());
        }
        if (StringUtils.isNotBlank(order.getCreateTimeFrom())&& StringUtils.isNotBlank(order.getCreateTimeTo())){
            queryWrapper.ge(Order::getCreateTime,order.getCreateTimeFrom())
                        .le(Order::getCreateTime,order.getCreateTimeTo());
        }

        queryWrapper.eq(Order::getUserId, userId);
        Page<Order> page = new Page<>(request.getPageNum(), request.getPageSize());
        SortUtil.handlePageSort(request, page, "createTime", EircConstant.ORDER_DESC, false);

        return this.page(page, queryWrapper);
    }

    public Park findPark(long id) {
        LambdaQueryWrapper<Park> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Park::getId, id);
        return parkService.getOne(queryWrapper);
    }

}
