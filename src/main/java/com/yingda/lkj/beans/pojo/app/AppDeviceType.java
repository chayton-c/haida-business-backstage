package com.yingda.lkj.beans.pojo.app;

import com.yingda.lkj.beans.entity.backstage.device.DeviceType;
import com.yingda.lkj.beans.entity.backstage.line.RailwayLine;
import lombok.Data;

import java.util.List;

/**
 * @author hood  2020/4/13
 */
@Data
public class AppDeviceType {
    private String deviceTypeId;
    private String deviceTypeName;
    private String appDeviceMeasurementItemListStr;

    public AppDeviceType() {
    }

    public AppDeviceType(DeviceType deviceType) {
        this.deviceTypeId = deviceType.getId();
        this.deviceTypeName = deviceType.getName();
    }
}
