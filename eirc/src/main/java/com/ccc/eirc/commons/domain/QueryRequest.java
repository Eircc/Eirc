package com.ccc.eirc.commons.domain;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * <a>Title:QueryRequest</a>
 * <a>Author：<a>
 * <a>Description：<a>
 * <p>
 * 分页参数 排序参数
 *
 * @Author ccc
 * @Date 2020/3/27 13:52
 * @Version 1.0.0
 */
@Data
@ToString
public class QueryRequest implements Serializable {

    private static final long serialVersionUID = 1497720572629309388L;
    // 当前页面数据量
    private int pageSize = 10;
    // 当前页码
    private int pageNum = 1;
    // 排序字段
    private String filed;
    // 排序规则，asc升序，desc降序
    private String order;
}
