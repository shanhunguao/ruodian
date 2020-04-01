package com.ncu.springboot.common.factory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.ncu.springboot.common.zookeeper.ZooKeeperClient;
import com.ncu.springboot.common.zookeeper.factory.ZooKeeperClientFactory;

@Component
public class NcuZooKeeperClientFactory {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(NcuZooKeeperClientFactory.class);
	
	private static final String CONNECTION_KEY = "zookeeper.connectionString";
	
    /**
     * 通过项目引入的common实现不同项目在zookeeper的命名空间的划分
     */
    public static final String YQOA_ZOOKEEPER_BASE_PATH = "/ncu";
    
    /**
     * 通过项目引入的common实现不同项目在zookeeper的命名空间的划分
     */
    public static final String ZOOKEEPER_CONNECTION_STRING;


    static {
    	System.out.println("zookeeper++++++++++++++++++++++++");
        Properties prop = new Properties();
        try {
            try (InputStream ips = NcuZooKeeperClientFactory.class.getClassLoader().getResourceAsStream("conf/zookeeper.properties")) {
                prop.load(ips);
            }
        } catch (IOException e) {
            LOGGER.error(" acquire conf/zookeeper.properties error . please check");
            System.exit(-1);
        }
        ZOOKEEPER_CONNECTION_STRING = prop.getProperty(CONNECTION_KEY);
        System.out.println("zookeeper is " + ZOOKEEPER_CONNECTION_STRING);
        if (null == ZOOKEEPER_CONNECTION_STRING || ZOOKEEPER_CONNECTION_STRING.isEmpty()) {
            LOGGER.error(" ************* acquire zookeeper.connectionString error , please config zookeeper.connectionString in conf/zookeeper.properties ");
            System.exit(-1);
        }
    }

    @Bean
    public ZooKeeperClient createZooKeeperClient() {
        return ZooKeeperClientFactory.createZooKeeperClient(YQOA_ZOOKEEPER_BASE_PATH, ZOOKEEPER_CONNECTION_STRING);
    }
    
}
