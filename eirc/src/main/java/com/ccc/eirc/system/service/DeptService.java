package com.ccc.eirc.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ccc.eirc.commons.domain.DeptTree;
import com.ccc.eirc.commons.domain.QueryRequest;
import com.ccc.eirc.system.domain.Dept;

import java.util.List;

/**
 * <a>Title:DeptService</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/27 20:51
 * @Version 1.0.0
 */
public interface DeptService extends IService<Dept> {
    /**
     * 获取部门树
     *
     * @return
     */
    List<DeptTree<Dept>> findDepts();

    /**
     * 导出Excl
     *
     * @param dept    对象
     * @param request 参数
     * @return
     */
    List<Dept> findDepts(Dept dept, QueryRequest request);

    /**
     * 根据部门获取部门树
     *
     * @param dept 对象
     * @return
     */
    List<DeptTree<Dept>> findDepts(Dept dept);

    /**
     * 新增部门
     *
     * @param dept 对象
     */
    void createDept(Dept dept);

    /**
     * 删除部门
     *
     * @param deptIds 对象
     */
    void deleteDept(String[] deptIds);

    /**
     * 修改部门信息
     *
     * @param dept 对象
     */
    void updateDept(Dept dept);

}
