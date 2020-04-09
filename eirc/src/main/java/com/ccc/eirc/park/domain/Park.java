package com.ccc.eirc.park.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.ccc.eirc.commons.converter.TimeConverter;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <a>Title:Park</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/30 19:15
 * @Version 1.0.0
 */
@Data
@TableName("t_park")
@Excel("车位信息表")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class
Park implements Serializable {

    private static final long serialVersionUID = -8010222036196949581L;

    /**
     * 未必预定 显示
     */
    public static final String STATUS_AVAILABLE = "1";
    /**
     * 被预定 不显示
     */
    public static final String STATUS_ORDER = "0";


    /**
     * 审核通过 可以显示
     */
    public static final String AVAILABLE_SHOW = "1";

    /**
     * 审核不通过 不显示
     */
    public static final String AVAILABLE_HIDEN = "0";

    /**
     * 审核展示
     */
    public static final String ALWAYS_SHOW = "1";


    /**
     * 车位ID
     */
    @TableId(value = "ID", type = IdType.AUTO)
    @ExcelField(value = "车位号")
    private long id;

    /**
     * 用户名
     */
    @TableField("USERNAME")
    @ExcelField(value = "用户名")
    private String username;

    /**
     * 描述
     */
    @TableField("DESCRIPTION")
    @ExcelField(value = "描述")
    private String description;

    /**
     * 车位状态 1 占用  0可用
     */
    @TableField("STATUS")
    @ExcelField(value = "车位状态")
    private String status;

    /**
     * 审核状态 1 通过 0 审核中 -1失败
     */
    @TableField("AVAILABLE")
    @ExcelField(value = "审核状态")
    private String available;

    /**
     * 价格
     */
    @TableField("PRICE")
    @ExcelField(value = "价格")
    private Double price;

    /**
     * 创建时间
     */
    @TableField("CREATETIME")
    @ExcelField(value = "创建时间", writeConverter = TimeConverter.class)
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField("MODIFYTIME")
    @ExcelField(value = "修改时间", writeConverter = TimeConverter.class)
    private Date modifyTime;


    /**
     * 时间 起
     */
    @TableField(exist = false)
    private String createTimeFrom;

    /**
     * 时间 止
     */
    @TableField(exist = false)
    private String createTimeTo;

    /**
     * 车牌
     */
    @TableField("SPAN")
    @ExcelField(value = "车牌")
    private String span;

    /**
     * 地点
     */
    @TableField("PLACE")
    @ExcelField(value = "地点")
    private String place;

    @TableField("MOBILE")
    @ExcelField(value = "联系方式")
    private String mobile;

    /**
     * 审核信息
     */
    @TableField("REVIEW")
    @ExcelField(value = "show")
    private String review;

    /**
     * 审核不通过原因
     */
    @TableField("WHY")
    @ExcelField(value = "原因")
    private String why;

/*    updateStrategy = FieldStrategy.IGNORED*/
    @TableField("OWNER")
    @ExcelField(value = "车位主人")
    private long owner;
}
