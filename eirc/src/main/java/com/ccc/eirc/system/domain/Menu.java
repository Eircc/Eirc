package com.ccc.eirc.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ccc.eirc.commons.converter.TimeConverter;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * <a>Title:Menu</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/24 16:11
 * @Version 1.0.0
 */
@Data
@Excel("菜单信息表")
@TableName("t_menu")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Menu implements Serializable {

    private static final long serialVersionUID = 7509797740436848205L;

    // 菜单
    public static final String TYPE_MENU = "0";
    // 按钮
    public static final String TYPE_BUTTON = "1";

    public static final Long TOP_NODE = 0L;

    /**
     * 菜单/按钮ID
     */
    @TableId(value = "MENU_ID", type = IdType.AUTO)
    private Long menuId;

    /**
     * 上级菜单ID
     */
    @TableField("PARENT_ID")
    private Long parentId;

    /**
     * 菜单/按钮名称
     */
    @TableField("MENU_NAME")
    @NotBlank(message = "{required}")
    @Size(max = 10, message = "{noMoreThan}")
    @ExcelField(value = "名称")
    private String menuName;

    /**
     * 菜单URL
     */
    @TableField("URL")
    @Size(max = 50, message = "{noMoreThan}")
    @ExcelField(value = "URL")
    private String url;

    /**
     * 权限标识
     */
    @TableField("PERMS")
    @Size(max = 50, message = "{noMoreThan}")
    @ExcelField(value = "权限")
    private String perms;

    /**
     * 图标
     */
    @TableField("ICON")
    @Size(max = 50, message = "{noMoreThan}")
    @ExcelField(value = "图标")
    private String icon;

    /**
     * 类型 0菜单 1按钮
     */
    @TableField("TYPE")
    @NotBlank(message = "{required}")
    @ExcelField(value = "类型", writeConverterExp = "0=按钮,1=菜单")
    private String type;

    /**
     * 排序
     */
    @TableField("ORDER_NUM")
    private Long orderNum;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    @ExcelField(value = "创建时间", writeConverter = TimeConverter.class)
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField("MODIFY_TIME")
    @ExcelField(value = "修改时间", writeConverter = TimeConverter.class)
    private Date modifyTime;



}
