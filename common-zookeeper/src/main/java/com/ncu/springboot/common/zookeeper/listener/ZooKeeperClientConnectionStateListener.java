package com.ncu.springboot.common.zookeeper.listener;

/**
 * zookeeper连接状态监听器。
 */
public interface ZooKeeperClientConnectionStateListener {

    void notifyConnected();

    void notifyDisconnected();
}
