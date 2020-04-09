package com.ccc.eirc.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wuwenze.poi.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <a>Title:UserRole</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/24 20:22
 * @Version 1.0.0
 */
@Data
@TableName("t_user_role")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class UserRole implements Serializable {

    private static final long serialVersionUID = 5843394993622216814L;
    /**
     * 用户ID
     */
    @TableField("USER_ID")
    private Long userId;

    /**
     * 角色ID
     */
    @TableField("ROLE_ID")
    private Long roleId;
}
