package com.ccc.eirc.park.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ccc.eirc.commons.domain.QueryRequest;
import com.ccc.eirc.park.domain.Park;

/**
 * <a>Title:ParkService</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/30 19:38
 * @Version 1.0.0
 */
public interface ParkService extends IService<Park> {
    /**
     * 分页
     *
     * @param request request
     * @param park    park
     * @return
     */
    IPage<Park> findParkDetails(QueryRequest request, Park park);

    /**
     * 新增车位
     *
     * @param park 对象
     */
    void createPark(Park park, Long userId);

    /**
     * 删除
     *
     * @param parkIds 对象
     */
    void parkDelete(String[] parkIds);

    /**
     * 修改
     *
     * @param park 对象
     */
    void parkUpdate(Park park);



    /**
     * 查看详情获取车位信息
     *
     * @param username username
     * @return
     */
    Park findByName(String username);

    /**
     * 审核页面
     *
     * @param request request
     * @param park    park
     * @return
     */
    IPage<Park> findParkAvailable(QueryRequest request, Park park);

    /**
     * 我的车位页面
     *
     * @param request request
     * @param park    park
     * @param userId  userId
     * @return
     */
    IPage<Park> findUserParkDetails(QueryRequest request, Park park, Long userId);

    void Update(Park park);

}
