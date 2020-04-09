package com.ccc.eirc.system.controller;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.ccc.eirc.commons.annotation.ControllerEndpoint;
import com.ccc.eirc.commons.controller.BaseController;
import com.ccc.eirc.commons.domain.DeptTree;
import com.ccc.eirc.commons.domain.EircResopnse;
import com.ccc.eirc.commons.domain.QueryRequest;
import com.ccc.eirc.system.domain.Dept;
import com.ccc.eirc.system.service.DeptService;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * <a>Title:DeptController</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/27 20:47
 * @Version 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("dept")
public class DeptController extends BaseController {

    @Autowired
    private DeptService deptService;

    /**
     * 获取部门树
     *
     * @return
     */
    @GetMapping("select/tree")
    @ControllerEndpoint(exceptionMessage = "获取部门树失败")
    public List<DeptTree<Dept>> getDeptTree() {
        return this.deptService.findDepts();
    }

    /**
     * 根据部门获取部门树
     *
     * @param dept 对象
     * @return EircResopnse()
     */
    @GetMapping("tree")
    @ControllerEndpoint(exceptionMessage = "获取部门树失败")
    public EircResopnse getDeptTree(Dept dept) {
        List<DeptTree<Dept>> depts = this.deptService.findDepts(dept);
        return new EircResopnse().success().data(depts);
    }

    /**
     * 新增部门
     *
     * @param dept 新增对象
     * @return EircResopnse()
     */
    @PostMapping
    @RequiresPermissions("dept:add")
    @ControllerEndpoint(operation = "新增部门", exceptionMessage = "新增部门失败")
    public EircResopnse addDept(@Valid Dept dept) {
        this.deptService.createDept(dept);
        return new EircResopnse().success();
    }

    /**
     * 删除部门
     *
     * @param deptIds 对象 ID 集合
     * @return EircResopnse()
     */
    @GetMapping("delete/{deptIds}")
    @RequiresPermissions("dept:delete")
    @ControllerEndpoint(operation = "删除部门", exceptionMessage = "输出部门失败")
    public EircResopnse deleteDept(@NotBlank(message = "{required}") @PathVariable String deptIds) {
        String[] ids = deptIds.split(StringPool.COMMA);
        this.deptService.deleteDept(ids);
        return new EircResopnse().success();
    }

    /**
     * 修改部门
     *
     * @param dept 对象
     * @return EircResopnse()
     */
    @PostMapping("update")
    @RequiresPermissions("dept:update")
    @ControllerEndpoint(operation = "修改部门", exceptionMessage = "修改部门失败")
    public EircResopnse updateDept(@Valid Dept dept) {
        this.deptService.updateDept(dept);
        return new EircResopnse().success();
    }

    @GetMapping
    @RequiresPermissions("dept:export")
    @ControllerEndpoint(exceptionMessage = "导出Excel失败")
    public void export(Dept dept, QueryRequest request, HttpServletResponse response) {
        List<Dept> depts = this.deptService.findDepts(dept, request);
        ExcelKit.$Export(Dept.class, response).downXlsx(depts, false);
    }
}
