package com.ccc.eirc.system.controller;

import com.ccc.eirc.commons.annotation.ControllerEndpoint;
import com.ccc.eirc.commons.controller.BaseController;
import com.ccc.eirc.commons.domain.EircResopnse;
import com.ccc.eirc.commons.domain.MenuTree;
import com.ccc.eirc.commons.exception.EircException;
import com.ccc.eirc.system.domain.Menu;
import com.ccc.eirc.system.domain.User;
import com.ccc.eirc.system.service.MenuService;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * <a>Title:MenuController</a>
 * <a>Author：<a>
 * <a>Description：<a>
 * <p>
 * 菜单
 *
 * @Author ccc
 * @Date 2020/3/25 15:44
 * @Version 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("menu")
public class MenuController extends BaseController {

    @Autowired
    private MenuService menuService;

    /**
     * 获取左边导航栏树形菜单
     *
     * @param username
     * @return
     */
    @GetMapping("{username}")
    public EircResopnse getUserMenu(@NotBlank(message = "{required}") @PathVariable String username) {
        User currentUser = getCurrentUser();
        if (!StringUtils.equalsIgnoreCase(username, currentUser.getUsername()))
            throw new EircException("您无权获取别人的菜单！");
        MenuTree<Menu> userMenus = this.menuService.findUserMenus(username);
        return new EircResopnse().data(userMenus);
    }

    /**
     * 获取下拉树菜单
     *
     * @param menu
     * @return
     */
    @GetMapping("tree")
    @ControllerEndpoint(exceptionMessage = "获取菜单树失败")
    public EircResopnse getMenuTree(Menu menu) {
        MenuTree<Menu> menus = this.menuService.findMenus(menu);
        return new EircResopnse().success().data(menus.getChilds());
    }

    /**
     * 新增 菜单或按钮
     *
     * @param menu 菜单
     * @return
     */
    @PostMapping
    @RequiresPermissions("menu:add")
    @ControllerEndpoint(operation = "新增菜单/按钮", exceptionMessage = "新增菜单/按钮失败")
    public EircResopnse addMenus(@Valid Menu menu) {
        this.menuService.createMenu(menu);
        return new EircResopnse().success();
    }

    /**
     * 删除菜单/按钮 批量/单个
     *
     * @param menuIds 要删除的菜单/按钮 id
     * @return
     */
    @GetMapping("delete/{menuIds}")
    @RequiresPermissions("menu:delete")
    @ControllerEndpoint(operation = "删除菜单/按钮", exceptionMessage = "删除菜单/按钮失败")
    public EircResopnse deleteMenus(@NotBlank(message = "{required}") @PathVariable String menuIds) {
        this.menuService.deleteMenus(menuIds);
        return new EircResopnse().success();
    }

    /**
     * 修改菜单/按钮
     *
     * @param menu
     * @return
     */
    @PostMapping("update")
    @RequiresPermissions("menu:update")
    @ControllerEndpoint(operation = "修改菜单/按钮", exceptionMessage = "修改菜单/按钮失败")
    public EircResopnse updateMenu(@Valid Menu menu) {
        this.menuService.updateMenu(menu);
        return new EircResopnse().success();
    }

    /**
     * 导出Excel文件
     *
     * @param menu
     * @param response
     */
    @GetMapping("excel")
    @RequiresPermissions("menu:export")
    @ControllerEndpoint(exceptionMessage = "导出Excel失败")
    public void export(Menu menu, HttpServletResponse response) {
        List<Menu> menus = this.menuService.findMenuList(menu);
        ExcelKit.$Export(Menu.class, response).downXlsx(menus, false);
    }


}
