package com.ccc.eirc.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ccc.eirc.system.domain.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <a>Title:UserRoleMapper</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/24 20:25
 * @Version 1.0.0
 */
@Mapper
@Repository
public interface UserRoleMapper extends BaseMapper<UserRole> {
}
