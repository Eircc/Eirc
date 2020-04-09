package com.ccc.eirc.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <a>Title:RoleMenu</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/26 19:25
 * @Version 1.0.0
 */
@Data
@TableName("t_role_menu")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class RoleMenu implements Serializable {

    private static final long serialVersionUID = 7276517656278717162L;
    /**
     * 角色ID
     */
    @TableField("ROLE_ID")
    private Long roleId;

    /**
     * 菜单/按钮ID
     */
    @TableField("MENU_ID")
    private Long menuId;

}
