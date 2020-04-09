package com.ccc.eirc.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccc.eirc.commons.domain.DeptTree;
import com.ccc.eirc.commons.domain.EircConstant;
import com.ccc.eirc.commons.domain.QueryRequest;
import com.ccc.eirc.commons.utils.SortUtil;
import com.ccc.eirc.commons.utils.TreeUtil;
import com.ccc.eirc.system.domain.Dept;
import com.ccc.eirc.system.mapper.DeptMapper;
import com.ccc.eirc.system.service.DeptService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <a>Title:DeptServiceImpl</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/27 20:52
 * @Version 1.0.0
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {

    /**
     * 获取部门树
     *
     * @return
     */
    @Override
    @Transactional
    public List<DeptTree<Dept>> findDepts() {
        List<Dept> depts = this.baseMapper.selectList(new QueryWrapper<>());
        List<DeptTree<Dept>> trees = this.convertDepts(depts);
        return TreeUtil.buildDeptTree(trees);
    }

    /**
     * 根据部门获取部门树
     *
     * @param dept 对象
     * @return
     */
    @Override
    @Transactional
    public List<DeptTree<Dept>> findDepts(Dept dept) {
        QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(dept.getDeptName()))
            queryWrapper.lambda().eq(Dept::getDeptName, dept.getDeptName());
        queryWrapper.lambda().orderByAsc(Dept::getOrderNum);

        List<Dept> depts = this.baseMapper.selectList(queryWrapper);
        List<DeptTree<Dept>> trees = this.convertDepts(depts);
        return TreeUtil.buildDeptTree(trees);
    }

    /**
     * 导出Excel
     *
     * @param dept    对象
     * @param request 参数
     * @return
     */
    @Override
    @Transactional
    public List<Dept> findDepts(Dept dept, QueryRequest request) {
        QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();

        if (StringUtils.isNotBlank(dept.getDeptName()))
            queryWrapper.lambda().eq(Dept::getDeptName, dept.getDeptName());
        SortUtil.handleWrapperSort(request, queryWrapper, "orderNum", EircConstant.ORDER_ASC, true);
        return this.baseMapper.selectList(queryWrapper);
    }

    /**
     * 新增部门
     *
     * @param dept 对象
     */
    @Override
    @Transactional
    public void createDept(Dept dept) {
        Long parentId = dept.getParentId();
        if (parentId == null)
            dept.setParentId(0L);
        dept.setCreateTime(new Date());
        this.save(dept);
    }

    /**
     * 删除部门
     *
     * @param deptIds 对象
     */
    @Override
    @Transactional
    public void deleteDept(String[] deptIds) {
        this.delete(Arrays.asList(deptIds));
    }

    /**
     * 修改部门信息
     *
     * @param dept 对象
     */
    @Override
    @Transactional
    public void updateDept(Dept dept) {
        dept.setModifyTime(new Date());
        this.baseMapper.updateById(dept);
    }

    private void delete(List<String> deptIds) {
        removeByIds(deptIds);

        LambdaQueryWrapper<Dept> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Dept::getParentId, deptIds);
        List<Dept> depts = baseMapper.selectList(queryWrapper);
        if (CollectionUtils.isNotEmpty(depts)) {
            List<String> deptIdList = new ArrayList<>();
            depts.forEach(dept -> deptIdList.add(String.valueOf(dept.getDeptId())));
            this.delete(deptIdList);
        }
    }

    /**
     * 转换成树
     *
     * @param depts
     * @return
     */
    private List<DeptTree<Dept>> convertDepts(List<Dept> depts) {
        List<DeptTree<Dept>> trees = new ArrayList<>();
        depts.forEach(dept -> {
            DeptTree<Dept> tree = new DeptTree<>();
            tree.setId(String.valueOf(dept.getDeptId()));
            tree.setParentId((String.valueOf(dept.getParentId())));
            tree.setName(dept.getDeptName());
            tree.setData(dept);
            trees.add(tree);
        });
        return trees;
    }
}
