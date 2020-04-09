package com.ccc.eirc.park.controller;

import com.ccc.eirc.commons.controller.BaseController;
import com.ccc.eirc.commons.domain.EircConstant;
import com.ccc.eirc.commons.utils.EircUtil;
import com.ccc.eirc.park.domain.Park;
import com.ccc.eirc.park.service.ParkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * <a>Title:ViewController</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/30 19:39
 * @Version 1.0.0
 */
@Controller("parkView")
public class ViewController extends BaseController {

    @Autowired
    private ParkService parkService;

    /**
     * 车位信息
     *
     * @return
     */
    @GetMapping(EircConstant.VIEW_PREFIX + "park/detail")
    public String parkDetail() {
        return EircUtil.view("park/park");
    }

    /**
     * 车位详细信息
     *
     * @param username 车主名
     * @param model    model
     * @return
     */
    @GetMapping(EircConstant.VIEW_PREFIX + "park/detail/show/{username}")
    public String parkUserDetail(@PathVariable String username, Model model) {
        resolveParkModel(username, model);
        return EircUtil.view("park/parkDetail");
    }

    /**
     * 新增车位
     */
    @GetMapping(EircConstant.VIEW_PREFIX + "park/detail/add")
    public String parkAdd() {
        return EircUtil.view("park/parkAdd");
    }

    /**
     * 修改
     *
     * @return
     */
    @GetMapping(EircConstant.VIEW_PREFIX + "park/detail/update/{username}")
    public String parkUpdate(@PathVariable String username, Model model) {
        resolveParkModel(username, model);
        return EircUtil.view("park/parkUpdate");
    }

    /**
     * 车位信息审核
     *
     * @return
     */
    @GetMapping(EircConstant.VIEW_PREFIX + "park/available")
    public String parkAvailable() {
        return EircUtil.view("review/available");
    }

    /**
     * 审核查看详情
     *
     * @param username username
     * @param model    model
     * @return
     */
    @GetMapping(EircConstant.VIEW_PREFIX + "park/available/review/{username}")
    public String parkAvailableReview(@PathVariable String username, Model model) {
        resolveParkModel(username, model);
        return EircUtil.view("review/availableUpdate");
    }

    /**
     * 用户车位（我的车位）
     *
     * @return
     */
    @GetMapping(EircConstant.VIEW_PREFIX + "park/mypark")
    public String parkInfo() {
        return EircUtil.view("my/myPark");
    }

    /**
     * 用户对车位修改
     *
     * @param username
     * @param model
     * @return
     */
    @GetMapping(EircConstant.VIEW_PREFIX + "park/user/update/{username}")
    public String parkUserUpdate(@PathVariable String username, Model model) {
        resolveParkModel(username, model);
        return EircUtil.view("my/myParkUpdate");
    }

    /**
     * 我的订单
     * @return
     */
    @GetMapping(EircConstant.VIEW_PREFIX + "park/order")
    public String myParkOrder() {
        return EircUtil.view("order/order");
    }

    private void resolveParkModel(String username, Model model) {
        Park park = parkService.findByName(username);
        model.addAttribute("park", park);
    }
}
