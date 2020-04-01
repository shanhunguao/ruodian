package com.ncu.springboot.common.zookeeper.listener;

import org.apache.zookeeper.data.Stat;

import com.ncu.springboot.core.constant.CharsetType;
import com.ncu.springboot.core.util.ZipUtil;

public abstract class PathChildrenStringDataCacheEventListener implements PathChildrenCacheEventListener {

    protected boolean dataCompressed;

    protected boolean useOriginalDataWhenDecompressFailed;

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
     * 新增子节点事件
     *
     * @param fullPath 节点路径
     * @param data     节点数据
     * @param stat     节点数据版本信息
     */
    @Override
    public void childPathAdded(String fullPath, byte[] data, Stat stat) {
        String stringData = toString(data);
        childPathAdded(fullPath, stringData, stat);
    }

    /**
     * 更新子节点事件
     *
     * @param fullPath 节点路径
     * @param data     节点数据
     * @param stat     节点数据版本信息
     */
    @Override
    public void childPathUpdated(String fullPath, byte[] data, Stat stat) {
        String stringData = toString(data);
        childPathUpdated(fullPath, stringData, stat);
    }

    /**
     * 新增子节点事件
     *
     * @param fullPath 节点路径
     * @param data     节点数据
     * @param stat     节点数据版本信息
     */
    public abstract void childPathAdded(String fullPath, String data, Stat stat);

    /**
     * 更新子节点事件
     *
     * @param fullPath 节点路径
     * @param data     节点数据
     * @param stat     节点数据版本信息
     */
    public abstract void childPathUpdated(String fullPath, String data, Stat stat);

    /**
     * 移除子节点事件
     *
     * @param fullPath 节点路径
     * @param stat     节点数据版本信息
     */
    public abstract void childPathRemoved(String fullPath, Stat stat);

}
