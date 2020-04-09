package com.ccc.eirc.park.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccc.eirc.commons.domain.EircConstant;
import com.ccc.eirc.commons.domain.QueryRequest;
import com.ccc.eirc.commons.utils.SortUtil;
import com.ccc.eirc.park.domain.Park;
import com.ccc.eirc.park.mapper.ParkMapper;
import com.ccc.eirc.park.service.ParkService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <a>Title:ParkServiceImpl</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/30 19:38
 * @Version 1.0.0
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ParkServiceImpl extends ServiceImpl<ParkMapper, Park> implements ParkService {


    /**
     * 车位信息 并分页
     *
     * @param request request request
     * @param park    park park
     * @return 结果
     */
    @Override
    public IPage<Park> findParkDetails(QueryRequest request, Park park) {
        LambdaQueryWrapper<Park> queryWrapper = new LambdaQueryWrapper<>();

        if (StringUtils.isNotBlank(park.getUsername())) {
            queryWrapper.like(Park::getUsername, park.getUsername());
        }
        if (StringUtils.isNotBlank(park.getStatus())) {
            queryWrapper.like(Park::getStatus, park.getStatus());
        }
        if (StringUtils.isNotBlank(park.getMobile())) {
            queryWrapper.like(Park::getMobile, park.getMobile());
        }
        if (StringUtils.isNotBlank(park.getCreateTimeFrom()) && StringUtils.isNotBlank(park.getCreateTimeTo())) {
            queryWrapper.ge(Park::getCreateTime, park.getCreateTimeFrom()).le(Park::getCreateTime, park.getCreateTimeTo());
        }
        queryWrapper.eq(Park::getStatus, Park.STATUS_AVAILABLE);
        queryWrapper.eq(Park::getAvailable, Park.AVAILABLE_SHOW);
        Page<Park> page = new Page<>(request.getPageNum(), request.getPageNum());
        SortUtil.handlePageSort(request, page, "createTime", EircConstant.ORDER_DESC, false);

        return this.page(page, queryWrapper);
    }


    /**
     * 我的车位页面
     *
     * @param request request
     * @param park    park
     * @param userId  userId
     * @return
     */
    @Override
    public IPage<Park> findUserParkDetails(QueryRequest request, Park park, Long userId) {
        LambdaQueryWrapper<Park> queryWrapper = new LambdaQueryWrapper<Park>();

        if (StringUtils.isNotBlank(park.getUsername())) {
            queryWrapper.like(Park::getUsername, park.getUsername());
        }
        if (StringUtils.isNotBlank(park.getPlace())) {
            queryWrapper.like(Park::getPlace, park.getPlace());
        }
        if (StringUtils.isNotBlank(park.getStatus())) {
            queryWrapper.like(Park::getStatus, park.getStatus());
        }
        if (StringUtils.isNotBlank(park.getAvailable())) {
            queryWrapper.like(Park::getAvailable, park.getAvailable());
        }
        if (StringUtils.isNotBlank(park.getCreateTimeFrom()) && StringUtils.isNotBlank(park.getCreateTimeTo())) {
            queryWrapper.ge(Park::getCreateTime, park.getCreateTimeFrom())
                    .le(Park::getCreateTime, park.getCreateTimeTo());
        }
        queryWrapper.eq(Park::getOwner, userId);

        Page<Park> page = new Page<>(request.getPageNum(), request.getPageSize());
        SortUtil.handlePageSort(request, page, "createTime", EircConstant.ORDER_DESC, false);

        return this.page(page, queryWrapper);
    }


    /**
     * 审核页面
     *
     * @param request request
     * @param park    park
     * @return
     */
    @Override
    public IPage<Park> findParkAvailable(QueryRequest request, Park park) {
        LambdaQueryWrapper<Park> queryWrapper = new LambdaQueryWrapper<>();

        if (StringUtils.isNotBlank(park.getUsername())) {
            queryWrapper.like(Park::getUsername, park.getUsername());
        }
        if (StringUtils.isNotBlank(park.getPlace())) {
            queryWrapper.like(Park::getPlace, park.getPlace());
        }
        if (StringUtils.isNotBlank(park.getStatus())) {
            queryWrapper.like(Park::getStatus, park.getStatus());
        }
        if (StringUtils.isNotBlank(park.getAvailable())) {
            queryWrapper.like(Park::getAvailable, park.getAvailable());
        }
        if (StringUtils.isNotBlank(park.getCreateTimeFrom()) && StringUtils.isNotBlank(park.getCreateTimeTo())) {
            queryWrapper.ge(Park::getCreateTime, park.getCreateTimeFrom())
                    .le(Park::getCreateTime, park.getCreateTimeTo());
        }
        queryWrapper.eq(Park::getReview, Park.ALWAYS_SHOW);
        Page<Park> page = new Page<>(request.getPageNum(), request.getPageSize());
        SortUtil.handlePageSort(request, page, "createTime", EircConstant.ORDER_DESC, false);

        return this.page(page, queryWrapper);
    }

    /**
     * 新增
     *
     * @param park 对象
     */
    @Override
    @Transactional
    public void createPark(Park park, Long userId) {
        park.setCreateTime(new Date());
//        设置拥有者
        park.setOwner(userId);
//        设置初始预定状态
        park.setStatus(Park.STATUS_AVAILABLE);
//        设置初始审核状态
        park.setAvailable(Park.AVAILABLE_HIDEN);
//        设置初始显示状态
        park.setReview(Park.ALWAYS_SHOW);
        save(park);
    }

    /**
     * 删除
     *
     * @param parkIds 对象
     */
    @Override
    @Transactional
    public void parkDelete(String[] parkIds) {
        List<String> list = Arrays.asList(parkIds);
        this.baseMapper.deleteBatchIds(list);
    }

    @Override
    @Transactional
    public void Update(Park park) {
        park.setModifyTime(new Date());
        updateById(park);
    }

    /**
     * 修改信息
     *
     * @param park 对象
     */
    @Override
    @Transactional
    public void parkUpdate(Park park) {
/*        park.setOwner(userId);*/
        park.setModifyTime(new Date());
        updateById(park);
    }

    /**
     * 修改回显信息
     *
     * @param username
     * @return
     */
    @Override
    public Park findByName(String username) {
        return this.baseMapper.findByName(username);
    }

}



