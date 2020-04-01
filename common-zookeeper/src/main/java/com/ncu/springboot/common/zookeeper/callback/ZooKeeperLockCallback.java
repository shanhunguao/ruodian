package com.ncu.springboot.common.zookeeper.callback;

import com.ncu.springboot.common.zookeeper.ResultContainer;
import com.ncu.springboot.common.zookeeper.ZooKeeperClient;

/**
 * 在ZooKeeper里加锁后执行业务逻辑的回调接口。
 *
 * @param <T> 业务返回结果的类型。
 */
public interface ZooKeeperLockCallback<T> {

    /**
     * 执行业务逻辑。
     *
     * @param lockedPath      已锁定的路径。
     * @param resultContainer 结果容器，如果有结果要返回，请调用{@link ResultContainer#setResult(Object)}设置结果，该结果最后会由
     *                        {@link ZooKeeperClient#lockAndWork(String, long, java.util.concurrent.TimeUnit, ZooKeeperLockCallback)}
     *                        返回。
     * @param client          ZooKeeperClient，方便对ZooKeeper进行其他操作。
     */
    void doWork(String lockedPath, ResultContainer<T> resultContainer, ZooKeeperClient client);

}
