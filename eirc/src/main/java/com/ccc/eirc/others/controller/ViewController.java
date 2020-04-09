package com.ccc.eirc.others.controller;

import com.ccc.eirc.commons.domain.EircConstant;
import com.ccc.eirc.commons.utils.EircUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <a>Title:ViweController</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/29 13:42
 * @Version 1.0.0
 */
@Controller("othersView")
@RequestMapping(EircConstant.VIEW_PREFIX + "others")
public class ViewController {

    @GetMapping("febs/form")
    @RequiresPermissions("febs:form:view")
    public String eircFrom() {
        return EircUtil.view("others/febs/form");
    }

    @GetMapping("febs/form/group")
    @RequiresPermissions("febs:formgroup:view")
    public String febsFormGroup() {
        return EircUtil.view("others/febs/formGroup");
    }

    @GetMapping("febs/tools")
    @RequiresPermissions("febs:tools:view")
    public String febsTools() {
        return EircUtil.view("others/febs/tools");
    }

    @GetMapping("febs/icon")
    @RequiresPermissions("febs:icons:view")
    public String febsIcon() {
        return EircUtil.view("others/febs/icon");
    }

    @GetMapping("febs/others")
    @RequiresPermissions("others:febs:others")
    public String febsOthers() {
        return EircUtil.view("others/febs/others");
    }

    @GetMapping("apex/line")
    @RequiresPermissions("apex:line:view")
    public String apexLine() {
        return EircUtil.view("others/apex/line");
    }

    @GetMapping("apex/area")
    @RequiresPermissions("apex:area:view")
    public String apexArea() {
        return EircUtil.view("others/apex/area");
    }

    @GetMapping("apex/column")
    @RequiresPermissions("apex:column:view")
    public String apexColumn() {
        return EircUtil.view("others/apex/column");
    }

    @GetMapping("apex/radar")
    @RequiresPermissions("apex:radar:view")
    public String apexRadar() {
        return EircUtil.view("others/apex/radar");
    }

    @GetMapping("apex/bar")
    @RequiresPermissions("apex:bar:view")
    public String apexBar() {
        return EircUtil.view("others/apex/bar");
    }

    @GetMapping("apex/mix")
    @RequiresPermissions("apex:mix:view")
    public String apexMix() {
        return EircUtil.view("others/apex/mix");
    }

    @GetMapping("map")
    @RequiresPermissions("map:view")
    public String map() {
        return EircUtil.view("others/map/gaodeMap");
    }

    @GetMapping("eximport")
    @RequiresPermissions("others:eximport:view")
    public String eximport() {
        return EircUtil.view("others/eximport/eximport");
    }

    @GetMapping("eximport/result")
    public String eximportResult() {
        return EircUtil.view("others/eximport/eximportResult");
    }

}
