package com.ccc.eirc.park.controller;

import com.ccc.eirc.commons.annotation.ControllerEndpoint;
import com.ccc.eirc.commons.controller.BaseController;
import com.ccc.eirc.commons.domain.EircResopnse;
import com.ccc.eirc.park.service.OrderService;
import com.ccc.eirc.system.domain.User;
import com.ccc.eirc.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

/**
 * <a>Title:OrderController</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/4/2 16:37
 * @Version 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("order")
public class OrderController extends BaseController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;


    @GetMapping("this/{id}")
    @ControllerEndpoint(operation = "预定车辆",exceptionMessage = "预定失败")
    public EircResopnse thisOrder(@NotBlank(message = "{required}") @PathVariable long id){
        User user = super.getCurrentUser();
        User currentUserDetail = userService.findByName(user.getUsername());
        Long userId = currentUserDetail.getUserId();
        this.orderService.createOrder(id,userId);
        return new EircResopnse().success();
    }




}
