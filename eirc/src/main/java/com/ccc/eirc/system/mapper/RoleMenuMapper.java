package com.ccc.eirc.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ccc.eirc.system.domain.RoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <a>Title:RoleMenuMapper</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/26 19:26
 * @Version 1.0.0
 */
@Mapper
@Repository
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {
}
