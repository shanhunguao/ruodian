package com.ncu.springboot.common.zookeeper.listener;

import org.apache.zookeeper.data.Stat;

public interface PathChildrenCacheEventListener {

    /**
     * 新增子节点事件
     *
     * @param fullPath 节点路径
     * @param data     节点数据
     * @param stat     节点数据版本信息
     */
    void childPathAdded(String fullPath, byte[] data, Stat stat);

    /**
     * 更新子节点事件
     *
     * @param fullPath 节点路径
     * @param data     节点数据
     * @param stat     节点数据版本信息
     */
    void childPathUpdated(String fullPath, byte[] data, Stat stat);

    /**
     * 移除子节点事件
     *
     * @param fullPath 节点路径
     * @param stat     节点数据版本信息
     */
    void childPathRemoved(String fullPath, Stat stat);

    /**
     * 是否需要递归(继续子节点级)监听
     */
    boolean isNeedRecursionWatch();

}
