package com.yingda.lkj.beans.pojo.app;

import com.yingda.lkj.beans.entity.backstage.device.DeviceMeasurementItem;
import lombok.Data;

/**
 * @author hood  2020/6/15
 */
@Data
public class AppDeviceMeasurementItem {
    private String deviceMeasurementItemId;
    private String name;
    private String measurementUnitId;

    public AppDeviceMeasurementItem(DeviceMeasurementItem deviceMeasurementItem) {
        this.deviceMeasurementItemId = deviceMeasurementItem.getId();
        this.name = deviceMeasurementItem.getName();
        this.measurementUnitId = deviceMeasurementItem.getMeasurementUnitId();
    }

    @Override
    public String toString() {
        return "{" +
                "deviceMeasurementItemId='" + deviceMeasurementItemId + '\'' +
                ", name='" + name + '\'' +
                ", measurementUnitId='" + measurementUnitId + '\'' +
                '}';
    }
}
