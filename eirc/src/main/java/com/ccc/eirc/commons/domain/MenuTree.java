package com.ccc.eirc.commons.domain;

import com.ccc.eirc.system.domain.Menu;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <a>Title:MenuTree</a>
 * <a>Author：<a>
 * <a>Description：<a>
 * <p>
 * 菜单树
 *
 * @Author ccc
 * @Date 2020/3/25 15:52
 * @Version 1.0.0
 */
@Data
public class MenuTree<T> implements Serializable {

    private static final long serialVersionUID = -1253229012565745949L;

    private String id;
    private String icon;
    private String href;
    private String title;
    private Map<String, Object> state;
    private boolean checked = false;
    private Map<String, Object> attributes;
    private List<MenuTree<T>> childs = new ArrayList<>();
    private String parentId;
    private boolean hasParent = false;
    private boolean hasChild = false;

    private Menu data;

}
