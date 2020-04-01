package com.ncu.springboot.common.zookeeper.listener;

import com.ncu.springboot.core.constant.CharsetType;
import com.ncu.springboot.core.util.ZipUtil;

/**
 * 节点数据缓存监听器。
 */
public abstract class NodeStringDataCacheListener implements NodeDataCacheListener {
    protected boolean dataCompressed;
    protected boolean useOriginalDataWhenDecompressFailed;

    /**
     * 设置节点是否压缩
     */
    public void setDataCompressed(boolean dataCompressed) {
        this.dataCompressed = dataCompressed;
    }

    private String toString(byte[] bytes) {
        if (null == bytes) {
            return null;
        }
        if (dataCompressed && 0 < bytes.length) {
            bytes = ZipUtil.ungzip(bytes, useOriginalDataWhenDecompressFailed);
        }
        return new String(bytes, CharsetType.CHARSET_UTF_8);
    }

    /**
     * 节点有变化，节点增删或数据更新。
     *
     * @param path 节点路径。
     * @param data 当前数据，如果为null表示节点不存在（或获取数据发生异常）。
     */
    @Override
    public void nodeChanged(String path, byte[] data) {
        String stringData = toString(data);
        nodeChanged(path, stringData);
    }

    /**
     * 节点有变化，节点增删或数据更新。
     *
     * @param path 节点路径。
     * @param data 当前数据，如果为null表示节点不存在（或获取数据发生异常）。
     */
    public abstract void nodeChanged(String path, String data);

}
