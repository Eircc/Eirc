package com.ccc.eirc.system.controller;

import com.ccc.eirc.commons.annotation.ControllerEndpoint;
import com.ccc.eirc.commons.controller.BaseController;
import com.ccc.eirc.commons.domain.EircResopnse;
import com.ccc.eirc.commons.domain.QueryRequest;
import com.ccc.eirc.system.domain.Role;
import com.ccc.eirc.system.service.RoleService;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * <a>Title:RoleController</a>
 * <a>Author：<a>
 * <a>Description：<a>
 * <p>
 * 角色控制器
 *
 * @Author ccc
 * @Date 2020/3/27 19:35
 * @Version 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    /**
     * 查询所有角色
     *
     * @param role 角色
     * @return
     */
    @GetMapping
    public EircResopnse getAllRoles(Role role) {
        return new EircResopnse().success().data(roleService.findRoles(role));
    }

    /**
     * 查询所有并分页
     *
     * @param role    对象
     * @param request 分页参数
     * @return
     */
    @GetMapping("list")
    @RequiresPermissions("role:view")
    public EircResopnse roleList(Role role, QueryRequest request) {
        Map<String, Object> dataTable = getDataTable(this.roleService.findRoles(role, request));
        return new EircResopnse().success().data(dataTable);
    }

    /**
     * 新增角色
     *
     * @param role
     * @return
     */
    @PostMapping
    @RequiresPermissions("role:add")
    @ControllerEndpoint(operation = "新增角色", exceptionMessage = "新增角色失败")
    public EircResopnse addRole(@Valid Role role) {
        this.roleService.create(role);
        return new EircResopnse().success();
    }

    /**
     * 删除角色
     *
     * @param roleIds 角色ids
     * @return
     */
    @GetMapping("delete/{roleIds}")
    @RequiresPermissions("role:delete")
    @ControllerEndpoint(operation = "删除角色", exceptionMessage = "删除角色失败")
    public EircResopnse deleteRole(@NotBlank(message = "{required}") @PathVariable String roleIds) {
        this.roleService.deleteRole(roleIds);
        return new EircResopnse().success();
    }

    /**
     * 修改角色信息
     *
     * @param role 对象
     * @return
     */
    @PostMapping("update")
    @RequiresPermissions("role:update")
    @ControllerEndpoint(operation = "修改角色", exceptionMessage = "修改角色失败")
    public EircResopnse updateRole(Role role) {
        this.roleService.updateRole(role);
        return new EircResopnse().success();
    }

    /**
     * 导出Excel
     *
     * @param request  request
     * @param role     对象
     * @param response response
     */
    @GetMapping("excel")
    @RequiresPermissions("role:export")
    @ControllerEndpoint(exceptionMessage = "导出Excel表格")
    public void export(QueryRequest request, Role role, HttpServletResponse response) {
        List<Role> roles = this.roleService.findRoles(role, request).getRecords();
        ExcelKit.$Export(Role.class, response).downXlsx(roles, false);
    }
}
