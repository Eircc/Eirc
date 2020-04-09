package com.ccc.eirc.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ccc.eirc.system.domain.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <a>Title:MenuMapper</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/24 16:16
 * @Version 1.0.0
 */
@Mapper
@Repository
public interface MenuMapper extends BaseMapper<Menu> {
    /**
     * 查找用户权限集合
     *
     * @param username 用户名
     * @return 用户权限集合
     */
    List<Menu> findUserPermissions(String username);

    /**
     * 获取用户菜单集合
     *
     * @param username 用户名
     * @return 用户菜单集合
     */
    List<Menu> findUserMenus(String username);

}
