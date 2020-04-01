package com.ncu.springboot.common.zookeeper.factory;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ncu.springboot.common.zookeeper.ZooKeeperClient;

public class ZooKeeperClientFactory {

    private static final Logger LOG = LoggerFactory.getLogger(ZooKeeperClientFactory.class);

    public static ZooKeeperClient createZooKeeperClient(String basePath, String zookeeperConnectString) {
        if (StringUtils.isNotBlank(basePath)) {
            basePath = ZooKeeperClient.buildFullPath(basePath);
            // 先不带基路径连接，连接后确认（创建）基路径
            ZooKeeperClient zkClient = new ZooKeeperClient(zookeeperConnectString);
            zkClient.start();
            zkClient.mkdirs(basePath);
            zkClient.close();
            // 组装完整的连接串
            zookeeperConnectString += basePath;
        }
        ZooKeeperClient zkClient = new ZooKeeperClient(zookeeperConnectString);
        LOG.info("create ZooKeeperClient of: " + zookeeperConnectString);
        zkClient.start();
        return zkClient;
    }

    public static ZooKeeperClient createZooKeeperClient() {
        return createZooKeeperClient(null, "");
    }

}
