package com.ncu.springboot.core.model;

import java.io.Serializable;
import java.util.List;

/**
 * 树形层级结构接口
 */
public interface Traversable<T extends Traversable> extends Serializable {
    /**
     * 获取子树
     * */
    List<T> getSubList();

    /**
     * 设置子树
     * */
    void setSubList(List<T> list);
}
