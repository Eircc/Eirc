package com.ccc.eirc.commons.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccc.eirc.commons.domain.EircConstant;
import com.ccc.eirc.commons.domain.QueryRequest;
import org.apache.commons.lang3.StringUtils;

/**
 * <a>Title:SortUtil</a>
 * <a>Author：<a>
 * <a>Description：<a>
 * <p>
 * 处理排序工具类
 *
 * @Author ccc
 * @Date 2020/3/27 14:04
 * @Version 1.0.0
 */
public class SortUtil {


    /**
     * 处理排序（分页情况下） for mybatis-plus
     *
     * @param request           QueryRequest
     * @param page              Page
     * @param defaultSort       默认排序的字段
     * @param defaultOrder      默认排序规则
     * @param camelToUnderscore 是否开启驼峰转下划线
     */
    public static void handlePageSort(QueryRequest request, Page<?> page, String defaultSort, String defaultOrder, boolean camelToUnderscore) {
//        设置当前页面
        page.setCurrent(request.getPageNum());
//        设置当前页面数据量
        page.setSize(request.getPageSize());
//        设置默认排序字段
        String sortField = request.getFiled();
//        如果开启驼峰转下划线
        if (camelToUnderscore) {
            sortField = EircUtil.camelToUnderscore(sortField);
            defaultSort = EircUtil.camelToUnderscore(defaultSort);
        }
//        如果有排序字段和默认排序方式，且排序字段不为空且排序方式也不为空
        if (StringUtils.isNotBlank(request.getFiled())
                && StringUtils.isNotBlank(request.getOrder())
                && !StringUtils.equalsIgnoreCase(request.getFiled(), "null")
                && !StringUtils.equalsIgnoreCase(request.getOrder(), "null")) {
            if (StringUtils.equals(request.getOrder(), EircConstant.ORDER_DESC))
                page.addOrder(OrderItem.desc(sortField));
            else
                page.addOrder(OrderItem.asc(sortField));
        } else {
            if (StringUtils.equals(defaultOrder, EircConstant.ORDER_DESC))
                page.addOrder(OrderItem.desc(defaultSort));
            else
                page.addOrder(OrderItem.asc(defaultSort));
        }
    }

    /**
     * 处理排序             for mybatis-plus
     *
     * @param request QueryRequest
     * @param page    page
     */
    public static void handlePageSort(QueryRequest request, Page<?> page) {
        handlePageSort(request, page, null, null, false);
    }

    /**
     * 处理排序 for mybatis-plus
     *
     * @param request           QueryRequest
     * @param page              page
     * @param camelToUnderscore 是否开启驼峰转下划线
     */
    public static void handlePageSort(QueryRequest request, Page<?> page, boolean camelToUnderscore) {
        handlePageSort(request, page, null, null, camelToUnderscore);
    }

    /**
     * 处理排序 for mybatis-plus
     *
     * @param request           QueryRequest
     * @param wrapper           wrapper
     * @param defaultSort       默认排序的字段
     * @param defaultOrder      默认排序规则
     * @param camelToUnderscore 是否开启驼峰转下划线
     */
    public static void handleWrapperSort(QueryRequest request, QueryWrapper<?> wrapper, String defaultSort, String defaultOrder, boolean camelToUnderscore) {
//        获取排序字段
        String sortFiled = request.getFiled();
//        如果开启驼峰转下划线
        if (camelToUnderscore) {
            sortFiled = EircUtil.camelToUnderscore(sortFiled);
            defaultSort = EircUtil.camelToUnderscore(defaultSort);
        }
        if (StringUtils.isNotBlank(request.getFiled())
                && StringUtils.isNotBlank(request.getOrder())
                && !StringUtils.equalsIgnoreCase(request.getFiled(), "null")
                && !StringUtils.equalsIgnoreCase(request.getOrder(), "null")) {
            if (StringUtils.equals(request.getOrder(), EircConstant.ORDER_DESC))
                wrapper.orderByDesc(sortFiled);
            else
                wrapper.orderByAsc(sortFiled);
        } else {
            if (StringUtils.equals(defaultOrder, EircConstant.ORDER_DESC))
                wrapper.orderByDesc(defaultSort);
            else
                wrapper.orderByAsc(defaultSort);
        }
    }

    /**
     * 处理排序             for mybatis-plus
     *
     * @param request QueryRequest
     * @param wrapper wrapper
     */
    public static void handleWrapperSort(QueryRequest request, QueryWrapper<?> wrapper) {
        handleWrapperSort(request, wrapper, null, null, false);
    }

    /**
     * 处理排序                 for mybatis-plus
     *
     * @param request           QueryRequest
     * @param wrapper           QueryWrapper
     * @param camelToUnderscore 是否开启驼峰转下划线
     */
    public static void handleWrapperSort(QueryRequest request, QueryWrapper<?> wrapper, boolean camelToUnderscore) {
        handleWrapperSort(request, wrapper, null, null, camelToUnderscore);
    }


}
