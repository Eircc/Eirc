package com.ccc.eirc.commons.utils;

import com.ccc.eirc.commons.domain.DeptTree;
import com.ccc.eirc.commons.domain.MenuTree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <a>Title:TreeUtil</a>
 * <a>Author：<a>
 * <a>Description：<a>
 * <p>
 * jsonTreeNode
 *
 * @Author ccc
 * @Date 2020/3/25 15:49
 * @Version 1.0.0
 */
public class TreeUtil {
    protected TreeUtil() {

    }

    /**
     * 构建树形菜单
     *
     * @param nodes 节点
     * @param <T>   泛型
     * @return
     */
    public static <T> MenuTree<T> buildMenuTree(List<MenuTree<T>> nodes) {
        if (nodes == null) {
            return null;
        }
        List<MenuTree<T>> topNodes = new ArrayList<>();
//        遍历子节点
        nodes.forEach(children -> {
            String pid = children.getParentId();
            if (pid == null || "0".equals(pid)) {
                topNodes.add(children);
                return;
            }
//            遍历子节点
            for (MenuTree<T> parent : nodes) {
                String id = parent.getId();
                if (id != null && id.equals(pid)) {
                    parent.getChilds().add(children);
                    children.setHasParent(true);
                    parent.setHasChild(true);
                    return;
                }
            }
        });

        /**
         * 管理员菜单
         *     id : 0
         *     parentId : ""
         *     hasParent : false
         *     hasChild : true
         *     hasChecked : true
         *     childs : topNodes
         */
        MenuTree<T> root = new MenuTree<>();
        root.setId("0");
        root.setParentId("");
        root.setHasParent(false);
        root.setHasChild(true);
        root.setChecked(true);
        root.setChilds(topNodes);
        Map<String, Object> state = new HashMap<>(16);
        root.setState(state);
        return root;
    }

    /**
     * 菜单树
     *
     * @param nodes   节点
     * @param idParam id
     * @param <T>     泛型
     * @return
     */
    public static <T> List<MenuTree<T>> buildList(List<MenuTree<T>> nodes, String idParam) {
        if (nodes == null) {
            return new ArrayList<>();
        }
        List<MenuTree<T>> topNodes = new ArrayList<>();
        nodes.forEach(children -> {
            String pid = children.getParentId();
//            如果 pid 为空或者为 0 则是父节点
            if (pid == null || idParam.equals(pid)) {
                topNodes.add(children);
                return;
            }
            nodes.forEach(parent -> {
                String id = parent.getId();
                if (id != null && id.equals(pid)) {
                    parent.getChilds().add(children);
                    children.setHasParent(true);
                    children.setHasChild(true);
                }
            });
        });
        return topNodes;
    }

    /**
     * 部门树
     *
     * @param nodes 节点
     * @param <T>   泛型
     * @return
     */
    public static <T> List<DeptTree<T>> buildDeptTree(List<DeptTree<T>> nodes) {
        if (nodes == null) {
            return null;
        }
        List<DeptTree<T>> result = new ArrayList<>();
        nodes.forEach(children -> {
            String pId = children.getParentId();
            if (pId == null || "0".equals(pId)) {
                result.add(children);
                return;
            }
            for (DeptTree<T> node : nodes) {
                String id = node.getId();
                if (node.getChildren() == null)
                    node.initChildren();
                node.getChildren().add(children);
                node.setHasParent(true);
                return;
            }
        });
        return result;
    }

}
