package com.ccc.eirc.monitor.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccc.eirc.commons.domain.EircConstant;
import com.ccc.eirc.commons.domain.QueryRequest;
import com.ccc.eirc.commons.utils.AddressUtil;
import com.ccc.eirc.commons.utils.HttpContextUtil;
import com.ccc.eirc.commons.utils.IpUtil;
import com.ccc.eirc.commons.utils.SortUtil;
import com.ccc.eirc.monitor.domain.LoginLog;
import com.ccc.eirc.monitor.mapper.LoginLogMapper;
import com.ccc.eirc.monitor.service.LoginLogService;
import com.ccc.eirc.system.domain.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <a>Title:LoginServiceImpl</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/23 17:12
 * @Version 1.0.0
 */
@Service("loginLogService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements LoginLogService {

    /**
     * 保存用户登录日志
     *
     * @param loginLog 对象
     */
@Override
@Transactional
public void saveLoginLog(LoginLog loginLog) {
    HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
    String ip = IpUtil.getIpAddress(request);
    loginLog.setIp(ip);
    loginLog.setLoginTime(new Date());
    loginLog.setLocation(AddressUtil.getCityInfo(ip));
    this.save(loginLog);
}

    /**
     * 查询当天访问次数
     *
     * @return result
     */
    @Override
    public Long findTotalVisitCount() {
        return this.baseMapper.findTotalVisitCount();
    }

    /**
     * 查询最近七天访问情次数
     *
     * @return result
     */
    @Override
    public Long findTodayVisitCount() {
        return this.baseMapper.findTodayVisitCount();
    }

    /**
     * 查询访问 ip
     *
     * @return result
     */
    @Override
    public Long findTodayIp() {
        return this.baseMapper.findTodayIp();
    }

    /**
     * 查询最近七天访问次数
     *
     * @param user 对象
     * @return result
     */
    @Override
    public List<Map<String, Object>> findLastSevenDaysVisitCount(User user) {
        return this.baseMapper.findLastSevenDaysVisitCount(user);
    }

    /**
     * 查询登陆日志信息 分页
     *
     * @param loginLog 对象
     * @param request  参数
     * @return
     */
    @Override
    public IPage<LoginLog> findLoginLogs(LoginLog loginLog, QueryRequest request) {
        QueryWrapper<LoginLog> queryWrapper = new QueryWrapper<>();

        if (StringUtils.isNotBlank(loginLog.getUsername())) {
            queryWrapper.lambda().like(LoginLog::getUsername, loginLog.getUsername().toLowerCase());
        }
        if (StringUtils.isNotBlank(loginLog.getLoginTimeFrom()) && StringUtils.isNotBlank(loginLog.getLoginTimeTo())) {
            queryWrapper.lambda().ge(LoginLog::getLoginTime, loginLog.getLoginTimeFrom())
                                 .le(LoginLog::getLoginTime, loginLog.getLoginTimeTo());
        }

        Page<LoginLog> page = new Page<>(request.getPageNum(),request.getPageSize());
        SortUtil.handlePageSort(request, page, "loginTime", EircConstant.ORDER_DESC, true);

        return this.page(page, queryWrapper);
    }

    /**
     * 删除登陆日志
     *
     * @param ids 对象
     */
    @Override
    public void deleteLoginLogs(String[] ids) {
        List<String> list = Arrays.asList(ids);
        this.baseMapper.deleteBatchIds(list);
    }
}
