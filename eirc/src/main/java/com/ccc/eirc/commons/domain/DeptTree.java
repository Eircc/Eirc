package com.ccc.eirc.commons.domain;

import com.ccc.eirc.system.domain.Dept;
import lombok.Data;
import org.apache.poi.ss.formula.functions.T;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;

/**
 * <a>Title:DeptTree</a>
 * <a>Author：<a>
 * <a>Description：<a>
 * <p>
 * 部门树
 *
 * @Author ccc
 * @Date 2020/3/27 20:55
 * @Version 1.0.0
 */
@Data
public class DeptTree<T> implements Serializable {

    private static final long serialVersionUID = 3656756184065493436L;

    private String id;
    private String icon;
    private String href;
    private String name;
    private Map<String, Object> state;
    private boolean check = false;
    private Map<String, Object> attributes;
    private List<DeptTree<T>> children;
    private String parentId;
    private boolean hasParent = false;
    private boolean hasChild = false;
    private Dept data;

    public void initChildren() {
        this.children = new ArrayList<>();
    }
}
