package com.yingda.lkj.beans.pojo.device.devicetype;

import com.yingda.lkj.beans.entity.backstage.device.DeviceSubType;
import com.yingda.lkj.utils.StreamUtil;

import java.util.List;

/**
 * @author hood  2020/11/22
 */
public class DeviceTypeSelectTreeNode {

    private String key;
    private String title;
    private List<DeviceTypeSelectTreeNode> children;

    public DeviceTypeSelectTreeNode(String deviceTypeId, String deviceTypeName, List<DeviceSubType> deviceSubTypes) {
        this.key = deviceTypeId;
        this.title = deviceTypeName;
        this.children = StreamUtil.getList(deviceSubTypes, DeviceTypeSelectTreeNode::new);
    }

    public DeviceTypeSelectTreeNode(DeviceSubType deviceSubType) {
        this.key = deviceSubType.getId();
        this.title = deviceSubType.getName();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<DeviceTypeSelectTreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<DeviceTypeSelectTreeNode> children) {
        this.children = children;
    }
}
