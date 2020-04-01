package com.ncu.springboot.common.zookeeper.listener;

/**
 * 主导节点竞争事件监听器。
 */
public interface LeaderLatchEventListener {
    /**
     * 当前是主导节点。
     */
    void isLeader();

    /**
     * 当前不是主导节点。
     */
    void notLeader();

}