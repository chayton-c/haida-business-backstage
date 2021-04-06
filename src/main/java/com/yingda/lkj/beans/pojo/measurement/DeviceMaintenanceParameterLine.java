package com.yingda.lkj.beans.pojo.measurement;

import com.yingda.lkj.beans.entity.backstage.measurement.DeviceMaintenanceParameter;
import com.yingda.lkj.utils.date.DateUtil;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author hood  2020/6/15
 */
@Data
public class DeviceMaintenanceParameterLine {
    private Object[] data;

    public DeviceMaintenanceParameterLine(DeviceMaintenanceParameter deviceMaintenanceParameter) {
        this.data = new Object[2];
        Timestamp measurementTime = deviceMaintenanceParameter.getMeasurementTime();
        double value = deviceMaintenanceParameter.getValue();
        data[0] = DateUtil.format(measurementTime, "yyyy-MM-dd HH:mm:ss");
        data[1] = value;
    }
}
