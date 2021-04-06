package com.yingda.lkj.beans.pojo.device.devicetype;

import com.yingda.lkj.beans.entity.backstage.device.DeviceSubType;
import com.yingda.lkj.beans.entity.backstage.device.DeviceType;
import com.yingda.lkj.beans.entity.backstage.line.RailwayLine;
import com.yingda.lkj.beans.entity.backstage.line.Station;
import com.yingda.lkj.beans.pojo.line.LineNodePojo;
import com.yingda.lkj.utils.StreamUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author hood  2020/11/22
 */
public class DeviceTypeNodePojo {
    private static final byte DEVICE_TYPE = 0;
    private static final byte DEIVCE_SUB_TYPE = 1;

    private String id;
    private String name;
    private byte type;
    private List<DeviceTypeNodePojo> deviceTypeNodePojoList;

    public DeviceTypeNodePojo(DeviceType deviceType, List<DeviceSubType> deviceSubTypes) {
        this.id = deviceType.getId();
        this.name = deviceType.getName();
        this.type = DEVICE_TYPE;
        this.deviceTypeNodePojoList = StreamUtil.getList(deviceSubTypes, DeviceTypeNodePojo::new);
    }

    public DeviceTypeNodePojo(DeviceSubType deviceSubType) {
        this.id = deviceSubType.getId();
        this.name = deviceSubType.getName();
        this.type = DEIVCE_SUB_TYPE;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public List<DeviceTypeNodePojo> getDeviceTypeNodePojoList() {
        return deviceTypeNodePojoList;
    }

    public void setDeviceTypeNodePojoList(List<DeviceTypeNodePojo> deviceTypeNodePojoList) {
        this.deviceTypeNodePojoList = deviceTypeNodePojoList;
    }
}
