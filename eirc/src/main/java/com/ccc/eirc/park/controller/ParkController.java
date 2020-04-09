package com.ccc.eirc.park.controller;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.ccc.eirc.commons.annotation.ControllerEndpoint;
import com.ccc.eirc.commons.controller.BaseController;
import com.ccc.eirc.commons.domain.EircResopnse;
import com.ccc.eirc.commons.domain.QueryRequest;
import com.ccc.eirc.park.domain.Order;
import com.ccc.eirc.park.domain.Park;
import com.ccc.eirc.park.service.OrderService;
import com.ccc.eirc.park.service.ParkService;
import com.ccc.eirc.system.domain.User;
import com.ccc.eirc.system.service.UserService;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * <a>Title:ParkController</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/30 19:47
 * @Version 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("park")
public class ParkController extends BaseController {

    @Autowired
    private ParkService parkService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    /**
     * 车位信息分页
     *
     * @param park    park
     * @param request request
     * @return 结果
     */
    @GetMapping("list")
    @RequiresPermissions("park:view")
    @ControllerEndpoint(operation = "获取车位信息", exceptionMessage = "获取信息失败")
    public EircResopnse parkList(Park park, QueryRequest request) {
        Map<String, Object> dataTable = getDataTable(this.parkService.findParkDetails(request, park));
        return new EircResopnse().success().data(dataTable);
    }

    @PostMapping
    @RequiresPermissions("park:add")
    @ControllerEndpoint(operation = "新增车位", exceptionMessage = "新增车位失败")
    public EircResopnse parkAdd(@Valid Park park) {
        User currentUser = super.getCurrentUser();
        User user = userService.findByName(currentUser.getUsername());
        Long userId = user.getUserId();
        this.parkService.createPark(park, userId);
        return new EircResopnse().success();
    }

    @GetMapping("delete/{ids}")
    @RequiresPermissions("park:delete")
    @ControllerEndpoint(operation = "删除车位", exceptionMessage = "删除车位失败")
    public EircResopnse parkDelete(@NotBlank(message = "{required}") @PathVariable String ids) {
        String[] parkIds = ids.split(StringPool.COMMA);
        this.parkService.parkDelete(parkIds);
        return new EircResopnse().success();
    }

    @PostMapping("update")
    @RequiresPermissions("park:update")
    @ControllerEndpoint(operation = "信息修改", exceptionMessage = "信息修改失败")
    public EircResopnse parkUpdate(@Valid Park park) {
        this.parkService.parkUpdate(park);
        return new EircResopnse().success();
    }


    @GetMapping("excel")
    @RequiresPermissions("park:export")
    @ControllerEndpoint(operation = "导出Excel失败")
    public void export(QueryRequest request, Park park, HttpServletResponse response) {
        List<Park> parks = this.parkService.findParkDetails(request, park).getRecords();
        ExcelKit.$Export(Park.class, response).downXlsx(parks, false);
    }

    /**
     * 审核页面
     *
     * @param park    park
     * @param request request
     * @return
     */
    @GetMapping("available")
    @RequiresPermissions("available:view")
    @ControllerEndpoint(operation = "审核列表", exceptionMessage = "获取审核信息失败")
    public EircResopnse parkAvailable(Park park, QueryRequest request) {
        Map<String, Object> dataTable = getDataTable(this.parkService.findParkAvailable(request, park));
        return new EircResopnse().success().data(dataTable);
    }

    @PostMapping("review")
    @RequiresPermissions("available:update")
    @ControllerEndpoint(operation = "审核车位信息", exceptionMessage = "车位信息审核失败")
    public EircResopnse parkReview(@Validated Park park) {
        this.parkService.parkUpdate(park);
        return new EircResopnse().success();
    }

    /**
     * 我的车位
     *
     * @param request request
     * @param park    park
     * @return
     */
    @GetMapping("my")
    @RequiresPermissions("my:view")
    @ControllerEndpoint(operation = "获取车位详情", exceptionMessage = "获取车位详情失败")
    public EircResopnse myParkDetail(QueryRequest request, Park park) {
        User user = super.getCurrentUser();
        User currentUserDetail = userService.findByName(user.getUsername());
        Long userId = currentUserDetail.getUserId();
        Map<String, Object> dataTable = getDataTable(parkService.findUserParkDetails(request, park, userId));
        return new EircResopnse().success().data(dataTable);
    }

    /**
     * 用户修改车位信息
     *
     * @param park park
     * @return
     */
    @PostMapping("userUpdate")
    @RequiresPermissions("my:update")
    @ControllerEndpoint(operation = "用户信息修改", exceptionMessage = "用户信息修改失败")
    public EircResopnse userParkUpdate(@Validated Park park) {
        this.parkService.parkUpdate(park);
        return new EircResopnse().success();
    }

    /**
     * 订单详情
     *
     * @param request request
     * @param order   park
     * @return
     */
    @GetMapping("order")
    @RequiresPermissions("my:order")
    @ControllerEndpoint(operation = "获取订单详情", exceptionMessage = "获取订单详情失败")
    public EircResopnse orderDetails(QueryRequest request, Order order) {
        User currentUser = super.getCurrentUser();
        User user = userService.findByName(currentUser.getUsername());
        Long userId = user.getUserId();
        Map<String, Object> dataTable = getDataTable(this.orderService.findUserOrderDetails(request, order, userId));
        return new EircResopnse().success().data(dataTable);
    }


}
