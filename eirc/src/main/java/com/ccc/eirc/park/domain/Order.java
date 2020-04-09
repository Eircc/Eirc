package com.ccc.eirc.park.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <a>Title:Order</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/4/2 16:38
 * @Version 1.0.0
 */
@Data
@TableName("t_order")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class Order implements Serializable {


    private static final long serialVersionUID = 8703440085617264015L;

    @TableField("USER_ID")
    private long userId;

    @TableField("PARK_ID")
    private long parkId;

    @TableField("CREATETIME")
    private Date createTime;

    @TableField("MODIFYTIME")
    private Date modifyTime;

    @TableField("PRICE")
    private Double price;

    @TableField("USERNAME")
    private String username;

    @TableField("PLACE")
    private String place;

    @TableField("MOBILE")
    private String mobile;

    @TableField(exist = false)
    private String createTimeFrom;

    @TableField(exist = false)
    private String createTimeTo;

}
